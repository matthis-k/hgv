package kit.pse.hgv.controller.commandController.commands;

/**
 * This class manages the commands that register an extension
 */
public class RegisterExtensionCommand extends ExtensionCommand {
    private final String path;

    /**
     * The constructor creates an element of this class
     *
     * @param path Path of the extension that should be added in the system
     */
    public RegisterExtensionCommand(String path) {
        this.path = path;
    }

    @Override
    public void execute() {

    }
}