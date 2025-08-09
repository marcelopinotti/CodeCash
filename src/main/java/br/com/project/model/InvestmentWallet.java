package br.com.project.model;

import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.project.model.BankService.INVESTIMENT;

@ToString
@Getter
public class InvestmentWallet extends Wallet {

    private final Investiment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investiment investment, AccountWallet account, final long amount) {
        super(INVESTIMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "investimento");
    }

    public void updateAmount(final long percent) {
        var amount = getFound() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getService(), "rendimentos", OffsetDateTime.now());
        var money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }
}