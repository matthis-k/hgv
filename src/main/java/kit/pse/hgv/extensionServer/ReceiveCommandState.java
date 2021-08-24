package kit.pse.hgv.extensionServer;

import kit.pse.hgv.controller.commandProcessor.ExtensionCommandType;

public class ReceiveCommandState implements ClientState {
    private ClientState nextState = null;

    @Override
    public ClientState nextState() {
        return nextState;
    }

    @Override
    public void work(ClientHandler handler) {
        String recieved = handler.receive();
        if (recieved == null || recieved.length() <= 0) {
            nextState = new ReceiveCommandState();
            return;
        }
        ExtensionCommandType lastCommandType = null;
        try {
            lastCommandType = ExtensionCommandType.processCommandString(recieved, handler.getClientId());
        } catch (IllegalArgumentException e) {
            nextState = new EndState();
        }
        nextState = new ReceiveCommandState();
    }

}
