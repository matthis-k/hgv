package kit.pse.hgv.view.uiHandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.controller.commandProcessor.FileSystemCommandProcessor;
import kit.pse.hgv.controller.dataGateway.DataGateway;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class manages the file section of the menu bar.
 */
public class FileMenuHandler implements UIHandler {

    @FXML
    private Menu lastOpened;
    private boolean cleared;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cleared = false;
    }

    /**
     * This method allows the user to choose and open a file.
     */
    @FXML
    public void openFile() {
        FileChooser chooser = new FileChooser();
        new FileSystemCommandProcessor().loadGraph(chooser.showOpenDialog(new Stage()));
    }

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
            MenuItem empty = new MenuItem("Noch keine Datei geöffnet.");
            lastOpened.getItems().add(empty);
        }
    }

    /**
     * This method allows a user to save the currently viewed graph.
     */
    @FXML
    public void saveFile() {
        FileChooser chooser = new FileChooser();
        String path = chooser.showOpenDialog(new Stage()).getAbsolutePath();
        new FileSystemCommandProcessor().saveGraph(path, 42);
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
