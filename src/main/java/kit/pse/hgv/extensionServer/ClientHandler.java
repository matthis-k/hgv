package kit.pse.hgv.extensionServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * An instance of this class manages the communication with one specific client.
 */
public class ClientHandler extends Thread {
    private int id;
    /**
     * Some information about the client.
     */
    private ClientInfo clientInfo = new ClientInfo();
    /**
     * The socket of the client communicats with.
     */
    private Socket socket;
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
     * Starts the communication loop.
     */
    @Override
    public void run() {
        while (!socket.isClosed()) {
            state.work(this);
            state = state.nextState();
        }
    }

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
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        state = new RecieveCommandState();
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
        } catch (SocketTimeoutException e) {
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException _e) {
            }
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
        interrupt();
        state = new PausedState();
    }

    /**
     * Resumes the client.
     */
    void resumeConnection() {
        interrupt();
        state = state.nextState();
        start();
    }

    /**
     * Stops the client.
     */
    void stopConnection() {
        interrupt();
        state = new EndState();
        start();
    }

}
