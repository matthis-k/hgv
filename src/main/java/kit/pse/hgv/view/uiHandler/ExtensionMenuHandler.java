package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import javafx.stage.Stage;
import kit.pse.hgv.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtensionMenuHandler implements UIHandler {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onClick() throws IOException {
        Parent root;
        Stage popupStage = new Stage();
        root = FXMLLoader.load(App.class.getResource("ExtensionPopupContainer.fxml"));
        popupStage.setScene(new Scene(root));
        popupStage.setTitle("Erweiterungen");
        popupStage.getIcons().add(new Image(App.class.getResourceAsStream("hgv.png")));
        popupStage.show();
    }
}
