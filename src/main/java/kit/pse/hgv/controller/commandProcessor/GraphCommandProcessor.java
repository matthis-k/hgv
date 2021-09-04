package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

/**
 * This class processes the input from the ui that affects the graph itself
 */
public class GraphCommandProcessor implements CommandProcessor {

    @Override
    public void queueCommand(ICommand command) {
        CommandController.getInstance().queueCommand(command);
    }

    /**
     * This method creates an createEdgeCommand
     * 
     * @param graphId where to create an edge
     * @param id1     first nodeId that should be connected
     * @param id2     second nodeId that should be connected
     */
    public void addEdge(int graphId, int id1, int id2) {
        int[] nodeIds = new int[2];
        nodeIds[0] = id1;
        nodeIds[1] = id2;
        CreateEdgeCommand command = new CreateEdgeCommand(graphId, nodeIds);
        queueCommand(command);
    }

    public void addEdge(int graphId, String id1, String id2) {
        try {
            int first = Integer.parseInt(id1);
            int second = Integer.parseInt(id2);

            addEdge(graphId, first, second);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("TODO");
        }
    }

    /**
     * This method checks if the coordinate of the node is in the correct format and
     * creates a createNodeCommand
     * 
     * @param graphId     where to create a node
     * @param phiAsString phi-Coordinate as string
     * @param rAsString   r-Coordinate as string
     */
    public void addNode(int graphId, String phiAsString, String rAsString) {
        try {
            Double phi = Double.valueOf(phiAsString);
            Double r = Double.valueOf(rAsString);
            Coordinate coordinate = new PolarCoordinate(phi, r);
            CreateNodeCommand command = new CreateNodeCommand(graphId, coordinate);
            queueCommand(command);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("TODO");
        }
    }

    /**
     * This method checks if the coordinate (where to move) is in the correct format
     * and creates a moveNodeCommand
     * 
     * @param elementId   id of the node
     * @param phiAsString x-Coordinate as string
     * @param rAsString   y-Coordinate as string
     */
    public void moveNode(int elementId, String phiAsString, String rAsString) {
        try {
            Double phi = Double.valueOf(phiAsString);
            Double r = Double.valueOf(rAsString);
            PolarCoordinate coordinate = new PolarCoordinate(phi, r);
            MoveNodeCommand command = new MoveNodeCommand(elementId, coordinate);
            queueCommand(command);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("TODO");
        }
    }

    /**
     * This method creates a deleteCommand
     * 
     * @param elementId id of the element that should be deleted
     */
    public void deleteElement(int elementId) {
        GraphElementDeleteCommand command = new GraphElementDeleteCommand(elementId);
        queueCommand(command);
    }

    public void deleteElement(String elementId) {
        try {
            int id = Integer.parseInt(elementId);
            deleteElement(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("TODO");
        }
    }
}
