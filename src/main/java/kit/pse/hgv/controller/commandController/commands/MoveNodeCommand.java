package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.PolarCoordinate;

/**
 * This class handles the commands that let the user move nodes
 */
public class MoveNodeCommand extends GraphSystemCommand {
    private int elementId;
    private PolarCoordinate coordinate;

    /**
     * The constructor creates an element of this class
     * 
     * @param elementId  ElementId of the Element that should be moved
     * @param coordinate Coordinate of the new position of the element
     */
    public MoveNodeCommand(int elementId, PolarCoordinate coordinate) {
        this.elementId = elementId;
        this.coordinate = coordinate;
        extendWorkingArea(elementId);
    }

    @Override
    public void execute() {
        if (GraphSystem.getInstance().getGraphElementByID(elementId) == null) {
            fail(NO_ELEMENT_WITH_ID);
            return;
        }
        GraphSystem.getInstance().getNodeByID(elementId).move(coordinate);
        modifiedIds.add(elementId);
        modifiedIds.addAll(GraphSystem.getInstance().getEdgeIdsOfNode(elementId));
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }
}
