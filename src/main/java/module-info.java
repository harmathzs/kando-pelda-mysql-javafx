module org.example.kandopeldamysqljavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;


    opens org.example.kandopeldamysqljavafx to javafx.fxml;
    exports org.example.kandopeldamysqljavafx;
}