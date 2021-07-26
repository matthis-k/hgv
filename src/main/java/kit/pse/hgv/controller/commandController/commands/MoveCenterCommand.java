package kit.pse.hgv.controller.commandController.commands;


import kit.pse.hgv.representation.Coordinate;

public class MoveCenterCommand extends HyperModelCommand {
    // TODO Add Coordinate if its available
    private Coordinate transform;

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }

    public Coordinate getTransform() {
        return this.transform;
    }
}
