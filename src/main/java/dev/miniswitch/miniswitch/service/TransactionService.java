package dev.miniswitch.miniswitch.service;

import org.springframework.stereotype.Service;
import dev.miniswitch.miniswitch.validation.TransactionValidator;
import dev.miniswitch.miniswitch.model.TransactionRequest;
import dev.miniswitch.miniswitch.corebanking.MockCoreBankingAdapter;
import dev.miniswitch.miniswitch.model.TransactionResult;

@Service 
public class TransactionService {

    private final TransactionValidator transactionValidator;
    private final MockCoreBankingAdapter coreBankingAdapter;

    public TransactionService(TransactionValidator transactionValidator, MockCoreBankingAdapter coreBankingAdapter) {
        this.transactionValidator = transactionValidator;
        this.coreBankingAdapter = coreBankingAdapter;
    }

    public TransactionResult processTransaction(TransactionRequest request) {
        if (!transactionValidator.isValid(request)) {
            return TransactionResult.INVALID_REQUEST;
        }

        String accountNumber = request.getAccountNumber();
        long transactionAmount = request.getTransactionAmount();

        return coreBankingAdapter.debit(accountNumber, transactionAmount);
    }
    
}
