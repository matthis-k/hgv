package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.element.GraphElement;

import java.util.ArrayList;
import java.util.List;

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
