package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.dataGateway.DataGateway;

import java.io.IOException;

/**
 * This class handles the commands that save the graph
 */
public class SaveGraphCommand extends FileSystemCommand {
    private static final String WRITE_TO_FILE_FAILED = "could not save graph";
    private final int id;
    private final String path;

    /**
     * The constructor creates an element of this class
     *
     * @param id   the id of the graph that should be saved
     * @param path the path that defines where the graph should be saved
     */
    public SaveGraphCommand(int id, String path) {
        this.id = id;
        this.path = path;
    }

    @Override
    public void execute() {
        try {
            DataGateway.saveGraph(id, path);
        } catch (IOException e) {
            fail(WRITE_TO_FILE_FAILED);
        }
    }
}
