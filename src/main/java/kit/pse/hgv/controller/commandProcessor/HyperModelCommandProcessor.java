package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.*;

public class HyperModelCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        //TODO CommandController.getInstance().queueCommand(command);
    }

    public void moveCenter(){
        MoveCenterCommand command = new MoveCenterCommand();
        queueCommand(command);
    }
}
