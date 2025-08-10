package br.com.project.repository;

import br.com.project.exception.AccountWithInvestimentException;
import br.com.project.exception.InvestmentNotFoundException;
import br.com.project.exception.WalletNotFoundException;
import br.com.project.model.AccountWallet;
import br.com.project.model.Investment;
import br.com.project.model.InvestmentWallet;

import java.util.ArrayList;
import java.util.List;

import static br.com.project.repository.CommonRepository.checkFoundsForTransaction;

public class InvestimentRepository {
    private long nextId;
    private final List<Investment> investiments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investment create(final long tax, final long initialFounds) {
        this.nextId++;
        var investiment = new Investment(this.nextId,tax,initialFounds);
        investiments.add(investiment);
        return investiment;
    }

    public InvestmentWallet initInvestiment(final AccountWallet account, final long id){
        var accountsInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
            if (accountsInUse.contains(account)) {
                throw new AccountWithInvestimentException("A conta "+ account + " já possui uma carteira de investimento.");
            }
        var investment = findById(id);
        checkFoundsForTransaction(account,investment.initialFounds());
        var wallet = new InvestmentWallet(investment,account,investment.initialFounds());
        wallets.add(wallet);
        return wallet;
    }



    public InvestmentWallet deposit(final String pix, final long founds) {
        var wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(founds),wallet.getService(),
                "Depósito de fundos na carteira de investimento");
        return wallet;

    }

    public InvestmentWallet withdraw(final String pix, final long founds){
        var wallet = findWalletByAccountPix(pix);
        checkFoundsForTransaction(wallet, founds);
        wallet.getAccount().addMoney(wallet.reduceMoney(founds),wallet.getService(),
                "Retirada de fundos da carteira de investimento");
        if (wallet.getFound() == 0) {
            wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmount(){
        wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    public Investment findById(final long id) {
        return investiments.stream().filter(a -> a.id() == id).findFirst().orElseThrow(
                () -> new InvestmentNotFoundException("O investimento com o ID " + id + " não foi encontrado.")
        );
    }

    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return wallets.stream().filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(
                        () -> new WalletNotFoundException("A carteira com o PIX " + pix + " não foi encontrada.")
                );}


    public List<InvestmentWallet> listWallets() {
        return this.wallets;
    }
    public List<Investment> list() {
        return this.investiments;
    }

}
