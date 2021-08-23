package kit.pse.hgv.controller.commands;

import kit.pse.hgv.controller.commandController.commands.Command;

public class DummyCommand extends Command {

    @Override
    public void execute() {
        response.put("success", true);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
    
}
