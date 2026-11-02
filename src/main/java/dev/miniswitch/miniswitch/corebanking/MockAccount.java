package dev.miniswitch.miniswitch.corebanking;

public class MockAccount {
    private String accountNumber;
    private long balance;

    public MockAccount(String accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
