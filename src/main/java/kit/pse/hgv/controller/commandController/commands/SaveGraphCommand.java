package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles the commands that save the graph
 */
public class SaveGraphCommand extends FileSystemCommand {
    private int id;
    private String path;

    /**
     * The constructor creates an element of this class
     * 
     * @param id the id of the graph that should be saved
     * @param path the path that defines where the graph should be saved
     */
    public SaveGraphCommand(int id, String path){
        this.id = id;
        this.path = path;
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
