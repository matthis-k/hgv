package kit.pse.hgv.controller.commandController.commands;

public class SaveGraphCommand extends FileSystemCommand {
    private int id;
    private String path;

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
