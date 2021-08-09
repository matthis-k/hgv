package kit.pse.hgv.controller.commandController.commands;

import java.io.IOException;

import kit.pse.hgv.controller.dataGateway.DataGateway;

/**
 * This class handles the commands that save the graph
 */
public class SaveGraphCommand extends FileSystemCommand {
    private int id;
    private String path;

    /**
     * The constructor creates an element of this class
     * 
     * @param id   the id of the graph that should be saved
     * @param path the path that defines where the graph should be saved
     */
    public SaveGraphCommand(int id, String path) {
        this.id = id;
        this.path = path;
    }

    @Override
    public void execute() {
        try {
            DataGateway.saveGraph(id, path);
            response.put("success", true);
        } catch (IOException e) {
            response.put("success", false);
        }
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }

}
