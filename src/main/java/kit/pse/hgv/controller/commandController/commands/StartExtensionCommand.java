package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.Extension;
import kit.pse.hgv.extensionServer.PyScript;

/**
 * This class handles the command for the start of an extension
 */
public class StartExtensionCommand extends ExtensionCommand {
    private String path;

    /**
     * The constructor creates an element of this class
     * 
     * @param path The id of the extension that should start
     */
    public StartExtensionCommand(String path) {
        this.path = path;
    }

    @Override
    public void execute() {
        Extension extension = null;
        if (path.endsWith(".py")) {
            extension = new PyScript(path);
            response.put("success", true);
        } else {
            response.put("success", true);
        }
        if (extension != null) {
            extension.startExtension();
        }
    }

    @Override
    public void undo() {

    }
}
