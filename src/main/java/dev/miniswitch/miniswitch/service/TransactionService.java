package dev.miniswitch.miniswitch.service;

import org.springframework.stereotype.Service;
import dev.miniswitch.miniswitch.validation.TransactionValidator;
import dev.miniswitch.miniswitch.model.TransactionRequest;
import dev.miniswitch.miniswitch.model.TransactionResult;
import dev.miniswitch.miniswitch.corebanking.CoreBankingPort;
import dev.miniswitch.miniswitch.model.TransactionRoute;
import dev.miniswitch.miniswitch.routing.TransactionRouter;

@Service 
public class TransactionService {

    private final TransactionValidator transactionValidator;
    private final CoreBankingPort coreBankingPort;
    private final TransactionRouter transactionRouter;

    public TransactionService(TransactionValidator transactionValidator, 
        CoreBankingPort coreBankingPort, TransactionRouter transactionRouter) {
        this.transactionValidator = transactionValidator;
        this.coreBankingPort = coreBankingPort;
        this.transactionRouter = transactionRouter;
    }

    public TransactionResult processTransaction(TransactionRequest request) {
        if (!transactionValidator.isValid(request)) {
            return TransactionResult.INVALID_REQUEST;
        }

        if (transactionRouter.determineRoute(request) != TransactionRoute.ON_US) {
            return TransactionResult.INVALID_REQUEST;
        }

        switch (transactionRouter.determineRoute(request)) {
            case ON_US:
                String accountNumber = request.getAccountNumber();
                long transactionAmount = request.getTransactionAmount();
                return coreBankingPort.debit(accountNumber, transactionAmount);
            case OFF_US:
                return TransactionResult.INVALID_REQUEST; // External routing not implemented yet
            case INVALID_DESTINATION:
                return TransactionResult.INVALID_REQUEST;
            default:
                throw new IllegalStateException("Unexpected transaction route: " 
                + transactionRouter.determineRoute(request));
        }

        
    }
    
}
