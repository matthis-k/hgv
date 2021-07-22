package kit.pse.hgv.extensionServer;
import java.io.IOException;

public class EndState implements ClientState {

    @Override
    public ClientState nextState() {
        return null;
    }

    @Override
    public void work(ClientHandler handler) {
        try {
            handler.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
