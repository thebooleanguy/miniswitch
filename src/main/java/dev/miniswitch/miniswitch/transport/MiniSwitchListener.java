package dev.miniswitch.miniswitch.transport;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.springframework.stereotype.Component;
import org.jpos.iso.ISOException;
import java.io.IOException;
import dev.miniswitch.miniswitch.service.TransactionService;
import dev.miniswitch.miniswitch.model.TransactionRequest;
import dev.miniswitch.miniswitch.model.TransactionResult;

@Component 
public class MiniSwitchListener implements ISORequestListener{
    private final TransactionService transactionService;

    public MiniSwitchListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public boolean process(ISOSource source, ISOMsg m) {
        ISOMsg r = new ISOMsg();

        try {
            System.out.println("\n\nMTI: " + m.getMTI());    
            
            if ("0200".equals(m.getMTI())) {
                System.out.println("Processing transaction request...");
                r = (ISOMsg) m.clone();
                r.setResponseMTI();
            } else {
                System.out.println("Received non-transaction request, ignoring...");
                return false;
            }

            if (!m.hasField(3) || !m.hasField(4) || !m.hasField(11) || !m.hasField(41) 
                || !m.hasField(102) || !m.hasField(100)) {
                System.out.println("Missing required fields, ignoring...");
                r.set(39, "12");
                source.send(r);
                return true;
            }

            System.out.println("Field 3: " + m.getString(3));
            System.out.println("Field 4: " + m.getString(4));
            System.out.println("Field 11: " + m.getString(11));
            System.out.println("Field 41: " + m.getString(41));
            System.out.println("Field 102: " + m.getString(102));
            System.out.println("Field 100: " + m.getString(100));


            TransactionRequest request = new TransactionRequest(m.getMTI(), m.getString(3), 
                Long.parseLong(m.getString(4)), m.getString(11), m.getString(41), 
                m.getString(102), m.getString(100));

            TransactionResult result = transactionService.processTransaction(request);

            switch(result) {
                case APPROVED:
                    System.out.println("Transaction approved");
                    r.set(39, "00");
                    break;
                case INVALID_REQUEST:
                    System.out.println("Invalid transaction request");
                    r.set(39, "12");
                    break;
                case INSUFFICIENT_FUNDS:
                    System.out.println("Insufficient funds");
                    r.set(39, "51");
                    break;
                case ACCOUNT_NOT_FOUND:
                    System.out.println("Account not found");
                    r.set(39, "14");
                    break;
                default:
                    System.out.println("Unknown transaction result");
                    r.set(39, "96");
            }
        
            source.send(r);
            return true;
            
        } catch (ISOException | IOException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Invalid transaction amount format");
            try{
                r.set(39, "12");
                source.send(r);
                return true;
            } catch (ISOException | IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

}
