package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;

import java.io.File;

/**
 * This class processes the input from the ui managing files
 */
public class FileSystemCommandProcessor implements CommandProcessor {

    @Override
    public void queueCommand(ICommand command) {
        CommandController.getInstance().queueCommand(command);
    }

    /**
     * This class checks if the given file is a graphml file and creates a
     * loadGraphCommand
     * 
     * @param file The file that defines the graph
     */
    public void loadGraph(File file) {
        String path = file.getAbsolutePath();
        if (path.endsWith(".graphml")) {
            LoadGraphCommand command = new LoadGraphCommand(path);
            queueCommand(command);
        }
    }

    /**
     * This class creates a saveGraphCommand
     * 
     * @param path where to save
     * @param id   which graph to save
     */
    public void saveGraph(String path, int id) {
        SaveGraphCommand command = new SaveGraphCommand(id, path);
        queueCommand(command);
    }

    /**
     * This class creates a createNewGraphCommand
     */
    public void createNewGraph() {
        CreateNewGraphCommand command = new CreateNewGraphCommand();
        queueCommand(command);
    }

    /**
     * This class creates a shutdown command
     */
    public void shutdown() {
        ShutdownCommand command = new ShutdownCommand();
        queueCommand(command);
    }
}
