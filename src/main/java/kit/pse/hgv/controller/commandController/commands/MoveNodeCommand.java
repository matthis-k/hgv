package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.Coordinate;

/**
 * This class handles the commands that let the user move nodes
 */
public class MoveNodeCommand extends GraphSystemCommand {
    private int elementId;
    private Coordinate coordinate;

    /**
     * The constructor creates an element of this class
     * 
     * @param elementId ElementId of the Element that should be moved
     * @param coordinate Coordinate of the new position of the element
     */
    public MoveNodeCommand(int elementId, Coordinate coordinate){
        this.elementId = elementId;
        this.coordinate = coordinate;
    }

    @Override
    public void execute(){
        GraphSystem.getInstance().getNodeByID(elementId).move(coordinate);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}
