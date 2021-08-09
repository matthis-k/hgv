package kit.pse.hgv.extensionServer;

public class PausedState implements ClientState {

    @Override
    public ClientState nextState() {
        return new RecieveCommandState();
    }

    @Override
    public void work(ClientHandler handler) {
        handler.interrupt();
    }

}
