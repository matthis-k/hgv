package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandProcessor.GraphCommandProcessor;

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
    @FXML
    private ChoiceBox<String> currentGraph;

    private static EditHandler instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        setAction();
    }

    public static EditHandler getInstance() {
        return instance;
    }

    public void addGraph(int id) {
        System.out.println("added checkbox");
        RenderHandler.getInstance().switchGraph(id);
        currentGraph.getItems().add(String.valueOf(id));
        currentGraph.setValue(String.valueOf(id));
    }

    private void setAction() {
        currentGraph.setOnAction(actionEvent -> {
            RenderHandler.getInstance().switchGraph(Integer.valueOf(currentGraph.getValue().toString()));
        });
    }
/*
    public void addNode(String radius, String angle) {
        processor.addNode(1, angle, radius);
    }

    public void deleteNode(int nodeID) {
    }

    public void addEdge(int startID, int endID) {
    }

    public void deleteEdge(int edgeID) {
    }

    public static EditHandler getEditHandler() {
        return instance;
    }*/
}
