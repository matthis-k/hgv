module com.pse.hgv {
    requires javafx.controls;
    requires javafx.fxml;

    opens kit.pse.hgv to javafx.fxml;
    opens kit.pse.hgv.uiHandler to javafx.fxml;
    exports kit.pse.hgv;
    exports kit.pse.hgv.uiHandler;
}