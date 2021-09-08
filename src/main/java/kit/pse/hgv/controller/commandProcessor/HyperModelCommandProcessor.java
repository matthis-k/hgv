package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.controller.commandController.commands.MoveCenterCommand;
import kit.pse.hgv.controller.commandController.commands.SetAccuracyCommand;
import kit.pse.hgv.representation.Coordinate;

/**
 * This class handles the input from the ui affecting the hyperbolic model
 * visualization
 */
public class HyperModelCommandProcessor implements CommandProcessor {

    @Override
    public void queueCommand(ICommand command) {
        CommandController.getInstance().queueCommand(command);
    }

    /**
     * This method creates a moveCenterCommand
     *
     * @param coordinate where to move the center
     */
    public void moveCenter(Coordinate coordinate) {
        MoveCenterCommand command = new MoveCenterCommand(coordinate);
        queueCommand(command);
    }

    /**
     * This method creates a setAccuracyCommand
     * @param accuracy which accuracy should be used
     */
    public void setAccuracy(String accuracy) {
        SetAccuracyCommand command = new SetAccuracyCommand(accuracy);
        queueCommand(command);
    }

}
