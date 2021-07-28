package kit.pse.hgv.controller.commandProcessor;
import kit.pse.hgv.controller.commandController.commands.Command;

/**
 * This class processes all commands coming from the extension and the user
 */
public interface CommandProcessor {
   /**
    * queues the Command in the CommandController
    * @param command the command to be executed
    */
   public void queueCommand(Command command);
}
