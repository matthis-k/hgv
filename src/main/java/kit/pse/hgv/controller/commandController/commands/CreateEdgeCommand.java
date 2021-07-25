package kit.pse.hgv.controller.commandController.commands;

public class CreateEdgeCommand extends GraphSystemCommand {
    private int[] nodeIds;
    public CreateEdgeCommand(int graphId, int[] nodeIds) {
        super(graphId);
        this.nodeIds = nodeIds;
    }
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
