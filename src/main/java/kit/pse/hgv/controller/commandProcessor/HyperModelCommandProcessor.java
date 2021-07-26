package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.*;

public class HyperModelCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        // TODO Auto-generated method stub
        
    }

    public void moveCenter(double x, double y){
        MoveCenterCommand command = new MoveCenterCommand();
    }
}
