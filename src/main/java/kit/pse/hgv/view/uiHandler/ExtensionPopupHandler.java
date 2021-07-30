package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.App;
import kit.pse.hgv.controller.commandProcessor.ExtensionCommandProcessor;
import kit.pse.hgv.extensionServer.ClientInfo;
import kit.pse.hgv.extensionServer.ExtensionServer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * This class manages the popup displaying and managing the extensions
 */
public class ExtensionPopupHandler implements UIHandler {

    @FXML
    private Accordion accordion;
    private Accordion activeAccordion;
    private Accordion availableAccordion;
    private ExtensionPopupHandler instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
         activeAccordion = new Accordion();
         availableAccordion = new Accordion();
        TitledPane activeExtensions = new TitledPane();
        activeExtensions.setText("Aktive Erweiterungen");
        activeExtensions.setContent(activeAccordion);
        HashMap<Integer, ClientInfo> map = ExtensionServer.getInstance().getClients();
        for (int key : map.keySet()) {
            TitledPane tobeAdded = new TitledPane();
            tobeAdded.setText(map.get(key).getName());
            accordion.getPanes().add(tobeAdded);
        }
        TitledPane availableExtensions = new TitledPane();
        availableExtensions.setText("Verfügbare Erweiterungen");
        availableExtensions.setContent(availableAccordion);
        accordion.getPanes().add(activeExtensions);
        accordion.getPanes().add(availableExtensions);

        refresh();
    }

    /**
     * The method allows the user to choose a new extension and add it.
     */
    @FXML
    public void selectExtension() {
        FileChooser chooser = new FileChooser();
        File extension = chooser.showOpenDialog(new Stage());
        availableAccordion.getPanes().add(addAvailable(extension.getAbsolutePath(), extension.getName()));
    }

    public TitledPane registerClient(int id, ClientInfo info){
        TitledPane newTitledPane = new TitledPane();
        newTitledPane.setText(info.getName() + "ID: " + id);

        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(50);
        pane.setPrefWidth(500);

        Button start = new Button("Start");

        Button pause = new Button("Pause");
        Button stop = new Button("Stop");
        AnchorPane.setRightAnchor(stop, 20.0);
        AnchorPane.setTopAnchor(stop, 10.0);
        AnchorPane.setRightAnchor(start, 120.0);
        AnchorPane.setTopAnchor(start, 10.0);
        AnchorPane.setRightAnchor(pause, 66.0);
        AnchorPane.setTopAnchor(pause, 10.0);

        pane.getChildren().add(start);
        pane.getChildren().add(pause);
        pane.getChildren().add(stop);

        newTitledPane.setContent(pane);
        return newTitledPane;
    }

    public TitledPane addAvailable(String path, String name) {
        TitledPane newTitledPane = new TitledPane();
        newTitledPane.setText(name);
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(60);
        pane.setPrefWidth(500);

        Button start = new Button("Starte Erweiterung");
        AnchorPane.setRightAnchor(start, 10.0);
        AnchorPane.setBottomAnchor(start, 10.0);
        Text text = new Text(path);
        pane.getChildren().add(start);
        pane.getChildren().add(text);
        AnchorPane.setTopAnchor(text, 10.0);
        AnchorPane.setLeftAnchor(text, 5.0);
        newTitledPane.setContent(pane);
        return newTitledPane;
    }

    public void refresh(){
        activeAccordion.getPanes().clear();
        HashMap<Integer, ClientInfo> map = ExtensionServer.getInstance().getClients();
        for(int key : map.keySet()) {
            registerClient(key, map.get(key));
        }
    }

    public ExtensionPopupHandler getInstance() {
        return instance;
    }
}
