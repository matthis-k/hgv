package kit.pse.hgv.controller.commandController.commands;

public class GraphElementDeleteCommand extends GraphElementCommand {
    private int elementId;

    public GraphElementDeleteCommand(int elementId){
        this.elementId = elementId;
    }

    @Override
    public void execute(){
    }

    @Override
    public void undo() {
    }
}
