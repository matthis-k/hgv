package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FileMenuHandler implements UIHandler {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void openFile() {
        FileChooser chooser = new FileChooser();
        chooser.showOpenDialog(new Stage());
        System.out.println("File opened");
    }

    @FXML
    public void saveFile() {
        FileChooser chooser = new FileChooser();
        chooser.showOpenDialog(new Stage());
        System.out.println("File saved");
    }

    @FXML
    public void createNewGraph() {
        System.out.println("New graph");
    }
}