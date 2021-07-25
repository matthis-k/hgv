package kit.pse.hgv.controller.commandController.commands;

public class StartExtensionCommand extends ExtensionCommand{
    private int id;

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
