package dev.miniswitch.miniswitch.corebanking;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import dev.miniswitch.miniswitch.model.TransactionResult;

@Component
public class MockCoreBankingAdapter implements CoreBankingPort  {

    private final Map<String, MockAccount> accounts = new HashMap<>();

    public MockCoreBankingAdapter() {
        MockAccount accountOne = new MockAccount("100001",500000);
        MockAccount accountTwo = new MockAccount("100002",150000);

        accounts.put(accountOne.getAccountNumber(), accountOne);
        accounts.put(accountTwo.getAccountNumber(), accountTwo);
    }

    public TransactionResult checkDebitEligibility(String accountNumber, long amount) {
        if (amount <= 0) {
            return TransactionResult.INVALID_REQUEST;
        }
        
        MockAccount account = accounts.get(accountNumber);

        if (account == null) {
            return TransactionResult.ACCOUNT_NOT_FOUND;
        }

        if (account.getBalance() >= amount) {
            return TransactionResult.APPROVED;
        } else {
            return TransactionResult.INSUFFICIENT_FUNDS;
        }
    }

    @Override
    public TransactionResult debit(String accountNumber, long amount) {
        MockAccount account = accounts.get(accountNumber);
        TransactionResult result = checkDebitEligibility(accountNumber, amount);

        if(result == TransactionResult.APPROVED) {
            account.setBalance(account.getBalance() - amount);
        }

        return result;
    }
}
