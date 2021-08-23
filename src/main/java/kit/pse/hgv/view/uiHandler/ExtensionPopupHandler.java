package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.controller.commandController.commands.PauseExtensionCommand;
import kit.pse.hgv.controller.commandController.commands.ResumeExtensionCommand;
import kit.pse.hgv.controller.commandController.commands.StopExtensionCommand;
import kit.pse.hgv.controller.commandProcessor.ExtensionCommandProcessor;
import kit.pse.hgv.extensionServer.ClientInfo;
import kit.pse.hgv.extensionServer.ExtensionServer;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * This class manages the popup displaying and managing the extensions
 */
public class ExtensionPopupHandler implements UIHandler {

    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String PAUSE = "Pause";
    private static final int PREF_HEIGHT_ANCHOR = 50;
    private static final int PREF_WIDTH_ANCHOR = 500;
    private static final String AVAILABLE = "Verfügbare Erweiterungen";
    private static final String ACTIVE = "Aktive Erweiterungen";
    private static final Double MARGIN_TOP_BOTTOM = 10.0;
    private static final Double PAUSE_RIGHT = 66.0;
    private static final Double START_RIGHT = 120.0;
    private static final Double STOP_RIGHT = 20.0;
    private static final Double TEXT_LEFT = 6.0;
    @FXML
    private Accordion accordion;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane accPane;
    /**
     * Content: currently active ClientInfos
     */
    private Accordion activeAccordion;
    /**
     * Content: currently available paths for extensions
     */
    private Accordion availableAccordion;
    private ExtensionPopupHandler instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // instantiate accordions
        instance = this;
        activeAccordion = new Accordion();
        availableAccordion = new Accordion();
        // bind and setup( create) accordions
        accPane.prefWidthProperty().bind(anchor.widthProperty());
        AnchorPane.setBottomAnchor(accPane, 0.0);
        setupAccordion(accordion);
        setupAccordion(activeAccordion);
        setupAccordion(availableAccordion);

        // create subsection for active extensions
        TitledPane activeExtensions = new TitledPane();
        activeExtensions.setText(ACTIVE);
        activeExtensions.setContent(activeAccordion);
        HashMap<Integer, ClientInfo> map = ExtensionServer.getInstance().getClients();
        for (int key : map.keySet()) {
            TitledPane tobeAdded = new TitledPane();
            tobeAdded.setText(map.get(key).getName());
            accordion.getPanes().add(tobeAdded);
        }
        // create subsection for available extensions
        TitledPane availableExtensions = new TitledPane();
        availableExtensions.setText(AVAILABLE);
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

    /**
     * Adds a client to the UI section of activeExcentions.
     * 
     * @param id   the id of the extension
     * @param info the client info
     * @return
     */
    private TitledPane registerClient(int id, ClientInfo info) {
        TitledPane newTitledPane = new TitledPane();
        newTitledPane.setText(info.getName() + " - ID: " + id);

        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(PREF_HEIGHT_ANCHOR);
        pane.setPrefWidth(PREF_WIDTH_ANCHOR);
        addButtons(pane, id);

        newTitledPane.setContent(pane);
        return newTitledPane;
    }

    /**
     * Add needed buttons for a client.
     * 
     * @param pane
     */
    private void addButtons(AnchorPane pane, int id) {
        Button start = new Button(START);
        Button pause = new Button(PAUSE);
        Button stop = new Button(STOP);

        AnchorPane.setRightAnchor(stop, STOP_RIGHT);
        AnchorPane.setTopAnchor(stop, MARGIN_TOP_BOTTOM);
        AnchorPane.setRightAnchor(start, START_RIGHT);
        AnchorPane.setTopAnchor(start, MARGIN_TOP_BOTTOM);
        AnchorPane.setRightAnchor(pause, PAUSE_RIGHT);
        AnchorPane.setTopAnchor(pause, MARGIN_TOP_BOTTOM);


        start.setOnMouseClicked(mouseEvent -> {
            new ResumeExtensionCommand(id).execute();
        });

        pause.setOnMouseClicked(mouseEvent -> {
            new PauseExtensionCommand(id).execute();
        });

        stop.setOnMouseClicked(mouseEvent -> {
            new StopExtensionCommand(id).execute();
        });

        pane.getChildren().add(start);
        pane.getChildren().add(pause);
        pane.getChildren().add(stop);
    }

    /**
     * Add an extension the the available accordion.
     * 
     * @param path the path of the extension
     * @param name the extensions's name
     * @return
     */
    public TitledPane addAvailable(String path, String name) {
        TitledPane newTitledPane = new TitledPane();
        newTitledPane.setText(name);
        // setup AnchorPane
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(PREF_HEIGHT_ANCHOR);
        pane.setPrefWidth(PREF_WIDTH_ANCHOR);

        // setup start button
        Button start = new Button("Starte Erweiterung");
        start.setOnMouseClicked(mouseEvent -> {
            new ExtensionCommandProcessor().startExtension(path);
        });
        AnchorPane.setRightAnchor(start, MARGIN_TOP_BOTTOM);
        AnchorPane.setBottomAnchor(start, MARGIN_TOP_BOTTOM);

        // setup text
        Text text = new Text(path);
        pane.getChildren().add(start);
        pane.getChildren().add(text);
        AnchorPane.setTopAnchor(text, MARGIN_TOP_BOTTOM);
        AnchorPane.setLeftAnchor(text, TEXT_LEFT);

        newTitledPane.setContent(pane);
        return newTitledPane;
    }

    /**
     * This method refreshes the active extensions.
     */
    public void refresh() {
        activeAccordion.getPanes().clear();
        HashMap<Integer, ClientInfo> map = ExtensionServer.getInstance().getClients();
        System.out.println(map.size());
        for (int key : map.keySet()) {
            activeAccordion.getPanes().add(registerClient(key, map.get(key)));
        }
    }

    private void setupAccordion(Accordion accordion) {
        accordion.prefWidthProperty().bind(anchor.widthProperty());
        accordion.prefHeightProperty().bind(anchor.heightProperty().subtract(PREF_HEIGHT_ANCHOR));
    }

    public ExtensionPopupHandler getInstance() {
        return instance;
    }
}
