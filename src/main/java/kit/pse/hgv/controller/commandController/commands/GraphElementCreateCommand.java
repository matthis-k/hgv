package kit.pse.hgv.controller.commandController.commands;

public class GraphElementCreateCommand extends GraphSystemCommand {
    private int graphId;

    public GraphElementCreateCommand(int graphId) {
        this.graphId = graphId;
    }

    @Override
    public void execute(){

    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}
