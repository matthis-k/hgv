package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.App;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtensionPopupHandler implements UIHandler {

    @FXML
    private Accordion accordion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void selectExtension() {
        FileChooser chooser = new FileChooser();
        File extension = chooser.showOpenDialog(new Stage());
    }

    @FXML
    public void addExtension() throws IOException {
        if(accordion.getPanes().size() < 10) {
            TitledPane newTitled = FXMLLoader.load(App.class.getResource("PopupElement.fxml"));
            accordion.getPanes().add(newTitled);
        } else {
            //TODO EXception
        }
    }
}
