package kit.pse.hgv.commandController.commands;

public class SendGraphCommand extends ExtensionCommand{
    private int graphId;
    private int clientId;

    public SendGraphCommand(int graphId, int clientId) {
        this.graphId = graphId;
        this.clientId = clientId;
    }

    public void execute() {
        
    }
}
