package kit.pse.hgv.controller.commandController.commands;

public class StopExtensionCommand extends ExtensionCommand {
    private int id;

    public StopExtensionCommand(int id) {
        this.id = id;
    }

    @Override
    public void execute() {
    }

    @Override
    public void undo() {

    }
}
