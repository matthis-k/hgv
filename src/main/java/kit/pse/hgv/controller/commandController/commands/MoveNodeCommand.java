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
            response.put("success", false);
            response.put("reason", "No element with that Id exists.");
            throw new IllegalArgumentException("No Element with that Id exists.");
        }
        GraphSystem.getInstance().getNodeByID(elementId).move(coordinate);
        Double phi = coordinate.getAngle();
        String phiAsString = phi.toString();
        GraphSystem.getInstance().getNodeByID(elementId).setMetadata("phi", phiAsString);
        Double r = coordinate.getDistance();
        String rAsString = r.toString();
        GraphSystem.getInstance().getNodeByID(elementId).setMetadata("r", rAsString);
        modifiedIds.add(elementId);
        modifiedIds.addAll(GraphSystem.getInstance().getEdgeIdsOfNode(elementId));
        response.put("success", true);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }
}
