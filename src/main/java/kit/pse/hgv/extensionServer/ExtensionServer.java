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
    private static ExtensionServer instance = new ExtensionServer(DEFAULT_PORT);
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
            }
        }
        System.out.println(getPort());
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
        resetServer();
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    void resetServer() {
        for (int handlerId : handlers.keySet()) {
            ClientHandler handler = handlers.get(handlerId);
            handler.interrupt();
        }
        nextId = 0;
        handlers.clear();
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
        handler.send(msg + "\n");
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
     * Pauses the {@link ClientHandler} with the specified Id.
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
     * Resumes the {@link ClientHandler} with the specified Id.
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
     * Stops the {@link ClientHandler} with the specified Id. A stopped client can
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

    /**
     * Removes a {@link ClientHandler} from {@link ExtensionServer#handlers}.
     *
     * @param id is the Id of the {@link ClientHandler} that will be removed.
     */
    void removeClient(int id) {
        handlers.remove(id);
    }

    /**
     * @return the port of the {@link ExtensionServer#socket}.
     */
    public int getPort() {
        return port;
    }
}
