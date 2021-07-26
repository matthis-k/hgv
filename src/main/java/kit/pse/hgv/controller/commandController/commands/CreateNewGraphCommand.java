package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;

public class CreateNewGraphCommand extends FileSystemCommand{

    @Override
    public void execute() {
        GraphSystem.getInstance().newGraph();
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
    
}
