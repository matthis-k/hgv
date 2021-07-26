package kit.pse.hgv.controller.commandController.commands;


import kit.pse.hgv.representation.Coordinate;

/**
 * This class manages the commands that update the center of the hyperbolic model
 */
public class MoveCenterCommand extends HyperModelCommand {
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
