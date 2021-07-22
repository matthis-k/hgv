package kit.pse.hgv.commandController.commands;

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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }

}
