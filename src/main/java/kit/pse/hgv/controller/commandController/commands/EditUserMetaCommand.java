package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;

public class EditUserMetaCommand extends MetaSystemCommand{
    private String metaKey;
    private String metaVal;

    public EditUserMetaCommand(String metaKey, String metaVal, int elementId){
        this.metaKey = metaKey;
        this.metaVal = metaVal;
        this.elementId = elementId;
    }

    @Override
    public void execute() {
        GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(metaKey, metaVal);
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }

}
