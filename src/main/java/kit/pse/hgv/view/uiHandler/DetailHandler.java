package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kit.pse.hgv.controller.commandProcessor.HyperModelCommandProcessor;
import kit.pse.hgv.controller.commandProcessor.MetaDataProcessor;
import kit.pse.hgv.representation.PolarCoordinate;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * This class manages the detail section of the program.
 */
public class DetailHandler implements UIHandler {

    private static final String DIRECT = "direct";
    private static final String LOW = "low";
    private static final String MEDIUM = "medium";
    private static final String HIGH = "high";
    private static final String NO_ID = "---";
    private static final String RADIUS = "r";
    private static final String PHI = "phi";
    private static final String COLOR = "color";
    private static final double EIGHTTEEN = 18;
    @FXML
    private ColorPicker colorPick;
    @FXML
    private TextField radius;
    @FXML
    private TextField angle;
    @FXML
    private TextField weight;
    @FXML
    private Pane detailPane;
    @FXML
    private Button updateButton;
    @FXML
    private Text idText;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Text accuracyText;
    private static Accuracy currentAcc = Accuracy.DIRECT;

    /**
     * Attributes to store the currently displayed information of a node.
     */
    private int currentID;
    private Color currentColor;
    private double currentAngle;
    private double currentRadius;

    /**
     * Kind of a singleton. There should only be a single DetailHandler and it has
     * to be accessible without a direct link between the accessor and the accessed.
     */
    private static DetailHandler instance;

    private static final int UPDATE_POSITION = 75;

    /**
     * Constructor cannot be declared private due to JavaFX issues.
     */
    public DetailHandler() {
    }

    public static Accuracy getCurrentAccuracy() {
        return currentAcc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        updateButton.layoutYProperty().bind(detailPane.heightProperty().subtract(UPDATE_POSITION));
        choiceBox.layoutYProperty()
                .bind(detailPane.heightProperty().subtract(UPDATE_POSITION).subtract(UPDATE_POSITION));
        accuracyText.layoutYProperty().bind(choiceBox.layoutYProperty().add(EIGHTTEEN));
        choiceBox.getItems().add(DIRECT);
        choiceBox.getItems().add(LOW);
        choiceBox.getItems().add(MEDIUM);
        choiceBox.getItems().add(HIGH);
        choiceBox.setValue(DIRECT);
        choiceBox.setOnAction(event -> {
            String mode = choiceBox.getValue().toUpperCase();
            new HyperModelCommandProcessor().setAccuracy(mode);
            currentAcc = Accuracy.valueOf(choiceBox.getValue().toUpperCase());
        });
    }

    /**
     * The method updates the meta data of a node if the user clicks the
     * updateButton
     */
    @FXML
    private void updateData() {
        MetaDataProcessor processor = new MetaDataProcessor();

        if (currentRadius == 0 && currentAngle == 0) {
            processor.editMetaData(currentID, COLOR, colorPick.getValue().toString());
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(COLOR, colorPick.getValue().toString());
            map.put(RADIUS, radius.getText());
            map.put(PHI, angle.getText());
            processor.editMetaData(currentID, map);
        }
    }

    public static DetailHandler getInstance() {
        return instance;
    }

    /**
     * This method updates the currently displayed information (if the object is a
     * node).
     * 
     * @param currentlySelected the ID of the currently selected node
     * @param color
     * @param toPolar           the coordinates of the currently selected nodes.
     */
    void updateDisplayedData(int currentlySelected, Color color, PolarCoordinate toPolar) {
        currentID = currentlySelected;
        currentColor = color;
        currentAngle = toPolar.getAngle();
        currentRadius = toPolar.getDistance();
        idText.setText(String.valueOf(currentlySelected));
        colorPick.setValue(color);
        radius.setText(String.valueOf(toPolar.getDistance()));
        angle.setText(String.valueOf(toPolar.getAngle()));
    }

    /**
     * This method updates the currently displayed information (if the object is an
     * edge).
     * 
     * @param currentlySelected the ID of the currently selected edge
     * @param color
     */
    void updateDisplayData(int currentlySelected, Color color) {
        currentID = currentlySelected;
        currentColor = color;
        currentAngle = 0;
        currentRadius = 0;
        idText.setText(String.valueOf(currentlySelected));
        colorPick.setValue(color);
        radius.setText("");
        angle.setText("");
    }
}
