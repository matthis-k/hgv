package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;

public class GraphElementDeleteCommand extends GraphSystemCommand {
    private int elementId;

    public GraphElementDeleteCommand(int elementId){
        super(0);
        this.elementId = elementId;
    }

    @Override
    public void execute(){
        GraphSystem.getInstance().removeElement(elementId);
    }

    @Override
    public void undo() {
    }
}
