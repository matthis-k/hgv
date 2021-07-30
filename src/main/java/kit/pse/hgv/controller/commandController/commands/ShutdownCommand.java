package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.extensionServer.ExtensionServer;

/**
 * This class handles the shutdown of the system
 */
public class ShutdownCommand extends Command {

    @Override
    public void execute() {
        ExtensionServer.getInstance().stopServer();
        CommandController.getInstance().stopController();
        System.exit(0);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}