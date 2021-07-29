package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.controller.commandProcessor.FileSystemCommandProcessor;
import kit.pse.hgv.controller.dataGateway.DataGateway;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class manages the file section of the menu bar.
 */
public class FileMenuHandler implements UIHandler {

    private static final int ONLY_GRAPH = 1;
    private static final String NOTHING_OPENED_YET = "Noch keine Datei geöffnet.";
    @FXML
    private Menu lastOpened;

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
     * This method updates the menu containing the five last opened graphs.
     */
    @FXML
    public void updateLastOpened() {
        lastOpened.getItems().clear();
        List<String> paths = DataGateway.getlastOpenedGraphs();
        if(!paths.isEmpty()) {
            for (String path : paths) {
                MenuItem newPath = new MenuItem(path);
                newPath.setOnAction(action ->
                        new FileSystemCommandProcessor().loadGraph(new File(path)));
                lastOpened.getItems().add(newPath);
            }
        } else {
            MenuItem empty = new MenuItem(NOTHING_OPENED_YET);
            lastOpened.getItems().add(empty);
        }
    }

    /**
     * This method allows a user to save the currently viewed graph.
     */
    @FXML
    public void saveFile() {
        FileChooser chooser = new FileChooser();
        String path = chooser.showSaveDialog(new Stage()).getAbsolutePath();
        new FileSystemCommandProcessor().saveGraph(path, ONLY_GRAPH);
    }

    /**
     * This method creates a new, empty graph.
     */
    @FXML
    public void createNewGraph() {
        new FileSystemCommandProcessor().createNewGraph();
    }

}
