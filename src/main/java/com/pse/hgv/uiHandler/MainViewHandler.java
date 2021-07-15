package com.pse.hgv.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewHandler implements UIHandler, Initializable {

    @FXML
    private AnchorPane primaryAnchorPane;
    @FXML
    private MenuBar viewMenuBar;
    @FXML
    private Pane renderPane;
    @FXML
    private Pane editPane;
    @FXML
    private Pane detailPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewMenuBar.prefWidthProperty().bind(primaryAnchorPane.widthProperty());
        renderPane.prefWidthProperty().bind(primaryAnchorPane.widthProperty()); //////////negativ evtl
        renderPane.prefHeightProperty().bind(primaryAnchorPane.heightProperty());

        editPane.prefHeightProperty().bind(primaryAnchorPane.heightProperty());
        detailPane.prefHeightProperty().bind(primaryAnchorPane.heightProperty());
    }
}
