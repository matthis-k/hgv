package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;

public class CreateEdgeCommand extends CreateElementCommand {
    private int[] nodeIds;
    public CreateEdgeCommand(int graphId, int[] nodeIds) {
        super(graphId);
        this.nodeIds = nodeIds;
    }
    @Override
    public void execute() {
        try {
            addedId = GraphSystem.getInstance().addElement(graphId, nodeIds);
        } catch (OverflowException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {

    }
}
