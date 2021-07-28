package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles all Commands managing the graphSystem
 */
public abstract class GraphSystemCommand extends Command {
    protected int graphId;
    /**
     * The constructor creates an element of this class with a specific graphId
     * 
     * @param graphId GraphId from the Graph that should be updated
     */
    public GraphSystemCommand(int graphId) {
        this.graphId = graphId;
    }
    
    /**
     * The constructor creates an element of this class without a specific graph
     */
    public GraphSystemCommand() {
    }
}
