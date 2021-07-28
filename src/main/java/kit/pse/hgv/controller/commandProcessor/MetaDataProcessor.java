package kit.pse.hgv.controller.commandProcessor;

import javafx.scene.paint.Color;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

/**
 * This class processes input from the ui that affect the metadata
 */
public class MetaDataProcessor implements CommandProcessor{

    @Override
    public void queueCommand(ICommand command) {
        CommandController.getInstance().queueCommand(command);
        CommandController.getInstance().queueCommand(new RenderCommand());
    }
    
    /**
     * This Method creates a new EditColorCommand
     * 
     * @param elementId
     * @param color
     */
    public void changeColor(int elementId, Color color){
        EditColorCommand command = new EditColorCommand(elementId, color);
        queueCommand(command);
    }

    /**
     * This Method creates an EditUserMetaCommand
     * 
     * @param elementId which Element should be edited
     * @param meta concrete meta information
     * @param key which MetaData-Type should be edited
     */
    public void editMetaData(int elementId, String meta, String key){
        EditUserMetaCommand command = new EditUserMetaCommand(elementId, key, meta);
        queueCommand(command);
    }
}
