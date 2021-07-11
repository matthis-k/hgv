module com.pse.hgv {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pse.hgv to javafx.fxml;
    exports com.pse.hgv;
}