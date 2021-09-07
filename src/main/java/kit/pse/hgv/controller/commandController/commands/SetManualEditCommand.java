package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.commandController.CommandController;

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
        CommandController.getInstance().setManualEdit(manualEditEnabled);
    }

    @Override
    public void undo() {

    }
}
