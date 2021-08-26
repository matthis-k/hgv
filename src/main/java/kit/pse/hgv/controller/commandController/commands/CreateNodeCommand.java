package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.representation.Coordinate;

public class CreateNodeCommand extends GraphSystemCommand {
    private final Coordinate coord;
    private final int graphId;
    
    /**
     * The constructor creates an element of this class
     * 
     * @param graphId the graphId from the graph where the node should be created
     * @param coord   the coordinate of the new node
     */
    public CreateNodeCommand(int graphId, Coordinate coord) {
        this.graphId = graphId;
        this.coord = coord;
    }

    @Override
    public void execute() {
        try {
            int addedId = GraphSystem.getInstance().addElement(graphId, coord);
            modifiedIds.add(addedId);
            response.put(ID, addedId);
        } catch (OverflowException e) {
            fail(NO_GRAPH_WITH_ID);
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {

    }
}
