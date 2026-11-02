package dev.miniswitch.miniswitch.service;

import org.springframework.stereotype.Service;
import dev.miniswitch.miniswitch.validation.TransactionValidator;
import dev.miniswitch.miniswitch.model.TransactionRequest;

@Service 
public class TransactionService {

    private final TransactionValidator transactionValidator;

    public TransactionService(TransactionValidator transactionValidator) {
        this.transactionValidator = transactionValidator;
    }

    public boolean processTransaction(TransactionRequest request) {
        return transactionValidator.isValid(request);
    }
    
}
