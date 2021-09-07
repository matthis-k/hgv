package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.ExtensionServer;

/**
 * This class handles the commands that pauses an extension
 */
public class PauseExtensionCommand extends ExtensionCommand {
    private int id = -1;

    /**
     * The constructor creates an element of this class
     * 
     * @param id The id of the extension that should be paused
     */
    public PauseExtensionCommand(int id) {
        this.id = id;
    }

    public PauseExtensionCommand(){}

    @Override
    public void execute() {
        ExtensionServer.getInstance().pause(id == -1 ? getClientId() : id);
    }

    @Override
    public void undo() {

    }
}
