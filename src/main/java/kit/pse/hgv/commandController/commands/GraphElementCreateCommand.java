package kit.pse.hgv.commandController.commands;

public class GraphElementCreateCommand extends GraphElementCommand {
    private int graphId;
    
    // TODO add Coordinate when it's available

    public GraphElementCreateCommand(int graphId){
        this.graphId = graphId;
    }

    public void execute(){

    }
}
