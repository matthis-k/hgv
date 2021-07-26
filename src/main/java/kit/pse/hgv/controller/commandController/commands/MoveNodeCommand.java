package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.representation.Coordinate;

public class MoveNodeCommand extends GraphSystemCommand {
    private int elementId;
    private Coordinate coordinate;

    public MoveNodeCommand(int elementId, Coordinate coordinate){
        this.elementId = elementId;
        this.coordinate = coordinate;
    }

    @Override
    public void execute(){
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}
