package kit.pse.hgv.controller.commandController.commands;

/**
 * TODO
 */
public class SendGraphCommand extends ExtensionCommand {
    private int graphId;
    private int clientId;

    public SendGraphCommand(int graphId, int clientId) {
        this.graphId = graphId;
        this.clientId = clientId;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void undo() {

    }
}
