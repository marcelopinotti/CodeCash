package br.com.project;

import br.com.project.exception.AccountNotFoundException;
import br.com.project.exception.NoFundsEnoughException;
import br.com.project.repository.AccountRepository;
import br.com.project.repository.InvestimentRepository;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestimentRepository investimentRepository = new InvestimentRepository();

    public static void main(String[] args) {
        System.out.println("-------------------------------------");
        System.out.println("|                                   |");
        System.out.println("|    Seja bem vindo ao JavaBank     |");
        System.out.println("|                                   |");
        System.out.println("-------------------------------------");
        while (true){
            System.out.println("Selecione a operação desejada:");
            System.out.println("1- Criar uma conta");
            System.out.println("2- Criar um investimento");
            System.out.println("3- Fazer um investimento");
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
                case 1: createAccount();
                case 2: createInvestment();
                case 3:
                case 4: deposit();
                case 5: withdraw();
                case 6:
                case 7:
                case 8:
                case 9: accountRepository.list().forEach(System.out::println);
                case 10: investimentRepository.list().forEach(System.out::println);
                case 11: investimentRepository.listWallets().forEach(System.out::println);
                case 12: {
                    investimentRepository.updateAmount();
                    System.out.println("Investimentos atualizados com sucesso!");
                }
                case 13:
                case 14: System.exit(0);
                default:
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
        var investment = investimentRepository.create(tax,initialFunds);
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
}
