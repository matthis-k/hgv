package kit.pse.hgv.controller.commandController.commands;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.PolarCoordinate;

/**
 * This class calls the needed methods to manage the metadata (except color)
 */
public class EditUserMetaCommand extends MetaSystemCommand {
    private static final String PHI = "phi";
    private static final String R = "r";
    private static final String COLOR = "color";
    private static final String CANNOT_PARSE = "can not parse metadata";
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
        GraphElement e = GraphSystem.getInstance().getGraphElementByID(elementId);
        Node n = GraphSystem.getInstance().getNodeByID(elementId);
        if (e == null) {
            fail(NO_ELEMENT_WITH_ID);
            return;
        }
        if (key.equals(PHI) && n != null) {
            try {
                double phi = Double.parseDouble(value);
                Double r = n.getCoord().toPolar().getDistance();
                n.move(new PolarCoordinate(phi, r));
                modifiedIds.add(elementId);
                modifiedIds.addAll(GraphSystem.getInstance().getEdgeIdsOfNode(elementId));
            } catch (NumberFormatException ex) {
                fail(CANNOT_PARSE);
            }
        } else if (key.equals(R) && n != null) {
            try {
                double r = Double.parseDouble(value);
                Double phi = n.getCoord().toPolar().getAngle();
                n.move(new PolarCoordinate(phi, r));
                modifiedIds.add(elementId);
                modifiedIds.addAll(GraphSystem.getInstance().getEdgeIdsOfNode(elementId));
            } catch (NumberFormatException ex) {
                fail(CANNOT_PARSE);
            }
        } else if (key.equals(COLOR)) {
            try {
                Color.web(value);
                e.setMetadata(key, value);
                modifiedIds.add(elementId);
            } catch (IllegalArgumentException ex) {
                fail(CANNOT_PARSE);
            }
        } else {
            GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(key, value);
            modifiedIds.add(elementId);
        }
    }

}
