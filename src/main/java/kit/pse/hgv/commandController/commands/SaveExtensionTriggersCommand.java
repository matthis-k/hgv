package kit.pse.hgv.commandController.commands;

import java.util.HashMap;
import java.util.List;

public class SaveExtensionTriggersCommand extends FileSystemCommand{
    private HashMap<Command, List> triggers;
    // TODO List<Extension>

    public SaveExtensionTriggersCommand(HashMap<Command, List> triggers){
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
