package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.controller.commandProcessor.FileSystemCommandProcessor;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the file section of the menu bar.
 */
public class FileMenuHandler implements UIHandler {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method allows the user to choose and open a file.
     */
    @FXML
    public void openFile() {
        FileChooser chooser = new FileChooser();
        new FileSystemCommandProcessor().loadGraph(chooser.showOpenDialog(new Stage()));
    }

    /**
     * This method allows a user to save the currently viewed graph.
     */
    @FXML
    public void saveFile() {
        FileChooser chooser = new FileChooser();
        String path = chooser.showOpenDialog(new Stage()).getAbsolutePath();
        new FileSystemCommandProcessor().saveGraph(path, 1);
        //TODO: ID bekommen processor.saveGraph(path, 42);
    }

    /**
     * This method creates a new, empty graph.
     */
    @FXML
    public void createNewGraph() {
        new FileSystemCommandProcessor().createNewGraph();
    }

}
