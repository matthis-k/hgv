package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the selection section of the menu bar.
 */
public class SelectionMenuHandler implements UIHandler {

    @FXML
    private Menu selectMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Method to dismiss the currently selected Nodes.
     */
    @FXML
    public void dismissSelection() {
        System.out.println("Selection dismissed");
    }

    /**
     * This method inverts the current selection.
     */
    @FXML
    public void invertSelection() {
        System.out.println("Selection inverted");
    }

    /**
     * This method selects all available nodes.
     */
    @FXML
    public void selectAll() {
        System.out.println("Selected all");
    }

    public void show() {
        selectMenu.show();
    }
}
