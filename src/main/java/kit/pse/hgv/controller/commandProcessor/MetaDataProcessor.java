package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CommandComposite;
import kit.pse.hgv.controller.commandController.commands.EditUserMetaCommand;
import kit.pse.hgv.controller.commandController.commands.ICommand;

import java.util.HashMap;

/**
 * This class processes input from the ui that affect the metadata
 */
public class MetaDataProcessor implements CommandProcessor {

    @Override
    public void queueCommand(ICommand command) {
        CommandController.getInstance().queueCommand(command);
    }

    /**
     * This Method creates an EditUserMetaCommand
     *
     * @param elementId which Element should be edited
     * @param meta      concrete meta information
     * @param key       which MetaData-Type should be edited
     */
    public void editMetaData(int elementId, String key, String meta) {
        EditUserMetaCommand command = new EditUserMetaCommand(elementId, key, meta);
        queueCommand(command);
    }

    public void editMetaData(int currentID, HashMap<String, String> map) {
        CommandComposite c = new CommandComposite();
        for (String key : map.keySet()) {
            c.addCommand(new EditUserMetaCommand(currentID, key, map.get(key)));
        }
        queueCommand(c);
    }
}
