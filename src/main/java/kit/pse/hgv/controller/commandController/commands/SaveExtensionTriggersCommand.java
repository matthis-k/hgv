package kit.pse.hgv.controller.commandController.commands;

import java.util.HashMap;
import java.util.List;

/**
 * TODO 
 */
public class SaveExtensionTriggersCommand extends FileSystemCommand{
    private HashMap<ICommand, List> triggers;
    // TODO List<Extension>

    public SaveExtensionTriggersCommand(HashMap<ICommand, List> triggers){
        this.triggers = triggers;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
    
}
