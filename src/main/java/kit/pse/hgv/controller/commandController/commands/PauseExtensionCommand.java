package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles the commands that pauses an extension
 */
public class PauseExtensionCommand extends ExtensionCommand {
    private int id;

    /**
     * The constructor creates an element of this class
     * 
     * @param id The id of the extension that should be paused
     */
    public PauseExtensionCommand(int id) {
        this.id = id;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
