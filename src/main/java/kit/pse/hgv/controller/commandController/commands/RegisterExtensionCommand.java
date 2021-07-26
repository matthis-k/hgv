package kit.pse.hgv.controller.commandController.commands;

public class RegisterExtensionCommand extends ExtensionCommand {
    private String path;

    public RegisterExtensionCommand(String path){
        this.path = path;
    }

    @Override
    public void execute(){

    }

    @Override
    public void undo() {

    }
}