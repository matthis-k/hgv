package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kit.pse.hgv.controller.commandController.commands.EditColorCommand;
import kit.pse.hgv.controller.commandController.commands.EditUserMetaCommand;
import kit.pse.hgv.controller.commandProcessor.MetaDataProcessor;
import kit.pse.hgv.representation.PolarCoordinate;

import java.net.URL;
import java.util.ResourceBundle;

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

    private int currentID;
    private Color currentColor;
    private double currentAngle;
    private double currentRadius;

    private static DetailHandler instance;


    private static final int UPDATE_POSITION = 75;

    public DetailHandler(){}

    @FXML
    public void updateData() {
        MetaDataProcessor processor = new MetaDataProcessor();
        processor.editMetaData(currentID, "r", radius.getText());
        processor.editMetaData(currentID, "phi", angle.getText());
        processor.editMetaData(currentID, "weight", weight.getText());
        processor.changeColor(currentID, colorPick.getValue());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        updateButton.layoutYProperty().bind(detailPane.heightProperty().subtract(UPDATE_POSITION));
    }

    public static DetailHandler getInstance() {
        return instance;
    }

    public void updateDisplayedDate(int currentlySelected, Color color, PolarCoordinate toPolar) {
        currentID = currentlySelected;
        currentColor = color;
        currentAngle = toPolar.getAngle();
        currentRadius = toPolar.getDistance();
        idText.setText(String.valueOf(currentlySelected));
        colorPick.setValue(color);
        radius.setText(String.valueOf(toPolar.getDistance()));
        angle.setText(String.valueOf(toPolar.getAngle()));
    }
}
