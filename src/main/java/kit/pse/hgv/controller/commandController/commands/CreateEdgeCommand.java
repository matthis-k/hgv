package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;

public class CreateEdgeCommand extends CreateElementCommand {
    private int[] nodeIds;

    /**
     * The constructor creates an element of this class
     * 
     * @param graphId the graphId from the graph where the edge should be created
     * @param nodeIds the nodeIds from the Nodes which should be connected
     */
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
        //TODO
    }
}
