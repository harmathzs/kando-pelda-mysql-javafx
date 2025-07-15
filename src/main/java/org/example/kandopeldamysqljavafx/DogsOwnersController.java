package org.example.kandopeldamysqljavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DogsOwnersController {
    @FXML public TableView dogsTableView;
    @FXML public TableView ownersTableView;
    @FXML public Spinner dogIdSpinner;
    @FXML public TextField dogNameTextField;
    @FXML public Spinner dogAgeSpinner;
    @FXML public ChoiceBox dogMaleChoiceBox;
    @FXML public Spinner dogOwneridSpinner;
    @FXML public Spinner ownerIdSpinner;
    @FXML public TextField ownerNameTextField;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onButton1Click() {
        //
    }
    @FXML
    protected void onButton2Click() {
        //
    }
    @FXML
    protected void onButton3Click() {
        //
    }
    @FXML
    protected void onButton4Click() {
        //
    }
    @FXML
    protected void onButton5Click() {
        //
    }
    @FXML
    protected void onButton6Click() {
        //
    }
    @FXML
    protected void onButton7Click() {
        //
    }
}