package kit.pse.hgv.commandProcessor;
import kit.pse.hgv.commandController.commands.*;

public class ExtensionCommandProcessor implements CommandProcessor{
    @Override
    public void queueCommand(Command command) {

    }

    public void getCommand(String extensionInput) {
        Command command;
        try {
            ExtensionCommandInput.processCommandString(extensionInput);
        } catch (IllegalArgumentException e) {
            //ERROR MESSAGE
        }
    }
}
