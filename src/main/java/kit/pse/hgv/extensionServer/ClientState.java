package kit.pse.hgv.extensionServer;


public interface ClientState {
    public ClientState nextState();
    public void work(ClientHandler handler);
}
