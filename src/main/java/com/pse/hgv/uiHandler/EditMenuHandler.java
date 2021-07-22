package com.pse.hgv.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMenuHandler implements UIHandler, Initializable {

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
}
