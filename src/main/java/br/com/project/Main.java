package br.com.project;

import br.com.project.exception.AccountNotFoundException;
import br.com.project.exception.NoFundsEnoughException;
import br.com.project.exception.WalletNotFoundException;
import br.com.project.model.AccountWallet;
import br.com.project.model.MoneyAudit;
import br.com.project.repository.AccountRepository;
import br.com.project.repository.InvestmentRepository;


import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Main {
    static Scanner sc = new Scanner(System.in);
    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    public static void main(String[] args) {
        System.out.println("-------------------------------------");
        System.out.println("|                                   |");
        System.out.println("|    Seja bem vindo ao CodeCash     |");
        System.out.println("|                                   |");
        System.out.println("-------------------------------------");
        while (true){
            System.out.println("Selecione a operação desejada:");
            System.out.println("1- Criar uma conta");
            System.out.println("2- Criar um investimento");
            System.out.println("3- Criar uma carteira de investimento");
            System.out.println("4- Depositar na conta");
            System.out.println("5- Sacar da conta");
            System.out.println("6- Transferir entre contas");
            System.out.println("7- Investir");
            System.out.println("8- Sacar Investimento");
            System.out.println("8- Sacar Investimento");
            System.out.println("9- Listar contas");
            System.out.println("10- Listar investimentos");
            System.out.println("11- Listar carteira de investimento");
            System.out.println("12- Atualizar investimentos");
            System.out.println("13- Histórico de conta");
            System.out.println("14- Sair");
            var option = sc.nextInt();
            switch (option){
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> createInvestmentWallet();
                case 4 -> deposit();
                case 5-> withdraw();
                case 6-> transferToAccount();
                case 7-> incInvest();
                case 8-> rescueInvestment();
                case 9-> accountRepository.list().forEach(System.out::println);
                case 10-> investmentRepository.list().forEach(System.out::println);
                case 11-> investmentRepository.listWallets().forEach(System.out::println);
                case 12-> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos atualizados com sucesso!");
                }
                case 13-> checkHistory();
                case 14-> System.exit(0);
                default->
                    System.out.println("Opção inválida!");
            }






        }


    }
    private static void createAccount() {
        System.out.println("Informe as chaves Pix separadas por ';' ");
        var pix = Arrays.stream(sc.next().split(";")).toList();
        System.out.println("Informe o valor inicial de depósito");
        var amount = sc.nextLong();
        var wallet = accountRepository.create(pix,amount);
        System.out.println("Conta criada: "+wallet);
    }

    private static void createInvestment() {
        System.out.println("Informe a taxa do investimento: ");
        var tax = sc.nextInt();
        System.out.println("Informe o valor inicial de depósito");
        var initialFunds = sc.nextLong();
        var investment = investmentRepository.create(tax,initialFunds);
        System.out.println("Investimento criado: "+investment);
    }

    private static void deposit(){
        System.out.println("Informe o valor a ser depositado:");
        var amount = sc.nextLong();
        System.out.println("Informe o pix da conta:");
        var pix = sc.next();
        try{
        accountRepository.deposit(pix,amount);
    } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void withdraw(){
        System.out.println("Informe o valor a ser sacado:");
        var amount = sc.nextLong();
        System.out.println("Informe o pix da conta:");
        var pix = sc.next();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void transferToAccount(){
        System.out.println("Informe o pix da conta de origem:");
        var source = sc.next();
        System.out.println("Informe o pix da conta destino:");
        var target = sc.next();
        System.out.println("Informe o valor a ser depositado:");
        var amount = sc.nextLong();

        try{
            accountRepository.transferMoney(source,target,amount);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createInvestmentWallet(){
        System.out.println("Informe o pix da conta:");
        var pix = sc.next();
        var account = accountRepository.findByPix(pix);
        System.out.println("Informe o id do investimento:");
        var investmentId = sc.nextInt();
        var investment = investmentRepository.initInvestiment(account,investmentId);
        System.out.println("Carteira de investimento criada: "+investment);
    }

    private static void incInvest(){
        System.out.println("Informe o valor a ser investido:");
        var amount = sc.nextLong();
        System.out.println("Informe o pix da conta para o investimento:");
        var pix = sc.next();
        try{
            accountRepository.deposit(pix,amount);
        } catch (WalletNotFoundException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rescueInvestment(){
        System.out.println("Informe o valor para ser sacado:");
        var amount = sc.nextLong();
        System.out.println("Informe o pix da conta para resgate do investimento:");
        var pix = sc.next();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkHistory() {
        System.out.println("Informe a chave Pix da conta para verificar o extrato:");
        var pix = sc.next();
        try {
            var account = accountRepository.findByPix(pix);

            System.out.println("=== Histórico de Transações da Conta ===");
            var acctHistory = account.getFinancialTransaction();
            if (acctHistory == null || acctHistory.isEmpty()) {
                System.out.println("Sem movimentações na conta para esta chave Pix.");
            } else {
                acctHistory.stream()
                        .sorted(java.util.Comparator.comparing(br.com.project.model.MoneyAudit::createdAt))
                        .forEach(audit -> {
                            printAuditCore(audit);
                            System.out.println("  Data: " + audit.createdAt().format(ISO_DATE_TIME));
                        });
            }

            System.out.println("\n=== Histórico de Investimentos ===");
            try {
                var wallet = investmentRepository.findWalletByAccountPix(pix);
                var investmentHistory = wallet.getFinancialTransaction();
                if (investmentHistory == null || investmentHistory.isEmpty()) {
                    System.out.println("Sem histórico de investimentos para esta conta.");
                } else {
                    investmentHistory.stream()
                            .sorted(java.util.Comparator.comparing(br.com.project.model.MoneyAudit::createdAt))
                            .forEach(audit -> {
                                printAuditCore(audit);
                                System.out.println("  Data: " + audit.createdAt().format(ISO_DATE_TIME));
                            });
                }
            } catch (WalletNotFoundException e) {
                System.out.println("Sem histórico de investimentos para esta conta.");
            }

        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void printAuditCore(final MoneyAudit transaction) {
        System.out.println("  ID da Transação: " + transaction.transactionId());
        System.out.println("  Serviço: " + transaction.targetService());
        System.out.println("  Descrição: " + transaction.description());
    }



}