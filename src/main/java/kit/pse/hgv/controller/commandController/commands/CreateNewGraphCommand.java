package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.uiHandler.EditHandler;

/**
 * This class handles the creation and undo of a creation of a new empty graph
 */
public class CreateNewGraphCommand extends FileSystemCommand {

    @Override
    public void execute() {
        int id = GraphSystem.getInstance().newGraph();
        response.put(ID, id);
        EditHandler.getInstance().addGraph(id);
        modifiedIds.addAll(GraphSystem.getInstance().getIDs(id));
    }

    @Override
    public void undo() {

    }

}
