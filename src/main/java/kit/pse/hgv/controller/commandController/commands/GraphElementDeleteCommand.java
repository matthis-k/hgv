package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.view.uiHandler.EditHandler;
import kit.pse.hgv.view.uiHandler.RenderHandler;

/**
 * This class handles the commands that delete elements or undo the delete
 * command
 */
public class GraphElementDeleteCommand extends GraphSystemCommand {
    private final int elementId;

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

    @Override
    public void execute() {
        GraphElement element = GraphSystem.getInstance().getGraphElementByID(elementId);
        if (element == null) {
            fail(NO_ELEMENT_WITH_ID);
        } else if(!GraphSystem.getInstance().isInGraph(RenderHandler.getInstance().getCurrentID(), elementId)){
            fail(WRONG_GRAPH);
        } else {
            modifiedIds.addAll(GraphSystem.getInstance().removeElement(elementId));
            modifiedIds.add(elementId);
        }
    }

    @Override
    public void undo() {
    }
}
