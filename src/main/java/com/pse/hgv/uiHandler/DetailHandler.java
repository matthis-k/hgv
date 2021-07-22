package com.pse.hgv.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailHandler implements UIHandler, Initializable {

    @FXML
    private Pane detailPane;
    @FXML
    private Button updateButton;

    private static final int UPDATE_POSITION = 75;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateButton.layoutYProperty().bind(detailPane.heightProperty().subtract(UPDATE_POSITION));
    }

}
