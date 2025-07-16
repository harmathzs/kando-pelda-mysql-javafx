package org.example.kandopeldamysqljavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class DogsOwnersController implements Initializable {
    @FXML public VBox mainWindow;
    @FXML public TableView<Dog> dogsTableView;
    @FXML public TableColumn<Dog, Integer> dogIdTableColumn;
    @FXML public TableColumn<Dog, String> dogNameTableColumn;
    @FXML public TableColumn<Dog, Float> dogAgeTableColumn;
    @FXML public TableColumn<Dog, Boolean> dogMaleTableColumn;
    @FXML public TableColumn<Dog, Integer> dogOwneridTableColumn;
    @FXML public TableView ownersTableView;
    @FXML public TableColumn owneridTableColumn;
    @FXML public TableColumn ownerNameTableColumn;
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
        // Init dogsTableView
        dogIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dogNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dogAgeTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        dogMaleTableColumn.setCellValueFactory(new PropertyValueFactory<>("male"));
        //dogOwneridTableColumn.setCellValueFactory(new PropertyValueFactory<>("ownerid"));
        dogOwneridTableColumn.setCellValueFactory(cellData -> {
            Owner owner = cellData.getValue().getOwner();
            Integer ownerId = cellData.getValue().getId();
            String ownerName = (owner != null) ? owner.getName() : "";
            return new SimpleObjectProperty<>(ownerId);
        });
        List<Dog> dogs = MysqlService.queryDogs("localhost", "dogs_and_owners", "root", "", Collections.emptySet());
        ObservableList<Dog> dogList = FXCollections.observableArrayList(dogs);
        dogsTableView.setItems(dogList);

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