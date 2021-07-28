package kit.pse.hgv.controller.commandController.commands;


import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.view.RenderModel.RenderEngine;

/**
 * This class manages the commands that update the center of the hyperbolic model
 */
public class MoveCenterCommand extends HyperModelCommand {
    private DrawManager manager;
    private Coordinate transform;

    public MoveCenterCommand(DrawManager manager, Coordinate transform) {
        this.transform = transform;
        this.manager = manager;
    }

    @Override
    public void execute() {
        RenderEngine.getInstance().getDrawManager().moveCenter(transform);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }

    public Coordinate getTransform() {
        return this.transform;
    }
}
