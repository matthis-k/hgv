package kit.pse.hgv.controller.commandProcessor;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

/**
 * This class processes the commands coming from the extension and the commands handling the extensions. It queues the command in the command controller, if it is in the correct format.
 */

public class ExtensionCommandProcessor implements CommandProcessor{
    @Override
    public void queueCommand(Command command) {
        CommandController.getInstance().queueCommand(command);
    }

    /**
     * This method receives the command from the extension as a string and returns an enum as command
     *
     * @param extensionInput the command as string from the extension
     * @return the command as enum
     */
    public ExtensionCommandType processCommand(String extensionInput) {
        try {
            return ExtensionCommandType.processCommandString(extensionInput);
        } catch (IllegalArgumentException e) {
            //TODO
            return null;
        }
    }

    /**
     * Processes the command to register the extension.
     * @param path Path of the extension
     */
    public void registerExtension(String path){
        RegisterExtensionCommand command = new RegisterExtensionCommand(path);
        queueCommand(command);
    }

    /**
     * Processes the command to start the extension
     * @param id id of the extension to be executed
     */
    public void startExtension(int id){
        StartExtensionCommand command = new StartExtensionCommand(id);
        queueCommand(command);
    }
}
