package dev.miniswitch.miniswitch.model;

public class TransactionRequest {
    private String mti;
    private String processingCode;
    private long transactionAmount;  // Amount in minor currency units
    private String stan;
    private String terminalId;
    private String accountNumber;
    private String destinationBankCode;;

    public TransactionRequest(String mti, String processingCode, long transactionAmount, 
        String stan, String terminalId, String accountNumber, String destinationBankCode) {
        this.mti = mti;
        this.processingCode = processingCode;
        this.transactionAmount = transactionAmount;
        this.stan = stan;
        this.terminalId = terminalId;
        this.accountNumber = accountNumber;
        this.destinationBankCode = destinationBankCode;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDestinationBankCode() {
        return destinationBankCode;
    }

    public void setDestinationBankCode(String destinationBankCode) {
        this.destinationBankCode = destinationBankCode;
    }
}

