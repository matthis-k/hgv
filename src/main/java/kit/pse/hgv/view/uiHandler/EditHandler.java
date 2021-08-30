package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandProcessor.GraphCommandProcessor;
import kit.pse.hgv.controller.commandProcessor.HyperModelCommandProcessor;

import java.net.URL;
import java.util.Locale;
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
    @FXML
    private ChoiceBox<String> currentGraph;
    @FXML
    private Text angleText;
    @FXML
    private Text radiusText;
    @FXML
    private TextField radiusField;
    @FXML
    private TextField angleField;
    @FXML
    private Button submitCreateNode;
    @FXML
    private Text firstNode;
    @FXML
    private Text secondNode;
    @FXML
    private TextField idFirst;
    @FXML
    private TextField idSecond;
    @FXML
    private Button submitCreateEdge;
    @FXML
    private Text deleteID;
    @FXML
    private Button deleteElement;
    @FXML
    private TextField toBeDeleted;

    private static EditHandler instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        setAction();
        hideCreateNode();
        hideCreateEdge();
        hideDelete();
    }

    public static EditHandler getInstance() {
        return instance;
    }

    public void addGraph(int id) {
        RenderHandler.getInstance().switchGraph(id);
        currentGraph.getItems().add(String.valueOf(id));
        currentGraph.setValue(String.valueOf(id));
    }

    private void setAction() {
        currentGraph.setOnAction(actionEvent -> {
            RenderHandler.getInstance().switchGraph(Integer.valueOf(currentGraph.getValue().toString()));
        });
    }
    public void activateAddNode() {
        showCreateNode();
    }
    public void activateAddEdge() {
        showCreateEdge();
    }

    public void activateDelete() {
        showDelete();
    }

    public void delete() {
        hideDelete(); //TODO
        //new GraphCommandProcessor().deleteElement(Integer.valueOf(toBeDeleted.getText()));
    }

    public void addNode() { //ACHTUNG BUG NOCH KEIN GRAPH
        hideCreateNode();
        new GraphCommandProcessor().addNode(Integer.valueOf(currentGraph.getValue().toString()), angleField.getText(), radiusField.getText());
    }

    public void addEdge() { //TODO
        hideCreateEdge();
        //new GraphCommandProcessor().addEdge(Integer.valueOf(currentGraph.getValue().toString());
    }

    private void showCreateNode() {
        angleText.setVisible(true);
        radiusText.setVisible(true);
        radiusField.setVisible(true);
        angleField.setVisible(true);
        submitCreateNode.setVisible(true);
    }

    private void hideCreateNode() {
        angleText.setVisible(false);
        radiusText.setVisible(false);
        radiusField.setVisible(false);
        angleField.setVisible(false);
        submitCreateNode.setVisible(false);
    }

    private void hideCreateEdge() {
        firstNode.setVisible(false);
        secondNode.setVisible(false);
        idFirst.setVisible(false);
        idSecond.setVisible(false);
        submitCreateEdge.setVisible(false);
    }
    private void showCreateEdge() {
        firstNode.setVisible(true);
        secondNode.setVisible(true);
        idFirst.setVisible(true);
        idSecond.setVisible(true);
        submitCreateEdge.setVisible(true);
    }

    private void hideDelete() {
        toBeDeleted.setVisible(false);
        deleteID.setVisible(false);
        deleteElement.setVisible(false);
    }
    private void showDelete() {
        toBeDeleted.setVisible(true);
        deleteID.setVisible(true);
        deleteElement.setVisible(true);
    }

    public void deleteNode(int nodeID) {
    }

    public void addEdge(int startID, int endID) {
    }

    public void deleteEdge(int edgeID) {
    }

    public static EditHandler getEditHandler() {
        return instance;
    }
}
