package br.com.project.repository;

import br.com.project.expction.AccountNotFoundException;
import br.com.project.model.AccountWallet;

import java.util.List;

import static br.com.project.repository.CommonRepository.checkFoundsForTransaction;

public class AccountRepository {

    private List<AccountWallet> accounts;

    public List<AccountWallet> list() {
        return this.accounts;
    }

    public void deposit(final String pix,final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito na conta");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkFoundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix,final String targetPix,final long amount) {
        var source = findByPix(sourcePix);
        var target = findByPix(targetPix);
        var message = "Transferência de " + amount + " de " + sourcePix + " para " + targetPix;
        target.addMoney(source.reduceMoney(amount),source.getService(),message);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream().filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada"));
    }

    public AccountWallet create(final List<String> pix, final long initialFunds) {
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }


}
