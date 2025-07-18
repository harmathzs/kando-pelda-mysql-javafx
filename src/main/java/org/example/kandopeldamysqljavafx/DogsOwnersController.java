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
import java.util.*;

public class DogsOwnersController implements Initializable {
    public boolean isRunningTest = false;

    int testOwnerId = 1;
    String testOwnerName = "Jane Doe";
    int testDogId = 1;
    String testDogName = "Fido";
    float testDogAge = 1;
    boolean testDogMale = false;

    @FXML public VBox mainWindow;
    @FXML public TableView<Dog> dogsTableView;
    @FXML public TableColumn<Dog, Integer> dogIdTableColumn;
    @FXML public TableColumn<Dog, String> dogNameTableColumn;
    @FXML public TableColumn<Dog, Float> dogAgeTableColumn;
    @FXML public TableColumn<Dog, Boolean> dogMaleTableColumn;
    @FXML public TableColumn<Dog, Integer> dogOwneridTableColumn;
    @FXML public TableView<Owner> ownersTableView;
    @FXML public TableColumn<Owner, Integer> owneridTableColumn;
    @FXML public TableColumn<Owner, String> ownerNameTableColumn;
    @FXML public Spinner<Integer> dogIdSpinner;
    @FXML public TextField dogNameTextField;
    @FXML public Spinner<Integer> dogAgeSpinner;
    @FXML public ChoiceBox dogMaleChoiceBox;
    @FXML public Spinner<Integer> dogOwneridSpinner;
    @FXML public Spinner<Integer> ownerIdSpinner;
    @FXML public TextField ownerNameTextField;



    @FXML
    private Label welcomeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTables();

        // Init dogIdSpinner
        dogIdSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1, 1)); // min, max, init, step

        // Init dogNameTextField
        dogNameTextField.setText("Fido");

        // Init dogAgeSpinner
        dogAgeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1, 1)); // min, max, init, step

        // Init dogMaleChoiceBox options
        ObservableList<String> dogMaleChoiceBoxObservableList = FXCollections.observableArrayList(Arrays.asList("female", "male"));
        dogMaleChoiceBox.setItems(dogMaleChoiceBoxObservableList);
        dogMaleChoiceBox.setValue("male");

        // Init dogOwneridSpinner
        dogOwneridSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1, 1)); // min, max, init, step

        // Init ownerIdSpinner
        ownerIdSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1, 1)); // min, max, init, step

        // Init ownerNameTextField
        ownerNameTextField.setText("Jane Doe");
    }

    public void refreshTables() {
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
        // Init ownersTableView
        owneridTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ownerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        List<Owner> owners = MysqlService.queryOwners("localhost", "dogs_and_owners", "root", "", Collections.emptySet());
        ObservableList<Owner> ownerList = FXCollections.observableArrayList(owners);
        ownersTableView.setItems(ownerList);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onButton1Click() {
        // 1. Insert new Owner
        String ownerName = isRunningTest ? testOwnerName : ownerNameTextField.getText();
        Integer ownerId = isRunningTest ? testOwnerId : ownerIdSpinner.getValue();
        Owner[] owners = new Owner[1];
        owners[0] = new Owner(ownerId, ownerName);
        MysqlService.upsertOwners("localhost", "dogs_and_owners", "root", "", owners);

        refreshTables();
    }
    @FXML
    protected void onButton2Click() {
        // 2. Insert new Dog
    }
    @FXML
    protected void onButton3Click() {
        // 3. Update existing Owner
    }
    @FXML
    protected void onButton4Click() {
        // 4. Update existing Dog
    }
    @FXML
    protected void onButton5Click() {
        // 5. Delete existing Owner
        Integer ownerId = isRunningTest ? testOwnerId : ownerIdSpinner.getValue();
        MysqlService.deleteOwners("localhost", "dogs_and_owners", "root", "", new HashSet<>(List.of(ownerId)));

        refreshTables();

    }
    @FXML
    protected void onButton6Click() {
        // 6. Delete existing Dog
    }
    @FXML
    protected void onButton7Click() {
        // 7. Exit program
        Platform.exit();
    }


}