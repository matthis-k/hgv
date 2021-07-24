package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kit.pse.hgv.controller.commandProcessor.FileSystemCommandProcessor;

import java.net.URL;
import java.util.ResourceBundle;

public class FileMenuHandler implements UIHandler {

    private FileSystemCommandProcessor processor;

    //public FileMenuHandler(FileSystemCommandProcessor processor) {
      //  this.processor = processor;
    //}

    @FXML
    public void openFile() {
        FileChooser chooser = new FileChooser();
        processor.loadGraph(chooser.showOpenDialog(new Stage()));
    }

    @FXML
    public void saveFile() {
        FileChooser chooser = new FileChooser();
        String path = chooser.showOpenDialog(new Stage()).getAbsolutePath();
        //TODO: ID bekommen processor.saveGraph(path, 42);
    }

    @FXML
    public void createNewGraph() {
        processor.createNewGraph();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
