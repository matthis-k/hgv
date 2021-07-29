package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;

/**
 * This class handles the creation and undo of a creation of a new empty graph
 */
public class CreateNewGraphCommand extends FileSystemCommand{

    @Override
    public void execute() {
        int id = GraphSystem.getInstance().newGraph();
        response.put("success", true);
        response.put("id", id);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
    
}
