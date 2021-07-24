package com.pse.hgv.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailHandler implements UIHandler {

    @FXML
    ColorPicker colorPick;
    @FXML
    TextField radius;
    @FXML
    TextField angle;
    @FXML
    TextField weight;
    @FXML
    private Pane detailPane;
    @FXML
    private Button updateButton;

    private static final int UPDATE_POSITION = 75;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateButton.layoutYProperty().bind(detailPane.heightProperty().subtract(UPDATE_POSITION));
    }

    @FXML
    public void updateData() {
        //TODO
        System.out.println(colorPick.getValue());
        System.out.println(radius.getText());
        System.out.println(angle.getText());
        System.out.println(weight.getText());

    }

}
