package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the help section of the menu bar.
 */
public class HelpMenuHandler implements UIHandler {

    @FXML
    private Menu helpMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method pops up a window with information about the team.
     */
    @FXML
    public void popUp() {
    }

    public void show() {
        helpMenu.show();
    }
}
