package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;

/**
 * This class manages all commands that handle the first visualization of a graph
 */
public class LoadGraphCommand extends FileSystemCommand{
    private String path;

    /**
     * The constructor creates an element of this class
     * 
     * @param path The path of the graph
     */
    public LoadGraphCommand(String path){
        this.path = path;
    }

    @Override
    public void execute() {
        int graphId = GraphSystem.getInstance().loadGraph(path);
        DataGateway.addlastOpened(path);
        modifiedIds.addAll(GraphSystem.getInstance().getIDs(graphId));
        response.put("success", true);
    }

    @Override
    public void undo() {

    }
    
}
