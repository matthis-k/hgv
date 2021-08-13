package kit.pse.hgv.extensionServer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.Socket;

public class ExtensionServerTest {
    private static ExtensionServer server = ExtensionServer.getInstance();

    @BeforeClass
    public static void startServer() {
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
        server.pause(2);
        sleep(100);
        assertEquals(1, server.getClients().keySet().size());
        server.resume(2);
        server.stop(2);
        sleep(100);
        assertEquals(0, server.getClients().keySet().size());
        client1.close();
        client2.close();
    }

    @After
    public void resetServer() {
        server.resetServer();
    }

    @AfterClass
    public static void stopServer() {
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
