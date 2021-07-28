module kit.pse.hgv {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.apache.commons.collections4;
    requires commons.math3;

    opens kit.pse.hgv.view.uiHandler to javafx.fxml;
    exports kit.pse.hgv.view.uiHandler;
    exports kit.pse.hgv;
    opens kit.pse.hgv to javafx.fxml;
}