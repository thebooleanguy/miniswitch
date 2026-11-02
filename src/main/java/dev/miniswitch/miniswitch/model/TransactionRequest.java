package dev.miniswitch.miniswitch.model;

public class TransactionRequest {
    private String mti;
    private String processingCode;
    private long transactionAmount;  // Amount in minor currency units
    private String stan;
    private String terminalId;

    public TransactionRequest(String mti, String processingCode, long transactionAmount, String stan, String terminalId) {
        this.mti = mti;
        this.processingCode = processingCode;
        this.transactionAmount = transactionAmount;
        this.stan = stan;
        this.terminalId = terminalId;
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
}

