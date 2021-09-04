package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorPopupHandler implements UIHandler{

    @FXML
    private Text errorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorText.setText("penis");
    }
}
