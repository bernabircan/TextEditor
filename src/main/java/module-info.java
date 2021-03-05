module com.ege {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ege to javafx.fxml;
    exports com.ege;
}
