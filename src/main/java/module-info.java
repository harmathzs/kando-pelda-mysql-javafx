module org.example.kandopeldamysqljavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.kandopeldamysqljavafx to javafx.fxml;
    exports org.example.kandopeldamysqljavafx;
}