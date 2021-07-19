package kit.pse.hgv.extensionServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ExtensionServer extends Thread {
    private int nextId = 0;
    private int port;
    private ServerSocket socket;
    private HashMap<Integer, ClientHandler> handlers = new HashMap<>();


    public ExtensionServer(int port) throws IOException {
        this.port = port;
        this.socket = new ServerSocket(this.port);
    }

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

    private void startCommunication(Socket client) {
        try {
            ClientHandler handler = new ClientHandler(client);
            handlers.put(++nextId, handler);
            handler.start();
            System.out.println("New Connection" + client.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        interrupt();
        for(ClientHandler handler : handlers.values()) {
            handler.interrupt();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not close server socket");
        }
    }

    public void send(int clientId, String msg) {
        ClientHandler handler = handlers.get(clientId);
        if (handler == null) { return; }
        handler.send(msg);
    }

    public static void main(String[] args) {
        ExtensionServer server;
        try {
            server = new ExtensionServer(12345);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server Creation failed");
        }
    }
}
