package kit.pse.hgv.controller.commandProcessor;

import javafx.scene.paint.Color;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

/**
 * This class processes input from the ui that affect the metadata
 */
public class MetaDataProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        CommandController.getInstance().queueCommand(command);
        CommandController.getInstance().queueCommand(new RenderCommand());
    }
    
    public void changeColor(int elementId, Color color){
        EditColorCommand command = new EditColorCommand(elementId, color);
        queueCommand(command);
    }

    public void editMetaData(int elementId, String meta, String key){
        EditUserMetaCommand command = new EditUserMetaCommand(elementId, key, meta);
    }
}
