package dev.miniswitch.miniswitch;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOException;
import java.io.IOException;

public class MiniSwitchListener implements ISORequestListener{
    public boolean process(ISOSource source, ISOMsg m) {

        try {
            System.out.println("\n\nMTI: " + m.getMTI());    

            ISOMsg r = new ISOMsg();
            
            
            if ("0200".equals(m.getMTI())) {
                System.out.println("Processing transaction request...");
                r = (ISOMsg) m.clone();
                r.setResponseMTI();
            } else {
                System.out.println("Received non-transaction request, ignoring...");
                return false;
            }

            if (!m.hasField(3) || !m.hasField(4) || !m.hasField(11)) {
                System.out.println("Missing required fields, ignoring...");
                r.set(39, "12"); // Invalid transaction
                source.send(r);
                return true;
            }

            System.out.println("Field 3: " + m.getString(3));
            System.out.println("Field 4: " + m.getString(4));
            System.out.println("Field 11: " + m.getString(11));

            if (("000000250000".equals(m.getString(4)))) {
                r.set(39, "00");
            } else {
                r.set(39, "05");
                System.out.println("Transaction amount not accepted");
            }

            source.send(r);
            return true;
            
        } catch (ISOException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        
        

        
        
    
    }

}
