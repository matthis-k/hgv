package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.*;

public class MetaDataProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        // TODO Auto-generated method stub
        
    }
    
    public void changeColor(int elementId){
        EditColorCommand command = new EditColorCommand(elementId);
    }

    public void editMetaData(int elementId, String metaKey, String metaVal){
        EditUserMetaCommand command = new EditUserMetaCommand(metaKey, metaVal, elementId);
    }
}
