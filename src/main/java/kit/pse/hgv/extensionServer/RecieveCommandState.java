package kit.pse.hgv.extensionServer;
import kit.pse.hgv.controller.commandProcessor.ExtensionCommandType;
public class RecieveCommandState implements ClientState {
    private ClientState nextState = null;

    @Override
    public ClientState nextState() {
        return nextState;
    }

    @Override
    public void work(ClientHandler handler) {
        String recieved = handler.recieve();
        if (recieved == null || recieved.length() <= 0) { nextState = new RecieveCommandState(); return; }
        System.out.println("recieved: '" + recieved + "' from: " + handler.getSocket().toString());
        ExtensionCommandType lastCommandType = null;
        try {
            lastCommandType = ExtensionCommandType.processCommandString(recieved, handler.getClientId());
        } catch (IllegalArgumentException e) {
            nextState = new EndState();
        }
        nextState = new RecieveCommandState();
    }
    
}
