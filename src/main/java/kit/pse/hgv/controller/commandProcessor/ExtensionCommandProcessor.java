package kit.pse.hgv.controller.commandProcessor;
import kit.pse.hgv.controller.commandController.commands.*;

/**
 * This class processes the commands coming from the extension. It queues the command in the command controller, if it is in the correct format.
 */

public class ExtensionCommandProcessor implements CommandProcessor{
    @Override
    public void queueCommand(Command command) {

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

    public void registerExtension(String path){
        RegisterExtensionCommand command = new RegisterExtensionCommand(path);
    }

    public void startExtension(int id){
        StartExtensionCommand command = new StartExtensionCommand(id);
    }
}
