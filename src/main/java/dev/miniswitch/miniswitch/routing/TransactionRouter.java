package dev.miniswitch.miniswitch.routing;

import org.springframework.stereotype.Component;
import dev.miniswitch.miniswitch.model.TransactionRoute;
import dev.miniswitch.miniswitch.model.TransactionRequest;

@Component 
public class TransactionRouter {
    private static final String ON_US_BANK_CODE = "001001";
    private static final String BANK_TWO_CODE = "001002";
    private static final String BANK_THREE_CODE = "001003";
    public TransactionRoute determineRoute(TransactionRequest request) {

        if (request == null) {
            return TransactionRoute.INVALID_DESTINATION;
        }
        String destinationBankCode = request.getDestinationBankCode();
        if (destinationBankCode == null || destinationBankCode.isBlank()) {
            return TransactionRoute.INVALID_DESTINATION;
        }

        if (ON_US_BANK_CODE.equals(destinationBankCode)) {
            return TransactionRoute.ON_US;
        } else if (BANK_TWO_CODE.equals(destinationBankCode) 
            || BANK_THREE_CODE.equals(destinationBankCode)) {
            return TransactionRoute.OFF_US;
        } else {
            return TransactionRoute.INVALID_DESTINATION;
        }
    }
}
