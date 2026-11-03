package dev.miniswitch.miniswitch.validation;

import org.springframework.stereotype.Component;

import dev.miniswitch.miniswitch.model.TransactionRequest;

@Component 
public class TransactionValidator {
    public boolean isValid(TransactionRequest request) {
        if(request == null) {
            return false;
        }

        String mti = request.getMti();
        String processingCode = request.getProcessingCode();
        String transactionAmount = request.getTransactionAmount();
        String stan = request.getStan();
        String terminalId = request.getTerminalId();
        String accountNumber = request.getAccountNumber();
        String destinationBankCode = request.getDestinationBankCode();

        if(!("0200".equals(mti))){
            return false;
        }

        if(processingCode == null || !(processingCode.matches("\\d{6}"))){
            return false;
        }

        if(transactionAmount == null || !transactionAmount.matches("\\d{12}") || Long.parseLong(transactionAmount) <= 0) {
            return false;
        }

        if(stan == null || !(stan.matches("\\d{6}")) || "000000".equals(stan)) {
            return false;
        }

        if(terminalId == null || terminalId.isBlank()) {
            return false;
        }

        if(accountNumber == null || accountNumber.isBlank()) {
            return false;
        }

        if(destinationBankCode == null || destinationBankCode.isBlank()) {
            return false;
        }

        return true;
    }
}
