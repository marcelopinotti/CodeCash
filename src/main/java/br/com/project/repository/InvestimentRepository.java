package br.com.project.repository;

import br.com.project.expction.AccountWithInvestimentException;
import br.com.project.expction.InvestmentNotFoundException;
import br.com.project.expction.PixInUseException;
import br.com.project.expction.WalletNotFoundException;
import br.com.project.model.AccountWallet;
import br.com.project.model.Investiment;
import br.com.project.model.InvestmentWallet;

import java.util.ArrayList;
import java.util.List;

import static br.com.project.repository.CommonRepository.checkFoundsForTransaction;

public class InvestimentRepository {
    private long nextId;
    private final List<Investiment> investiments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investiment create(final long tax, final long initialFounds) {
        this.nextId++;
        var investiment = new Investiment(this.nextId,tax,initialFounds);
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

    public void updateAmount(final long percent){
        wallets.forEach(w -> w.updateAmount(percent));
    }

    public Investiment findById(final long id) {
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
    public List<Investiment> list() {
        return this.investiments;
    }

}
