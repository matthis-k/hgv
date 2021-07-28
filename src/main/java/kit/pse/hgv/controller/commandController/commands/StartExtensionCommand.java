package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles the command for the start of an extension
 */
public class StartExtensionCommand extends ExtensionCommand{
    private int id;

    /**
     * The constructor creates an element of this class
     * 
     * @param id The id of the extension that should start
     */
    public StartExtensionCommand(int id) {
        this.id = id;
    }

    @Override
    public void execute(){
        
    }

    @Override
    public void undo() {

    }
}
