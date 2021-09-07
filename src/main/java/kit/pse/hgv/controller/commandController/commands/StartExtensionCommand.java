package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.Extension;
import kit.pse.hgv.extensionServer.PyScript;

/**
 * This class handles the command for the start of an extension
 */
public class StartExtensionCommand extends ExtensionCommand {
    private static final String FILE_NOT_FOUND = "file not found";
    private static final String PY_FILE_SUFFIX = ".py";
    private final String path;

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
        if (path.endsWith(PY_FILE_SUFFIX)) {
            extension = new PyScript(path);
        }
        if (extension != null) {
            extension.startExtension();
        } else {
            fail(FILE_NOT_FOUND);
        }
    }

    @Override
    public void undo() {

    }
}
