package kit.pse.hgv.commandProcessor;
import kit.pse.hgv.commandController.commands.*;

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
    public ExtensionCommandInput processCommand(String extensionInput) {
        try {
            return ExtensionCommandInput.processCommandString(extensionInput);
        } catch (IllegalArgumentException e) {
            //TODO
            return null;
        }
    }
}
