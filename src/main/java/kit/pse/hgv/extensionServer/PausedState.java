package kit.pse.hgv.extensionServer;

public class PausedState implements ClientState {

    @Override
    public ClientState nextState() {
        return new ReceiveCommandState();
    }

    @Override
    public void work(ClientHandler handler) {
        handler.interrupt();
    }

}
