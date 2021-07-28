package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.PolarCoordinate;

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
        if(key.equals("phi")) {
            try {
                double phi = Double.parseDouble(meta);
                GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, meta);
                Double r = GraphSystem.getInstance().getNodeByID(elementId).getCoord().toPolar().getDistance();
                GraphSystem.getInstance().getNodeByID(elementId).move(new PolarCoordinate(phi, r));
            } catch (NumberFormatException e) {
                //TODO
            }
        } else if (key.equals("r")) {
            try {
                double r = Double.parseDouble(meta);
                GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, meta);
                Double phi = GraphSystem.getInstance().getNodeByID(elementId).getCoord().toPolar().getAngle();
                GraphSystem.getInstance().getNodeByID(elementId).move(new PolarCoordinate(phi, r));
            } catch (NumberFormatException e) {
                //TODO
            }
        } else if (key.equals("weight")){
            GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, meta);
        } else {
            throw new IllegalArgumentException("This metadata type is non-existent");
        }
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
    }

}
