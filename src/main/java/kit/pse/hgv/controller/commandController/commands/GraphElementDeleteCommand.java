package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Node;

/**
 * This class handles the commands that delete elements or undo the delete
 * command
 */
public class GraphElementDeleteCommand extends GraphSystemCommand {
    private final int elementId;
    private int graphID = -1;

    /**
     * The constructor creates an element of this class
     *
     * @param elementId ElementId from the Element that should be deleted
     */
    public GraphElementDeleteCommand(int elementId) {
        this.elementId = elementId;
        extendWorkingArea(elementId);
        Node n = GraphSystem.getInstance().getNodeByID(elementId);
        if (n != null) {
            for (int e : GraphSystem.getInstance().getEdgeIdsOfNode(elementId)) {
                extendWorkingArea(e);
            }
        }
    }

    public GraphElementDeleteCommand(int elementId, int graphID) {
        this.elementId = elementId;
        extendWorkingArea(elementId);
        Node n = GraphSystem.getInstance().getNodeByID(elementId);
        if (n != null) {
            for (int e : GraphSystem.getInstance().getEdgeIdsOfNode(elementId)) {
                extendWorkingArea(e);
            }
        }
        this.graphID = graphID;
    }

    @Override
    public void execute() {
        if (GraphSystem.getInstance().getGraphElementByID(elementId) == null) {
            fail(NO_ELEMENT_WITH_ID);
        } else if (graphID != -1 && !GraphSystem.getInstance().isInGraph(graphID, elementId)) {
            fail(NO_ELEMENT_WITH_ID);
        } else {
            modifiedIds.addAll(GraphSystem.getInstance().removeElement(elementId));
            modifiedIds.add(elementId);
        }
    }

    @Override
    public void undo() {
    }
}
