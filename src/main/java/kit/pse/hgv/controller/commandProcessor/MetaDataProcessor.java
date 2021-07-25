package kit.pse.hgv.controller.commandProcessor;

import javafx.scene.paint.Color;
import kit.pse.hgv.controller.commandController.commands.*;

public class MetaDataProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        // TODO Auto-generated method stub
        
    }
    
    public void changeColor(int elementId, Color color){
        EditColorCommand command = new EditColorCommand(elementId, color);
    }

    public void editMetaData(int elementId, String radius, String angle, String weight){
        //TODO anpassen
        //EditUserMetaCommand command = new EditUserMetaCommand(metaKey, metaVal, elementId);
    }
}
