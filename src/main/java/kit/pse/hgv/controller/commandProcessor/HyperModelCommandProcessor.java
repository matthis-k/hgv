package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;

/**
 * This class handles the input from the ui affecting the hyperbolic model visualization
 */
public class HyperModelCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        CommandController.getInstance().queueCommand(command);
        CommandController.getInstance().queueCommand(new RenderCommand());
    }

    /**
     * This method creates a moveCenterCommand
     * TODO: double or string?
     * 
     * @param phi x-coordinate where to move the center
     * @param r y-coordinate where to move the center
     */
    public void moveCenter(Coordinate coordinate){
        MoveCenterCommand command = new MoveCenterCommand(coordinate);
        queueCommand(command);
    }
}
