package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import kit.pse.hgv.App;
import kit.pse.hgv.view.RenderModel.DefaultRenderEngine;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorPopupHandler implements UIHandler{

    @FXML
    private AnchorPane textPane;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            errorLabel.setText(DefaultRenderEngine.getErrorMessage());
            errorLabel.setWrapText(true);
    }

}
