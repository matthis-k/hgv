package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.IllegalGraphOperation;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.view.uiHandler.EditHandler;

import java.io.FileNotFoundException;
import java.util.IllegalFormatException;

/**
 * This class manages all commands that handle the first visualization of a
 * graph
 */
public class LoadGraphCommand extends FileSystemCommand {
    private static final String FILE_NOT_FOUND = "file not found";
    private static final String GRAPH_TOO_BIG = "graph is too big";
    private final String path;

    /**
     * The constructor creates an element of this class
     *
     * @param path The path of the graph
     */
    public LoadGraphCommand(String path) {
        this.path = path;
    }

    @Override
    public void execute() {
        try {
            int graphId = GraphSystem.getInstance().loadGraph(path);
            EditHandler.getInstance().addGraph(graphId);
            DataGateway.addlastOpened(path);
            modifiedIds.addAll(GraphSystem.getInstance().getIDs(graphId));
        } catch (IllegalFormatException e) {
            fail(e.getMessage());
        } catch (FileNotFoundException e) {
            fail(FILE_NOT_FOUND);
        } catch (OverflowException e) {
            fail(GRAPH_TOO_BIG);
        } catch (IllegalGraphOperation illegalGraphOperation) {
            illegalGraphOperation.printStackTrace();
        }
    }

}
