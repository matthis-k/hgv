package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import kit.pse.hgv.controller.commandProcessor.MetaDataProcessor;

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


    private static final int UPDATE_POSITION = 75;


    @FXML
    public void updateData() {
        MetaDataProcessor processor = new MetaDataProcessor();
        //TODO ID
        processor.editMetaData(42, "r", radius.getText());
        processor.editMetaData(42, "phi", angle.getText());
        processor.editMetaData(42, "weight", weight.getText());
        processor.changeColor(42, colorPick.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            updateButton.layoutYProperty().bind(detailPane.heightProperty().subtract(UPDATE_POSITION));
    }
}
