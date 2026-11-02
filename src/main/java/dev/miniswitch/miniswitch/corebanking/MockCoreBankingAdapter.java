package dev.miniswitch.miniswitch.corebanking;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MockCoreBankingAdapter {

    private Map<String, MockAccount> accounts = new HashMap<>();

    public MockCoreBankingAdapter() {
        MockAccount accountOne = new MockAccount("100001",500000);
        MockAccount accountTwo = new MockAccount("100002",150000);

        accounts.put(accountOne.getAccountNumber(), accountOne);
        accounts.put(accountTwo.getAccountNumber(), accountTwo);
    }

    public boolean hasSufficientFunds(String accountNumber, long amount) {
        if (amount <= 0) {
            return false;
        }
        
        MockAccount account = accounts.get(accountNumber);

        if (account == null) {
            return false;
        } else {}

        return account.getBalance() >= amount;
    }

    public boolean debit(String accountNumber, long amount) {
        MockAccount account = accounts.get(accountNumber);

        if(this.hasSufficientFunds(accountNumber, amount)) {
            account.setBalance(account.getBalance() - amount);
            return true;
        }
        System.out.println("Insufficient funds or invalid account number");
        return false;
    }
}
