package de.home.todoapp.view;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Task;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ListViewController implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML public ListView<Task> listView;
    private Task task;

    @FXML
    private ToggleButton addBtn;
    @FXML
    private ToggleButton editBtn;
    @FXML
    private ToggleButton checkBtn;
    @FXML
    private ToggleButton emainlBtn;

    @FXML
    public ToggleButton allBtn;
    @FXML
    public ToggleButton hurryBtn;
    @FXML
    public ToggleButton openBtn;
    @FXML
    public ToggleButton noHurryBtn;
    @FXML
    public MenuButton otherBtn;

    @FXML
    private MenuItem newMenuBtn;

    @FXML
    private MenuItem loadMenuBtn;

    @FXML
    private MenuItem saveMenuBtn;

    @FXML
    private MenuItem saveAsMenuBtn;

    @FXML
    private MenuItem aboutMenuBtn;

    @FXML
    private MenuItem exitMenuBtn;



    public void setCellFactory() {
        listView.setCellFactory(
                new Callback<ListView<Task>, ListCell<Task>>() {
                    @Override
                    public ListCell<Task> call(ListView<Task> listView) {
                        return new ListViewCell();
                    }
                }
        );
    }

    public ListView<Task> getListView() {
        return listView;
    }

    // Reference to the main application.
    private MainApp mainApp;

    public ListViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert listView != null : "fx:id\"listView\" was not injected: check your FXML file 'ListView.fxml'.";
        setCellFactory();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        listView.setItems(mainApp.getObservableList());
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new task.
     */
    @FXML
    private void handleNewTask() {
        Task tempTask = new Task();
        boolean okClicked = mainApp.showEditDialog(tempTask);
        if (okClicked) {
            mainApp.getObservableList().add(tempTask);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditTask() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
            boolean okClicked = mainApp.showEditDialog(selectedTask);
            if (okClicked) {
                selectedTask.setName(selectedTask.getName());
                selectedTask.setInput(selectedTask.getInput());
                selectedTask.setFinishDate(selectedTask.getFinishDate());
                selectedTask.setPriority(selectedTask.getPriority());
                listView.refresh();
            }


        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");
            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteTask() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            listView.getItems().remove(selectedIndex);

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

//    public void enableFiltering() {
//        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
//        FilteredList<Task> filteredData = new FilteredList<>(mainApp.getObservableList(), r -> true);
//        // 2. Set the filter Predicate whenever the filter changes.
//
//
//        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(record -> {
//                // If filter text is empty, display all records.
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                // Compare all columns with filter text.
//                String lowerCaseFilter = newValue; //.toLowerCase();
//
//                if (record.getDebitAcc().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches debitAcc.
//                } else if (record.getCreditAcc().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches creditAcc.
////                } else if (record.getId().contains(lowerCaseFilter)) {
////                    return true; // Filter matches iD.
//                } else if (record.getAmount().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches amount.
//                } else if (record.getDate().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches date.
//                } else if (record.getDocNum().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches docNum.
//                } else return record.getTags().contains(lowerCaseFilter); // Filter matches tags.
//                // Does not match.
//            });
//        });
//
//        // 3. Wrap the FilteredList in a SortedList.
//        SortedList<Task> sortedData = new SortedList<>(filteredData);
//        // 4. Bind the SortedList comperator to the TableView comperator.
//        sortedData.comparatorProperty().bind(listView.comparatorProperty());
//        // 5. Add sorted (and filtered) data to the table.
//        listView.setItems(sortedData);
//    }

    /**
     * Is set as the standard selection.
     * Showing all of the content.
     * All other buttons will have a grey color.
     *
     */
    public void handleAll() {
        allBtn.setId("buttonSelected");
        hurryBtn.setId("buttonNotSelected");
        openBtn.setId("buttonNotSelected");
        noHurryBtn.setId("buttonNotSelected");
        otherBtn.setId("buttonNotSelected");
    }

    public void handleHurry() {
        hurryBtn.setId("buttonSelected");
        allBtn.setId("buttonNotSelected");
        openBtn.setId("buttonNotSelected");
        noHurryBtn.setId("buttonNotSelected");
        otherBtn.setId("buttonNotSelected");

    }

    public void handleOpen() {
        openBtn.setId("buttonSelected");
        hurryBtn.setId("buttonNotSelected");
        allBtn.setId("buttonNotSelected");
        noHurryBtn.setId("buttonNotSelected");
        otherBtn.setId("buttonNotSelected");


    }

    public void handleNoHurry() {
        noHurryBtn.setId("buttonSelected");
        openBtn.setId("buttonNotSelected");
        hurryBtn.setId("buttonNotSelected");
        allBtn.setId("buttonNotSelected");
        otherBtn.setId("buttonNotSelected");
    }

    public void handleOther() {
        otherBtn.setId("buttonSelected");
        noHurryBtn.setId("buttonNotSelected");
        openBtn.setId("buttonNotSelected");
        hurryBtn.setId("buttonNotSelected");
        allBtn.setId("buttonNotSelected");
    }

    /**
     * Creates an empty todoList.
     */
    @FXML
    private void handleNewMenuBtn() {
        mainApp.getObservableList().clear();
        mainApp.setTaskFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an task list to load.
     */
    @FXML
    private void handleOpenMenuBtn() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getStage());

        if (file != null) {
            mainApp.loadTaskDataFromFile(file);
        }
    }

    /**
     * Saves the file to the task file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveMenuBtn() {
        File taskFile = mainApp.getTaskFilePath();
        if (taskFile != null) {
            mainApp.saveTaskDataToFile(taskFile);
        } else {
            handleSaveAsMenuBtn();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAsMenuBtn() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveTaskDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAboutMenuBtn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TodoApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Henning Wuehn");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExitMenuBtn() {
        System.exit(0);
    }


}