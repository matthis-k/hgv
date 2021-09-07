package kit.pse.hgv.extensionServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * An instance of this class manages the communication with one specific client.
 */
public class ClientHandler extends Thread {
    private final int id;
    /**
     * Some information about the client.
     */
    private final ClientInfo clientInfo = new ClientInfo();
    /**
     * The socket of the client communicats with.
     */
    private final Socket socket;
    /**
     * OutputStream of the socket.
     */
    private BufferedWriter out;
    /**
     * InputStream of the socket.
     */
    private BufferedReader in;
    /**
     * State of the client.
     */
    private ClientState state;
    /**
     * State of the client, if a state was set via
     * {@link ClientHandler#setState(ClientState)}.
     */
    private ClientState setState = null;
    private boolean isPaused = false;

    /**
     * Creates a new ClientHandler for a specific Socket.
     *
     * @param clientSocket the socket of the client.
     */
    public ClientHandler(Socket clientSocket, int id) {
        setName("ClientHandler" + id);
        clientInfo.setName("Client#" + id);
        clientInfo.setDescription("Client#" + id + " description");
        this.id = id;
        socket = clientSocket;
        try {
            socket.setSoTimeout(50);
        } catch (SocketException e) {
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
        }
        state = new ReceiveCommandState();
    }

    /**
     * Starts the communication loop.
     */
    @Override
    public void run() {
        while (!socket.isClosed() && state != null) {
            state.work(this);
            if (setState == null) {
                state = state.nextState();
            } else {
                state = setState;
                setState = null;
            }
            synchronized (this) {
                if (isPaused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                    isPaused = false;
                }
            }
        }
        ExtensionServer.getInstance().removeClient(getClientId());
    }

    /**
     * Reads a String from the Socket. Waits until something is received if nothing
     * new was sent.
     *
     * @return the next message received
     */
    String receive() {
        try {
            String res = in.readLine();
            return res;
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * Sends a message to the client.
     *
     * @param msg is the message to be send.
     */
    void send(String msg) {
        try {
            out.write(msg);
            out.flush();
        } catch (IOException e) {
        }
    }

    /**
     * @return information about the client
     */
    public ClientInfo getInfo() {
        return clientInfo;
    }

    /**
     * @return Socket of the client
     */
    Socket getSocket() {
        return socket;
    }

    /**
     * @return the client's Id.
     */
    public int getClientId() {
        return id;
    }

    /**
     * Pauses the client.
     */
    void pauseConnection() {
        synchronized (this) {
            isPaused = true;
        }
    }

    void resumeConnection() {
        synchronized (this) {
            notifyAll();
        }
    }

    /**
     * Stops the client.
     */
    void stopConnection() {
        setState(new EndState());
    }

    void setState(ClientState state) {
        setState = state;
    }

}
