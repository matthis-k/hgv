package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;

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
            int addedId = GraphSystem.getInstance().addElement(graphId, nodeIds);
            modifiedIds.add(addedId);
            response.put("success", true);
            response.put("id", addedId);
        } catch (OverflowException e) {
            response.put("success", false);
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {
        //TODO
    }
}
