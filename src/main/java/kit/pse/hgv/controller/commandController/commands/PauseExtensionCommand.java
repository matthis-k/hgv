package kit.pse.hgv.controller.commandController.commands;

public class PauseExtensionCommand extends ExtensionCommand{
    private int id;

    public PauseExtensionCommand(int id){
        this.id = id;
    }

    @Override
    public void execute(){
        
    }

    @Override
    public void undo() {

    }
}
