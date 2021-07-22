package kit.pse.hgv.extensionServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * This Class represents a Server, that listens for input on a specified port.
 * Each Connection is managed by a {@link ClientHandler} on a seperate Thread.
 */
public class ExtensionServer extends Thread {
    /**
     * Stores the Id of the next {@link ClientHandler}.
     */
    private int nextId = 0;
    /**
     * The port of the {@link ServerSocket}.
     */
    private int port;
    /**
     * The socket of the server.
     */
    private ServerSocket socket;
    /**
     * A Collection of all ClientHandlers that are active.
     */
    private HashMap<Integer, ClientHandler> handlers = new HashMap<>();

    /**
     * Create A new {@link ExtensionServer} on a specific port.
     * 
     * @param port is the port to listen to.
     */
    public ExtensionServer(int port) {
        this.port = port;
        try {
            this.socket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the Server on a new Thread.
     */
    @Override
    public void run() {
        while (!socket.isClosed()) {
            Socket client = null;
            try {
                client = socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not accept client");
                continue;
            }
            startCommunication(client);
        }
    }

    /**
     * Creates a {@link ClientHandler} and starts the communication of with the
     * client.
     * 
     * @param client is the socket of the client
     */
    private void startCommunication(Socket client) {
        ClientHandler handler = new ClientHandler(client);
        handlers.put(++nextId, handler);
        handler.start();
    }

    /**
     * Stops the server and closes all connections to clients.
     */
    public void stopServer() {
        interrupt();
        for (int handlerId : handlers.keySet()) {
            ClientHandler handler = handlers.get(handlerId);
            handler.interrupt();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not close server socket");
        }
    }

    /**
     * Sends a message to the client with the specified id.
     * 
     * @param clientId is the Id of the client
     * @param msg      is the message to be sent.
     */
    public void send(int clientId, String msg) {
        ClientHandler handler = handlers.get(clientId);
        if (handler == null) {
            return;
        }
        handler.send(msg);
    }
}
