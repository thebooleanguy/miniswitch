package dev.miniswitch.miniswitch;

import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.ISO87BPackager;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.ISOServer;
import org.jpos.util.ThreadPool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component 
public class MiniSwitchServer {
    private ISOServer server;
    private Thread serverThread;

    @PostConstruct
    public void start() {
        try {
            ISOPackager packager = new ISO87BPackager();
            ServerChannel channel = new ASCIIChannel(packager);
            server = new ISOServer(5000, channel, new ThreadPool(1, 10));
            server.addISORequestListener(new MiniSwitchListener());
            serverThread = new Thread(server);
            serverThread.start();
            System.out.println("Starting Miniswitch server on port 5000...");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to start MiniSwitch server", e);
        }
    }

    @PreDestroy 
    public void stop() {
        if (server != null) {
            server.shutdown();
            System.out.println("Stopping Miniswitch server...");
            try {
                serverThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Failed to stop MiniSwitch server", e);
            }
            
            if(serverThread.isAlive()) {
                System.err.println("Server thread did not terminate in time, interrupting...");
                serverThread.interrupt();
            }
        }
    }
    
}
