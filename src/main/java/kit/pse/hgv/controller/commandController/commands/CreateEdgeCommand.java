package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.IllegalGraphOperation;
import kit.pse.hgv.graphSystem.exception.OverflowException;

public class CreateEdgeCommand extends GraphSystemCommand {
    private static final String INVALID_NODES = "Diese Knoten können nicht verbunden werden.";

    private final int[] nodeIds;
    private final int graphId;

    /**
     * The constructor creates an element of this class
     *
     * @param graphId the graphId from the graph where the edge should be created
     * @param nodeIds the nodeIds from the Nodes which should be connected
     */
    public CreateEdgeCommand(int graphId, int[] nodeIds) {
        this.graphId = graphId;
        this.nodeIds = nodeIds;
        extendWorkingArea(nodeIds[0]);
        extendWorkingArea(nodeIds[1]);
    }

    @Override
    public void execute() {
        try {
            int addedId = GraphSystem.getInstance().addElement(graphId, nodeIds);
            modifiedIds.add(addedId);
            response.put(ID, addedId);
        } catch (OverflowException e) {
            fail(INVALID_NODES);
            e.printStackTrace();
        } catch (IllegalGraphOperation illegalGraphOperation) {
            fail(INVALID_NODES);
        }
    }


}
