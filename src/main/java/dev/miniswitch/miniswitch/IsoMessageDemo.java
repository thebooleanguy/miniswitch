
package dev.miniswitch.miniswitch;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.ISO87BPackager;

public class IsoMessageDemo {

    private final ISOPackager packager = new ISO87BPackager();

    public byte[] createAndPackMessage() throws ISOException {
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);

        isoMsg.setMTI("0200");
        isoMsg.set(3, "000000");       // Processing Code
        isoMsg.set(4, "000000250000"); // Transaction Amount
        isoMsg.set(11, "123456");      // STAN
        isoMsg.set(41, "TERM1");       // Terminal ID

        return isoMsg.pack();
    }

    public void unpackAndPrint(byte[] packedMessage) throws ISOException {
        ISOMsg unpackedMsg = new ISOMsg();
        unpackedMsg.setPackager(packager);
        unpackedMsg.unpack(packedMessage);

        System.out.println("\n\nMTI: " + unpackedMsg.getMTI());
        System.out.println("Field 3: " + unpackedMsg.getString(3));
        System.out.println("Field 4: " + unpackedMsg.getString(4));
        System.out.println("Field 11: " + unpackedMsg.getString(11));
        System.out.println("Field 41: " + unpackedMsg.getString(41));
    }

    public static void main(String[] args) throws ISOException {
        IsoMessageDemo demo = new IsoMessageDemo();

        byte[] packedMessage = demo.createAndPackMessage();

        System.out.println("Packed message:");
        for (byte b : packedMessage) {
            System.out.printf("%02X ", b & 0xFF);
        }

        demo.unpackAndPrint(packedMessage);
    }
}
