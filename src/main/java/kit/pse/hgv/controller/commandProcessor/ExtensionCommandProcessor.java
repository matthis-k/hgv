package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

/**
 * This class processes the commands coming from the extension and the commands
 * handling the extensions. It queues the command in the command controller, if
 * it is in the correct format.
 */

public class ExtensionCommandProcessor implements CommandProcessor {
    @Override
    public void queueCommand(ICommand command) {
        CommandController.getInstance().queueCommand(command);
    }

    /**
     * Processes the command to register the extension.
     * 
     * @param path Path of the extension
     */
    public void registerExtension(String path) {
        RegisterExtensionCommand command = new RegisterExtensionCommand(path);
        queueCommand(command);
    }

    /**
     * Processes the command to start the extension
     * 
     * @param path of the extension to be executed
     */
    public void startExtension(String path) {
        StartExtensionCommand command = new StartExtensionCommand(path);
        queueCommand(command);
    }
}
