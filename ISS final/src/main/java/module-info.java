module com.example.iss {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;


    opens com.example.iss to javafx.fxml;
    exports com.example.iss;
}