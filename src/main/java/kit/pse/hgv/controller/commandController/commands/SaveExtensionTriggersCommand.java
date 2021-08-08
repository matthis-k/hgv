package kit.pse.hgv.controller.commandController.commands;

import java.util.HashMap;
import java.util.List;

import kit.pse.hgv.extensionServer.Extension;

/**
 * TODO
 */
public class SaveExtensionTriggersCommand extends FileSystemCommand {
    private HashMap<ICommand, List<Extension>> triggers;
    // TODO List<Extension>

    public SaveExtensionTriggersCommand(HashMap<ICommand, List<Extension>> triggers) {
        this.triggers = triggers;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        /*  */
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }

}
