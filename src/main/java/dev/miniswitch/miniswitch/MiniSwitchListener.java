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
            System.out.println("Field 3: " + m.getString(3));
            System.out.println("Field 4: " + m.getString(4));
            System.out.println("Field 11: " + m.getString(11));

            ISOMsg r = (ISOMsg) m.clone();
            r.setResponseMTI();
            r.set(39, "00"); // Response Code

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
