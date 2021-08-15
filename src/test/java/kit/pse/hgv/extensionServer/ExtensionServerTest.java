package kit.pse.hgv.extensionServer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

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
        sleep(100);
        assertEquals(1, server.getClients().keySet().size());
        client1.close();
        client2.close();
    }

    @Test
    public void send() throws UnknownHostException, IOException {
        Socket client = new Socket("localhost", server.getPort());
        String msg = "testmessage\n";
        sleep(100);
        server.send(1, msg);
        sleep(100);
        String received = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
        assertTrue(msg.substring(0, msg.length() - 1).contentEquals(received));
        client.close();
    }

    @Test
    public void nonexistingClient() {
        server.resume(7);
        server.pause(8);
        server.stop(8);
        server.send(8, "");
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
