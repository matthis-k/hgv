package com.pse.hgv.uiHandler;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectionMenuHandler implements UIHandler {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void dismissSelection() {
        System.out.println("Selection dismissed");
    }

    @FXML
    public void invertSelection() {
        System.out.println("Selection inverted");
    }

    @FXML
    public void selectAll() {
        System.out.println("Selected all");
    }
}
