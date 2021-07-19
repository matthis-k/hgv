package kit.pse.hgv.extensionServer;
public class RecieveCommandState implements ClientState {
    private String last_msg = null;

    @Override
    public ClientState nextState() {
        if (last_msg == null || last_msg.equals("quit")) { return new EndState(); }
        return new RecieveCommandState();
    }

    @Override
    public void work(ClientHandler handler) {
        String recieved = handler.recieve();
        if (recieved == null) { return; }
        last_msg = recieved;
        System.out.println("recieved: '" + recieved + "' from: " + handler.getSocket().toString());
        // new CmdProcessor.process(recieved);
        // Command cmd = proc.process(String)
        // memory = cmd
        // proc.queue(cmd)
    }
    
}
