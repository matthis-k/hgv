package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.RenderModel.TabManager;

public class LoadGraphCommand extends FileSystemCommand{
    private String path;

    public LoadGraphCommand(String path){
        this.path = path;
    }

    @Override
    public void execute() {
        GraphSystem.getInstance().loadGraph(path);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
    
}
