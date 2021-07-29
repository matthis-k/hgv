package kit.pse.hgv.controller.commandController.commands;
import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.GraphSystem;

/**
 * This class instructs the Element to change its color
 */
public class EditColorCommand extends MetaSystemCommand{
    private int elementId;
    private Color color;

    /**
     * The constructor creates an element of this class
     * 
     * @param elementId ElementId from the Element which color should be updated
     * @param color New color for the element
     */
    public EditColorCommand(int elementId, Color color){
        this.color = color;
        this.elementId = elementId;
    }

    @Override
    public void execute() {
        if (GraphSystem.getInstance().getGraphElementByID(elementId) == null) {
            throw new IllegalArgumentException("No Element with that Id exists.");
        }
        String colorAsString = String.format("#%02X%02X%02X", (int) Math.round(color.getRed() *255), (int) Math.round(color.getGreen() * 255), (int) Math.round(color.getBlue() * 255));
        GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata("color", colorAsString);
        modifiedIds.add(elementId);
        response.put("success", true);
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
    }
    
}
