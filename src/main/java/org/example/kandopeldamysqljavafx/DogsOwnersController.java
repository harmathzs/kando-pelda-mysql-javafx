package org.example.kandopeldamysqljavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

public class DogsOwnersController implements Initializable {
    @FXML public VBox mainWindow;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Init dogMaleChoiceBox options
        ObservableList<String> dogMaleChoiceBoxObservableList = FXCollections.observableArrayList(Arrays.asList("female", "male"));
        dogMaleChoiceBox.setItems(dogMaleChoiceBoxObservableList);
    }

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
        Platform.exit();
    }


}