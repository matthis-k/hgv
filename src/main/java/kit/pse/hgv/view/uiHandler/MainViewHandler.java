package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the structure of the UI.
 */
public class MainViewHandler implements UIHandler {

    /**
     * An underlying pane used to create the structure.
     */
    @FXML
    private AnchorPane primaryAnchorPane;
    /**
     * The menu bar of the program.
     */
    @FXML
    private MenuBar viewMenuBar;
    /**
     * The container used to display the representation of a graph.
     */
    @FXML
    private Pane renderPane;
    /**
     * The edit container of the program.
     */
    @FXML
    private Pane editPane;
    /**
     * The detail container of the program. Used to edit meta data.
     */
    @FXML
    private Pane detailPane;

    /**
     * This method binds the containers to their destined location.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewMenuBar.prefWidthProperty().bind(primaryAnchorPane.widthProperty());

        renderPane.prefWidthProperty().bind(primaryAnchorPane.widthProperty());
        renderPane.prefHeightProperty().bind(primaryAnchorPane.heightProperty());

        editPane.prefHeightProperty().bind(primaryAnchorPane.heightProperty());
        detailPane.prefHeightProperty().bind(primaryAnchorPane.heightProperty());
    }
}
