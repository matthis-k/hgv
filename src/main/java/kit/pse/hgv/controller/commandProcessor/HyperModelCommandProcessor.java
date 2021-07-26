package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

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
     * @param x x-coordinate where to move the center
     * @param y y-coordinate where to move the center
     */
    public void moveCenter(double x, double y){
        MoveCenterCommand command = new MoveCenterCommand(new CartesianCoordinate(x, y));
        queueCommand(command);
    }
}
