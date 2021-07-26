package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;

/**
 * This class handles the creation and undo of a creation of an edge
 */
public class CreateEdgeCommand extends GraphSystemCommand {
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
            GraphSystem.getInstance().addElement(graphId, nodeIds);
        } catch (OverflowException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {
        //TODO
    }
}
