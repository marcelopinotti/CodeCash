package br.com.project.repository;


import br.com.project.expction.NoFundsEnoughException;
import br.com.project.model.AccountWallet;
import br.com.project.model.Money;
import br.com.project.model.MoneyAudit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.project.model.BankService.ACCOUNT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonRepository {

    public static void checkFoundsForTransaction(final AccountWallet source, final long amount) {
        if (source.getFound() < amount) {
            throw new NoFundsEnoughException("Sua conta não possui saldo suficiente para realizar esta transação.");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description) {
        var history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }
}