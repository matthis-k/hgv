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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the detail section of the program.
 */
public class DetailHandler implements UIHandler {

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

    /**
     * Attributes to store the currently displayed information of a node.
     */
    private int currentID;
    private Color currentColor;
    private double currentAngle;
    private double currentRadius;

    /**
     * Kind of a singleton. There should only be a single DetailHandler and it has to be accessible without a direct link
     * between the accessor and the accessed.
     */
    private static DetailHandler instance;

    private static final int UPDATE_POSITION = 75;

    /**
     * Constructor cannot be declared private due to JavaFX issues.
     */
    public DetailHandler(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        updateButton.layoutYProperty().bind(detailPane.heightProperty().subtract(UPDATE_POSITION));
        choiceBox.getItems().add("direct");
        choiceBox.getItems().add("low");
        choiceBox.getItems().add("medium");
        choiceBox.getItems().add("high");
        choiceBox.setValue("high");
    }

    /**
     * The method updates the meta data of a node if the user clicks the updateButton
     */
    @FXML
    public void updateData() {
        MetaDataProcessor processor = new MetaDataProcessor();
        HyperModelCommandProcessor hyperProcessor = new HyperModelCommandProcessor();

        String mode = choiceBox.getValue().toString().toUpperCase();

        if(idText.getText().equals("---")) {
            hyperProcessor.setAccuracy(mode);
        } else if (currentRadius == 0 && currentAngle == 0){
            processor.changeColor(currentID, colorPick.getValue());
            hyperProcessor.setAccuracy(mode);
        } else {
            processor.changeColor(currentID, colorPick.getValue());
            processor.editMetaData(currentID, "r", radius.getText());
            processor.editMetaData(currentID, "phi", angle.getText());
            hyperProcessor.setAccuracy(mode);

        }
    }


    public static DetailHandler getInstance() {
        return instance;
    }

    /**
     * This method updates the currently displayed information.
     * @param currentlySelected the ID of the currently selected node
     * @param color
     * @param toPolar the coordinates of the currently selected nodes.
     */
    public void updateDisplayedData(int currentlySelected, Color color, PolarCoordinate toPolar) {
        currentID = currentlySelected;
        currentColor = color;
        currentAngle = toPolar.getAngle();
        currentRadius = toPolar.getDistance();
        idText.setText(String.valueOf(currentlySelected));
        colorPick.setValue(color);
        radius.setText(String.valueOf(toPolar.getDistance()));
        angle.setText(String.valueOf(toPolar.getAngle()));
    }

    public void updateDisplayData(int currentlySelected, Color color) {
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
