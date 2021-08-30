package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.uiHandler.RenderHandler;

/**
 * This class manages the commands that update the center of the hyperbolic
 * model
 */
public class MoveCenterCommand extends HyperModelCommand {

    private Coordinate transform;

    /**
     * The constructor creates an element of this class
     *
     * @param transform where the new center should be
     */
    public MoveCenterCommand(Coordinate transform) {
        this.transform = transform;
    }

    @Override
    public void execute() {
        modifiedIds.addAll(GraphSystem.getInstance().getAllIds());
        RenderHandler.getInstance().moveDrawManagerCenter(transform);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }

    public Coordinate getTransform() {
        return this.transform;
    }
}
