package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles all Commands managing the graphSystem
 */
public abstract class GraphSystemCommand extends WorkingAreaCommand {
    protected int graphId;


    /**
     * The constructor creates an element of this class without a specific graph
     */
    public GraphSystemCommand() {
    }
}
