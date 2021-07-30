package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Thie class manages the edit section of the program.
 */
public class EditHandler implements UIHandler {

    @FXML
    private Button addNodeButton;
    @FXML
    private Button deleteNodeButton;
    @FXML
    private Button addEdgeButton;
    @FXML
    private Button deleteEdgeButton;

    private static final boolean TOGGLE = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNodeButton.setDisable(TOGGLE);
        deleteNodeButton.setDisable(TOGGLE);
        addEdgeButton.setDisable(TOGGLE);
        deleteEdgeButton.setDisable(TOGGLE);
    }

    public void addNode(String radius, String angle) {
    }

    public void deleteNode(int nodeID) {
    }

    public void addEdge(int startID, int endID) {
    }

    public void deleteEdge(int edgeID) {
    }
}
