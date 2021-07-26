package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the edit section of the menu bar.
 */
public class EditMenuHandler implements UIHandler {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method reverts the last called command.
     */
    @FXML
    public void undo() {
        System.out.println("Undo");
    }
        //TODO ÄNDERN
    /**
     * This method reverts undo().
     */
    @FXML
    public void redo() {
        System.out.println("Redo");
    }

}
