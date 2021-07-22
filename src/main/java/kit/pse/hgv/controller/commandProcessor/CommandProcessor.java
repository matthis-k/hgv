package kit.pse.hgv.controller.commandProcessor;
import kit.pse.hgv.controller.commandController.commands.Command;

public interface CommandProcessor {
   public void queueCommand(Command command);
}
