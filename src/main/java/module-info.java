module com.pse.hgv {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pse.hgv to javafx.fxml;
    opens com.pse.hgv.uiHandler to javafx.fxml;
    exports com.pse.hgv;
    exports com.pse.hgv.uiHandler;
}