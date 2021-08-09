package kit.pse.hgv.extensionServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.Socket;


public class ExtensionServerTest {
    private ExtensionServer server = ExtensionServer.getInstance();

    @Before
    public void startServer() {
        server.start();
    }

    @Test
    public void testConnections() throws IOException {
        assertEquals(0, server.getClients().keySet().size());
        Socket client1 = new Socket("localhost", server.getPort());
        sleep(100);
        assertEquals(1, server.getClients().keySet().size());
        Socket client2 = new Socket("localhost", server.getPort());
        sleep(100);
        assertEquals(2, server.getClients().keySet().size());
        server.stop(1);
        sleep(100);
        assertEquals(1, server.getClients().keySet().size());
    }

    @After
    public void cleanup() {
        server.stopServer();
    }

    private void sleep(int ms) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
