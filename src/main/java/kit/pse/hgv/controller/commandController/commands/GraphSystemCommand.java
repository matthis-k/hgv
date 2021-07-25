package kit.pse.hgv.controller.commandController.commands;


public abstract class GraphSystemCommand implements Command {
    protected int graphId;
    public GraphSystemCommand(int graphId) {
        this.graphId = graphId;
    }
    public GraphSystemCommand() {
    }
}
