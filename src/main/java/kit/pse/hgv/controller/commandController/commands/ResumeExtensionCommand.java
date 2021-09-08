package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.ExtensionServer;

/**
 * This class manages the commands that register an extension
 */
public class ResumeExtensionCommand extends ExtensionCommand {
    private final int id;

    /**
     * The constructor creates an element of this class.
     *
     * @param id is the Id if the client that will be paused.
     */
    public ResumeExtensionCommand(int id) {
        this.id = id;
    }

    @Override
    public void execute() {
        ExtensionServer.getInstance().resume(id);
    }

}