package dev.miniswitch.miniswitch.transport;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.springframework.stereotype.Component;
import org.jpos.iso.ISOException;
import java.io.IOException;
import dev.miniswitch.miniswitch.service.TransactionService;
import dev.miniswitch.miniswitch.model.TransactionRequest;

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

            if (!m.hasField(3) || !m.hasField(4) || !m.hasField(11) || !m.hasField(41) || !m.hasField(102)) {
                System.out.println("Missing required fields, ignoring...");
                r.set(39, "12"); // Invalid transaction
                source.send(r);
                return true;
            }

            System.out.println("Field 3: " + m.getString(3));
            System.out.println("Field 4: " + m.getString(4));
            System.out.println("Field 11: " + m.getString(11));
            System.out.println("Field 41: " + m.getString(41));
            System.out.println("Field 102: " + m.getString(102));

            TransactionRequest request = new TransactionRequest(m.getMTI(), m.getString(3), 
                Long.parseLong(m.getString(4)), m.getString(11), m.getString(41), m.getString(102));

            if (!transactionService.processTransaction(request)) {
                System.out.println("Transaction validation failed");
                r.set(39, "12"); // Invalid transaction
                source.send(r);
                return true;
            } 

            if (("000000250000".equals(m.getString(4)))) {
                r.set(39, "00");
            } else {
                r.set(39, "05");
                System.out.println("Transaction amount not accepted");
            }

            source.send(r);
            return true;
            
        } catch (ISOException | IOException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Invalid transaction amount format");
            try {
                r.set(39, "12"); // Invalid transaction
                source.send(r);
                return true;
            } catch (ISOException | IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }

        
        

        
        
    
    }

}
