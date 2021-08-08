package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;

/**
 * This class handles the commands that delete elements or undo the delete
 * command
 */
public class GraphElementDeleteCommand extends GraphSystemCommand {
    private int elementId;

    /**
     * The constructor creates an element of this class
     * 
     * @param elementId ElementId from the Element that should be deleted
     */
    public GraphElementDeleteCommand(int elementId) {
        this.elementId = elementId;
    }

    @Override
    public void execute() {
        if (GraphSystem.getInstance().getGraphElementByID(elementId) == null) {
            response.put("success", false);
            response.put("reason", "No element with that Id exists.");
            throw new IllegalArgumentException("No Element with that Id exists.");
        }
        modifiedIds.addAll(GraphSystem.getInstance().removeElement(elementId));
        modifiedIds.add(elementId);
        response.put("success", true);
    }

    @Override
    public void undo() {
    }
}
