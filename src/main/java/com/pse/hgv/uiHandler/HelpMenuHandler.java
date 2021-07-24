package com.pse.hgv.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpMenuHandler implements UIHandler {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void popUp() {
        System.out.println("Help popped up");
    }
}
