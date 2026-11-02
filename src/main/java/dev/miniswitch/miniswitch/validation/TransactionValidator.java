package dev.miniswitch.miniswitch.validation;

import dev.miniswitch.miniswitch.model.TransactionRequest;

public class TransactionValidator {
    public boolean isValid(TransactionRequest request) {
        if(request == null) {
            return false;
        }

        String mti = request.getMti();
        String processingCode = request.getProcessingCode();
        long transactionAmount = request.getTransactionAmount();
        String stan = request.getStan();
        String terminalId = request.getTerminalId();

        if(!("0200".equals(mti))){
            return false;
        }

        if(processingCode == null || !(processingCode.matches("\\d{6}"))){
            return false;
        }

        if(transactionAmount <= 0) {
            return false;
        }

        if(stan == null || !(stan.matches("\\d{6}")) || "000000".equals(stan)) {
            return false;
        }

        if(terminalId == null || terminalId.isBlank()) {
            return false;
        }

        return true;
    }
}
