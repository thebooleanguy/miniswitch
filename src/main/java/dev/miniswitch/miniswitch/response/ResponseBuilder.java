package dev.miniswitch.miniswitch.response;

import dev.miniswitch.miniswitch.model.TransactionResult;
import org.jpos.iso.ISOMsg;
import org.springframework.stereotype.Component;
import org.jpos.iso.ISOException;

@Component 
public class ResponseBuilder {
    public ISOMsg buildResponse(ISOMsg request, TransactionResult transactionResult) throws ISOException {
        if(transactionResult == null) {
            throw new IllegalArgumentException("TransactionResult cannot be null");
        }
        
        ISOMsg response = (ISOMsg) request.clone();
        
        response.setResponseMTI();

        switch(transactionResult) {
                case APPROVED:
                    response.set(39, "00");
                    break;
                case INVALID_REQUEST:
                    response.set(39, "12");
                    break;
                case INSUFFICIENT_FUNDS:
                    response.set(39, "51");
                    break;
                case ACCOUNT_NOT_FOUND:
                    response.set(39, "14");
                    break;
                default:
                    response.set(39, "96");
            }
         
        return response;
    }
}
