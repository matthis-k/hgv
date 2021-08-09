package kit.pse.hgv.extensionServer;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.CommandQListener;

import kit.pse.hgv.controller.commandController.commands.ICommand;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * This singleton Class represents a Server, that listens for input on a
 * specified port. Each Connection is managed by a {@link ClientHandler} on a
 * seperate Thread.
 */
public class ExtensionServer extends Thread implements CommandQListener {
    private static int DEFAULT_PORT = 12345;
    private static ExtensionServer instance;
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
    private DualHashBidiMap<Integer, ClientHandler> handlers = new DualHashBidiMap<>();

    public static ExtensionServer getInstance() {
        if (instance == null) {
            instance = new ExtensionServer(DEFAULT_PORT);
        }
        return instance;
    }

    /**
     * Create A new {@link ExtensionServer} on a specific port.
     * 
     * @param port is the port to listen to.
     */
    private ExtensionServer(int port) {
        setName("ExtensionServer");
        CommandController.getInstance().register(this);
        this.port = port;
        while (socket == null) {
            try {
                this.socket = new ServerSocket(this.port);
            } catch (IOException e) {
                this.port++;
                e.printStackTrace();
            }
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
        ClientHandler handler = new ClientHandler(client, ++nextId);
        handlers.put(handler.getClientId(), handler);
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
        handlers.clear();
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
     * @param clientId is the Id of the client.
     * @param msg      is the message to be sent.
     */
    public void send(int clientId, String msg) {
        ClientHandler handler = handlers.get(clientId);
        if (handler == null) {
            return;
        }
        handler.send(msg);
    }

    @Override
    public void onNotify(ICommand c) {
        if (c.isUser()) {
            return;
        }
        send(c.getClientId(), c.getResponse().toString() + '\n');
    }

    /**
     * @return A {@link HashMap} that maps an Id of a client to a {@link ClientInfo}
     *         for each existing client.
     */
    public HashMap<Integer, ClientInfo> getClients() {
        HashMap<Integer, ClientInfo> clients = new HashMap<>();
        for (int key : handlers.keySet()) {
            clients.put(key, handlers.get(key).getInfo());
        }
        return clients;
    }

    /**
     * Pauses the {@link ClientHander} with the specified Id.
     * 
     * @param clientId is the Id of the {@link ClientHandler} to be paused.
     */
    public void pause(int clientId) {
        ClientHandler handler = handlers.get(clientId);
        if (handler == null) {
            return;
        }
        handler.pauseConnection();
    }

    /**
     * Resumes the {@link ClientHander} with the specified Id.
     * 
     * @param clientId is the Id of the {@link ClientHandler} to be resuemed.
     */
    public void resume(int clientId) {
        ClientHandler handler = handlers.get(clientId);
        if (handler == null) {
            return;
        }
        handler.resumeConnection();
    }

    /**
     * Stops the {@link ClientHander} with the specified Id. A stopped client can
     * not be resumed in the future.
     * 
     * @param clientId is the Id of the {@link ClientHandler} to be resuemed.
     */
    public void stop(int clientId) {
        ClientHandler handler = handlers.get(clientId);
        if (handler == null) {
            return;
        }
        handler.stopConnection();
    }

    void removeClient(int id) {
        handlers.remove(id);
    }
}
