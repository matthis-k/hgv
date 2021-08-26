package kit.pse.hgv.controller.commandController.commands;

/**
 * The class handles the commands that set the manual edit options
 */
public class SetManualEditCommand extends ExtensionCommand {
    boolean manualEditEnabled;

    public SetManualEditCommand(boolean manualEditEnabled) {
        this.manualEditEnabled = manualEditEnabled;
    }

    @Override
    public void execute() {
    }

    @Override
    public void undo() {

    }
}
