module PiDev {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    opens controllers to javafx.fxml;
    opens org.example to javafx.graphics, javafx.fxml;
}