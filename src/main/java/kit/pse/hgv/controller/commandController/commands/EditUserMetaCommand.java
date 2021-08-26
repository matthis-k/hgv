package kit.pse.hgv.controller.commandController.commands;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.PolarCoordinate;

/**
 * This class calls the needed methods to manage the metadata (except color)
 */
public class EditUserMetaCommand extends MetaSystemCommand {
    private final String key;
    private final String value;
    private final int elementId;

    /**
     * The constructor creates an element of this class
     * 
     * @param elementId The elementId from the element wich metadata should be
     *                  changed
     * @param key       Key represents the type of the Metadata (e.g. weight)
     * @param value     Meta represents the value of the Metadata-Key
     */
    public EditUserMetaCommand(int elementId, String key, String value) {
        this.key = key;
        this.value = value;
        this.elementId = elementId;
        extendWorkingArea(elementId);
    }

    @Override
    public void execute() {
        if (GraphSystem.getInstance().getGraphElementByID(elementId) == null) {
            response.put("success", false);
            response.put("reason", "No element with that Id exists.");
            throw new IllegalArgumentException("No Element with that Id exists.");
        }
        if (key.equals("phi")) {
            try {
                double phi = Double.parseDouble(value);
                GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, value);
                Double r = GraphSystem.getInstance().getNodeByID(elementId).getCoord().toPolar().getDistance();
                GraphSystem.getInstance().getNodeByID(elementId).move(new PolarCoordinate(phi, r));
                modifiedIds.add(elementId);
            } catch (NumberFormatException e) {
                // TODO
                response.put("success", false);
                response.put("reason", "can not parse metadata");
            }
        } else if (key.equals("r")) {
            try {
                double r = Double.parseDouble(value);
                GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, value);
                Double phi = GraphSystem.getInstance().getNodeByID(elementId).getCoord().toPolar().getAngle();
                GraphSystem.getInstance().getNodeByID(elementId).move(new PolarCoordinate(phi, r));
                modifiedIds.add(elementId);
            } catch (NumberFormatException e) {
                // TODO
                response.put("success", false);
                response.put("reason", "can not parse metadata");
                return;
            }
        } else if (key.equals("color")) {
            try {
                Color.web(value);
                GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, value);
                modifiedIds.add(elementId);
            } catch (IllegalArgumentException e) {
                response.put("success", false);
                response.put("reason", "can not parse metadata");
            }
        } else {
            GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, value);
            modifiedIds.add(elementId);
        }
        response.put("success", true);
    }

    @Override
    public void undo() {

    }

}
