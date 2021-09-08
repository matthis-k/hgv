package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.ExtensionServer;

/**
 * This class handles the stop of an extension
 */
public class StopExtensionCommand extends ExtensionCommand {
    private int id = -1;

    /**
     * The constructor creates an element of this class
     *
     * @param id The id of the extension that should stop
     */
    public StopExtensionCommand(int id) {
        this.id = id;
    }

    /**
     * The constructor creates an element of this class without an id
     */
    public StopExtensionCommand() {
    }

    @Override
    public void execute() {
        ExtensionServer.getInstance().stop(id == -1 ? getClientId() : id);
    }

}
