package kit.pse.hgv.extensionServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.BeforeClass;
import org.junit.Test;

public class ClientHandlerTest {
    private static ClientHandler handler;
    private static ServerSocket server;
    private static Socket client;
    private static int port = 12345;

    @BeforeClass
    public static void init() throws IOException {
        while (server == null) {
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                port++;
            }
        }
        client = new Socket("localhost", port);
        handler = new ClientHandler(server.accept(), 1);
    }

    @Test
    public void receiveFromClient() throws IOException, InterruptedException {
        BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        String message = "testmessage\n";
        sender.write(message);
        sender.flush();
        sleep(100);
        String received = handler.receive();
        // the \n is removed by the readLine() call
        assertTrue(message.substring(0, message.length() - 1).contentEquals(received));
    }

    @Test
    public void sendToClient() throws IOException, InterruptedException {
        String message = "testmessage\n";
        handler.send(message);
        BufferedReader receiver = new BufferedReader(new InputStreamReader(client.getInputStream()));
        sleep(100);
        String received = receiver.readLine();
        // the \n is removed by the readLine() call
        assertTrue(message.substring(0, message.length() - 1).contentEquals(received));
    }

    @Test
    public void receiveNull() {
        String received = handler.receive();
        assertEquals(null, received);
    }

    @Test
    public void pauseResumeHandler() throws InterruptedException {
        handler.start();
        handler.pauseConnection();
        sleep(100);
        assertEquals(Thread.State.WAITING, handler.getState());
        handler.resumeConnection();
        sleep(100);
        assertEquals(Thread.State.RUNNABLE, handler.getState());
        handler.stopConnection();
        sleep(100);
    }

    private void sleep(int ms) throws InterruptedException {
        synchronized (this) {
            wait(ms);
        }
    }
}
