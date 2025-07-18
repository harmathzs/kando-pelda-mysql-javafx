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

    public List<Dog> dogs;
    public List<Owner> owners;

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
        if (!isRunningTest) dogIdSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1, 1)); // min, max, init, step

        // Init dogNameTextField
        if (!isRunningTest) dogNameTextField.setText("Fido");

        // Init dogAgeSpinner
        if (!isRunningTest) dogAgeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1, 1)); // min, max, init, step

        // Init dogMaleChoiceBox options
        ObservableList<String> dogMaleChoiceBoxObservableList = FXCollections.observableArrayList(Arrays.asList("female", "male"));
        if (!isRunningTest) dogMaleChoiceBox.setItems(dogMaleChoiceBoxObservableList);
        if (!isRunningTest) dogMaleChoiceBox.setValue("male");

        // Init dogOwneridSpinner
        if (!isRunningTest) dogOwneridSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1, 1)); // min, max, init, step

        // Init ownerIdSpinner
        if (!isRunningTest) ownerIdSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1, 1)); // min, max, init, step

        // Init ownerNameTextField
        if (!isRunningTest) ownerNameTextField.setText("Jane Doe");
    }

    public void refreshTables() {
        // Init dogsTableView
        if (!isRunningTest) dogIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (!isRunningTest) dogNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (!isRunningTest) dogAgeTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        if (!isRunningTest) dogMaleTableColumn.setCellValueFactory(new PropertyValueFactory<>("male"));
        //dogOwneridTableColumn.setCellValueFactory(new PropertyValueFactory<>("ownerid"));
        if (!isRunningTest) dogOwneridTableColumn.setCellValueFactory(cellData -> {
            Owner owner = cellData.getValue().getOwner();
            Integer ownerId = cellData.getValue().getId();
            String ownerName = (owner != null) ? owner.getName() : "";
            return new SimpleObjectProperty<>(ownerId);
        });
        dogs = MysqlService.queryDogs("localhost", "dogs_and_owners", "root", "", Collections.emptySet());
        ObservableList<Dog> dogList = FXCollections.observableArrayList(dogs);
        if (!isRunningTest) dogsTableView.setItems(dogList);
        // Init ownersTableView
        if (!isRunningTest) owneridTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (!isRunningTest) ownerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        owners = MysqlService.queryOwners("localhost", "dogs_and_owners", "root", "", Collections.emptySet());
        ObservableList<Owner> ownerList = FXCollections.observableArrayList(owners);
        if (!isRunningTest) ownersTableView.setItems(ownerList);
    }

    @FXML
    public void onHelloButtonClick() {
        // welcomeText.setText("Welcome to JavaFX Application!");
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
        Integer dogId = isRunningTest ? testDogId : dogIdSpinner.getValue();
        String dogName = isRunningTest ? testDogName : dogNameTextField.getText();
        float dogAge = isRunningTest ? testDogAge : dogAgeSpinner.getValue();
        boolean dogMale = isRunningTest ? testDogMale : dogMaleChoiceBox.getValue()=="male";
        Integer ownerId = isRunningTest ? testOwnerId : dogOwneridSpinner.getValue();
        Owner dogOwner = null;
        for (Owner owner: owners) {
            if (Objects.equals(owner.getId(), ownerId)) {
                dogOwner = owner;
            }
        }
        Dog[] dogs = new Dog[1];
        dogs[0] = new Dog(dogId, dogName, dogAge, dogMale, dogOwner);
        MysqlService.upsertDogs("localhost", "dogs_and_owners", "root", "", dogs);

        refreshTables();

    }
    @FXML
    protected void onButton3Click() {
        // 3. Update existing Owner
        Integer ownerId = isRunningTest ? testOwnerId : ownerIdSpinner.getValue();
        String ownerName = isRunningTest ? testOwnerName : ownerNameTextField.getText();
        Owner[] owners = new Owner[1];
        owners[0] = new Owner(ownerId, ownerName);
        MysqlService.upsertOwners("localhost", "dogs_and_owners", "root", "", owners);

        refreshTables();
    }
    @FXML
    protected void onButton4Click() {
        // 4. Update existing Dog
        Integer dogId = isRunningTest ? testDogId : dogIdSpinner.getValue();
        String dogName = isRunningTest ? testDogName : dogNameTextField.getText();
        float dogAge = isRunningTest ? testDogAge : dogAgeSpinner.getValue();
        boolean dogMale = isRunningTest ? testDogMale : dogMaleChoiceBox.getValue()=="male";
        Integer ownerId = isRunningTest ? testOwnerId : dogOwneridSpinner.getValue();
        Owner dogOwner = null;
        for (Owner owner: owners) {
            if (Objects.equals(owner.getId(), ownerId)) {
                dogOwner = owner;
            }
        }
        Dog[] dogs = new Dog[1];
        dogs[0] = new Dog(dogId, dogName, dogAge, dogMale, dogOwner);
        MysqlService.upsertDogs("localhost", "dogs_and_owners", "root", "", dogs);

        refreshTables();
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
        Integer dogId = isRunningTest ? testDogId : dogIdSpinner.getValue();
        MysqlService.deleteDogs("localhost", "dogs_and_owners", "root", "", new HashSet<>(List.of(dogId)));

        refreshTables();
    }
    @FXML
    protected void onButton7Click() {
        // 7. Exit program
        Platform.exit();
    }


}