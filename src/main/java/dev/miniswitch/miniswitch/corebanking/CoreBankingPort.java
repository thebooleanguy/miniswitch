package dev.miniswitch.miniswitch.corebanking;

import dev.miniswitch.miniswitch.model.TransactionResult;

public interface CoreBankingPort {
    TransactionResult debit(String accountNumber, long amount);
}
