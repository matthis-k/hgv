package com.pse.hgv.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMenuHandler implements UIHandler {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void redo() {
        System.out.println("Redo");
    }

    @FXML
    public void undo() {
        System.out.println("Undo");
    }
}
