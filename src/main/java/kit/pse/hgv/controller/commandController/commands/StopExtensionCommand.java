package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.Extension;

/**
 * This class handles the stop of an extension
 */
public class StopExtensionCommand extends ExtensionCommand {
    private int id;

    /**
     * The constructor creates an element of this class
     * 
     * @param id The id of the extension that should stop
     */
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
