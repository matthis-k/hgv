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
        if (recieved == null) { return; }
        System.out.println("recieved: '" + recieved + "' from: " + handler.getSocket().toString());
        ExtensionCommandType lastCommandType = ExtensionCommandType.processCommandString(recieved);
        switch (lastCommandType) {
            default: nextState = new RecieveCommandState(); break;
        }

    }
    
}
