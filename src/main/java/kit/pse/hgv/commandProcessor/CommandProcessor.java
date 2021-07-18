package kit.pse.hgv.commandProcessor;
import kit.pse.hgv.commandController.commands.*;

public interface CommandProcessor {
   public void queueCommand(Command command);
}
