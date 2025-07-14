package org.example.kandopeldamysqljavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DogsOwnersController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}