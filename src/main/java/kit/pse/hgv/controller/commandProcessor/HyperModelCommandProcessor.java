package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

/**
 * This class handles the input from the ui affecting the hyperbolic model visualization
 */
public class HyperModelCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(ICommand command) {
        CommandController.getInstance().queueCommand(command);
        CommandController.getInstance().queueCommand(new RenderCommand());
    }

    /**
     * This method creates a moveCenterCommand
     * 
     * @param phi x-coordinate where to move the center
     * @param r y-coordinate where to move the center
     */
    public void moveCenter(Coordinate coordinate){
        MoveCenterCommand command = new MoveCenterCommand(coordinate);
        queueCommand(command);
    }
}
