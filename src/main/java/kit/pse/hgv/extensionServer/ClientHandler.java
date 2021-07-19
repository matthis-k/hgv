package kit.pse.hgv.extensionServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private ClientInfo clientInfo;
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private ClientState state;

    @Override
    public void run() {
        while (!socket.isClosed()) {
            state.work(this);
            state = state.nextState();
        }
        System.out.println("Connection closed: " + socket.toString());
    }

    public ClientHandler(Socket clientSocket) throws IOException {
        socket = clientSocket;
        socket.setSoTimeout(1000);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        state = new RecieveCommandState();
    }

    String recieve() {
        try {
            String res = in.readLine();
            return res;
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return new String();
    }

    void send(String msg) {
        try {
            out.write(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientInfo getInfo() {
        return clientInfo;
    }

    Socket getSocket() {
        return socket;
    }
}
