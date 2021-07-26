package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;

/**
 * This class calls the needed methods to manage the metadata (except color)
 */
public class EditUserMetaCommand extends MetaSystemCommand{
    private String key;
    private String meta;
    private int elementId;
    
    /**
     * The constructor creates an element of this class
     * 
     * @param elementId The elementId from the element wich metadata should be changed
     * @param key Key represents the type of the Metadata (e.g. weight)
     * @param meta Meta represents the value of the Metadata-Key
     */
    public EditUserMetaCommand(int elementId, String key, String meta){
        this.key = key;
        this.meta = meta;
        this.elementId = elementId;
    }

    @Override
    public void execute() {
        GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, meta);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
    }

}
