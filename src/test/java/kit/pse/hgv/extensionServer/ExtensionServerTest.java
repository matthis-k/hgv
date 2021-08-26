package kit.pse.hgv.extensionServer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.CommandEventSource;
import kit.pse.hgv.controller.commandController.CommandQListener;
import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.controller.commandController.commands.SendGraphCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Vector;

public class ExtensionServerTest {
    private static ExtensionServer server = ExtensionServer.getInstance();

    @BeforeClass
    public static void startServer() {
        server.start();
    }

    @Test
    public void testConnections() throws IOException, InterruptedException {
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
    public void send() throws UnknownHostException, IOException, InterruptedException {
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

    @Test(expected = SocketTimeoutException.class)
    public void onNotify() throws UnknownHostException, IOException, InterruptedException {
        CommandEventSource emitter = new CommandEventSource() {
            private Vector<CommandQListener> listeners = new Vector<>();

            @Override
            public void register(CommandQListener listener) {
                listeners.add(listener);
            }

            @Override
            public void notifyAll(ICommand c) {
                for (CommandQListener listener : listeners) {
                    listener.onNotify(c);
                }
            }
        };
        emitter.register(server);
        Socket client = new Socket("localhost", server.getPort());
        client.setSoTimeout(100);
        ICommand c = new SendGraphCommand(1);
        c.setClientId(1);
        emitter.notifyAll(c);
        sleep(100);
        try {
            String received = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
            assertEquals("{}", received);
        } catch (SocketTimeoutException e) {
            assertTrue("timeout ocuured", false);
        }
        c.setClientId(0);
        emitter.notifyAll(c);
        String received = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
    }

    @After
    public void resetServer() {
        server.resetServer();
    }

    @AfterClass
    public static void stopServer() {
        server.stopServer();
    }

    private void sleep(int ms) throws InterruptedException {
        synchronized (this) {
            wait(ms);
        }
    }
}
