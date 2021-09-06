package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import kit.pse.hgv.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is in charge of opening a new popup.
 */
public class ExtensionMenuHandler implements UIHandler {

    private static final String EXTENSIONS = "Erweiterungen";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method allows the pop up to pop up.
     * 
     * @throws IOException
     */
    public void onClick() throws IOException {
        Parent root;
        Stage popupStage = new Stage();
        root = FXMLLoader.load(App.class.getResource("ExtensionPopupContainer.fxml"));
        popupStage.setScene(new Scene(root));
        popupStage.setTitle(EXTENSIONS);
        popupStage.getIcons().add(new Image(App.class.getResourceAsStream("/hyperbolicthomas.png")));
        popupStage.show();
    }
}
