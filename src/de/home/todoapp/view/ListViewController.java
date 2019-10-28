package de.home.todoapp.view;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.*;
import de.home.todoapp.util.Dispatcher;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.prefs.Preferences;

public class ListViewController implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML public ListView<Task> listView;

    @FXML private HBox hBoxFilters;
    @FXML private ToggleButton allBtn;
    @FXML public ToggleButton hurryBtn;
    @FXML public ToggleButton openBtn;
    @FXML public ToggleButton noHurryBtn;
    @FXML public MenuButton otherBtn;

    /*@FXML private MenuItem newMenuBtn;
    @FXML private MenuItem loadMenuBtn;
    @FXML private MenuItem saveMenuBtn;
    @FXML private MenuItem saveAsMenuBtn;
    @FXML private MenuItem aboutMenuBtn;
    @FXML private MenuItem exitMenuBtn;*/


    private TaskAdministration taskAdministration ;
    private ToggleGroup filtersGroup = new ToggleGroup();

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
    private IMainController mainController;

    public ListViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init listview");
        assert listView != null : "fx:id\"listView\" was not injected: check your FXML file 'ListView.fxml'.";

        //setAppState(new TaskAdministration());

        //taskAdministration.loadTestData();
        setCellFactory();
        //listView.itemsProperty().bind(taskAdministration.viewableTasksProperty());
        listView.setContextMenu(createContextMenu());

        allBtn.setUserData(new PriorityMatcher(Priority.Alle)); // "*"
        allBtn.setOnAction( toggleHandler );
        allBtn.setToggleGroup( filtersGroup );

        hurryBtn.setUserData(new PriorityMatcher(Priority.Eilt));
        hurryBtn.setOnAction( toggleHandler );
        hurryBtn.setToggleGroup( filtersGroup );

        openBtn.setUserData(new PriorityMatcher(Priority.Offen));
        openBtn.setOnAction(toggleHandler);
        openBtn.setToggleGroup(filtersGroup);

        noHurryBtn.setUserData(new PriorityMatcher(Priority.Eilt_nicht));
        noHurryBtn.setOnAction(toggleHandler);
        noHurryBtn.setToggleGroup(filtersGroup);
    }

    private ContextMenu createContextMenu() {
        ContextMenu cm = new ContextMenu();
        MenuItem finish = new MenuItem("Finish");
        MenuItem edit = new MenuItem("Edit");
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        finish.setOnAction((evt) -> Dispatcher.getInstance().removeTask(selectedTask));
        edit.setOnAction((evt) -> {
            //mainController.showEditDialog(selectedTask);

//
        });
        cm.getItems().add(finish);
        cm.getItems().add(edit);
        return cm;
    }

    private EventHandler
            <ActionEvent> toggleHandler = event -> {
        ToggleButton tb = (ToggleButton)event.getSource();
        Predicate<Task> filter = (Predicate<Task>)tb.getUserData();
        Dispatcher.getInstance().filter(filter);
    };

    @FXML
    public void showAddPlayer() {

        Dispatcher.getInstance().newTask();

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param controller
     */
    public void setMainController(IMainController controller) {
        this.mainController = controller;



    }

    public void setAppState(TaskAdministration appState) {
        taskAdministration= appState;
        //listView.itemsProperty().bind(appState.viewableTasksProperty());
        listView.setItems(appState.getViewableTasks());

    }



    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditTask() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            Dispatcher.getInstance().editTask(listView.getSelectionModel().getSelectedItem());
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainController.getStage());
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
            alert.initOwner(mainController.getStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

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
    @FXML private void handleNewMenuBtn() {
        taskAdministration.getTasks().clear();
    }

    /**
     * Opens a FileChooser to let the user select an task list to load.
     */
    @FXML private void handleOpenMenuBtn() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainController.getStage());

        if (file != null) {
            Dispatcher.getInstance().loadTaskDataFromFile(file);
        }
    }

    /**
     * Saves the file to the task file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML private void handleSaveMenuBtn() {
        File taskFile = getTaskFilePath();
        if (taskFile != null) {
            saveTaskDataToFile(taskFile);
        } else {
            handleSaveAsMenuBtn();
        }
    }
    @FXML private void handleSaveAsMenuBtn() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainController.getStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            saveTaskDataToFile(file);
        }
    }
    @FXML private void handleAboutMenuBtn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TodoApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Henning Wuehn");

        alert.showAndWait();
    }
    @FXML private void handleExitMenuBtn() {
        System.exit(0);
    }

    /**
     * Loads task data from the specified file. The current task data will
     * be replaced.
     *
     * @param file
     */


    /**
     * Returns the task file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getTaskFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Saves the current task data to the specified file.
     *
     * @param file
     */
    public void saveTaskDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(XMLWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our task data.
            try {
                XMLWrapper wrapper = new XMLWrapper();
                wrapper.setTasks(taskAdministration.getTasks());
                // Marshalling and saving XML to the file.
                m.marshal(wrapper, file);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Save the file path to the registry.
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }





}