package dev.miniswitch.miniswitch;

import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.ISO87BPackager;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOException;
import java.io.IOException;
import org.jpos.iso.BaseChannel;
import java.net.SocketException;

public class MiniSwitchClient {
    private final ISOPackager packager = new ISO87BPackager();
    private BaseChannel channel = new ASCIIChannel("localhost", 5000, packager);

    public void sendMessage(ISOMsg m) throws SocketException {
        this.channel.setTimeout(7000);
        try{
            this.channel.connect();
            this.channel.send(m);
            ISOMsg response = this.channel.receive();
            System.out.println("\n\nReceived response:");
            System.out.println(response.getMTI());
            System.out.println("Field 39: " + response.getString(39));
        } catch (ISOException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.channel.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }    
    }

    public static void main(String[] args) throws ISOException {
        MiniSwitchClient client = new MiniSwitchClient();
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(client.packager);

        isoMsg.setMTI("0200");
        isoMsg.set(3, "000000");       // Processing Code
        isoMsg.set(4, "000000250000"); // Transaction Amount
        isoMsg.set(11, "123456");      // STAN
        isoMsg.set(41, "TERM1");       // Terminal ID

        try {
            client.sendMessage(isoMsg);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
