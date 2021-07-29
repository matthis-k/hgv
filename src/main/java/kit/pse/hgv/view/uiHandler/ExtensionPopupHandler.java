package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.App;
import kit.pse.hgv.controller.commandProcessor.ExtensionCommandProcessor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the popup displaying and managing the extensions
 */
public class ExtensionPopupHandler implements UIHandler {

    @FXML
    private Accordion accordion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * The method allows the user to choose a new extension and add it.
     */
    @FXML
    public void selectExtension() {
        FileChooser chooser = new FileChooser();
        File extension = chooser.showOpenDialog(new Stage());
        new ExtensionCommandProcessor().registerExtension(extension.getAbsolutePath());
    }

    /**
     * This method adds a new PopupElement instance for an extension.
     * @throws IOException if PopupElement.fxml couldn't be accessed
     */
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
