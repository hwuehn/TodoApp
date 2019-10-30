package de.home.todoapp.view;

import de.home.todoapp.model.Priority;
import de.home.todoapp.model.PriorityMatcher;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.service.Dispatcher;
import de.home.todoapp.service.TaskMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ListViewController implements Initializable {

    @FXML
    public ListView<Task> listView;
    @FXML
    public ToggleButton hurryBtn;
    @FXML
    public ToggleButton openBtn;
    @FXML
    public ToggleButton noHurryBtn;
    @FXML
    public MenuButton otherBtn;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private HBox hBoxFilters;
    @FXML
    private ToggleButton allBtn;
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

    // Reference to the main application.
    private IMainController mainController;

    public ListViewController() {
    }

    private EventHandler
            <ActionEvent> toggleHandler = event -> {
        ToggleButton tb = (ToggleButton) event.getSource();
        Predicate<Task> filter = (Predicate<Task>) tb.getUserData();
        //Dispatcher.getInstance().dispatch(new FilterMessage("filter"));
        Dispatcher.getInstance().filter(filter);
    };

    private ContextMenu createContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem finish = new MenuItem("Finish");
        MenuItem edit = new MenuItem("Edit");

        finish.setOnAction((evt) -> Dispatcher.getInstance().dispatch(new TaskMessage("remove_task")));
        edit.setOnAction((evt) -> Dispatcher.getInstance().dispatch(new TaskMessage("edit_task")));
        menu.getItems().add(finish);
        menu.getItems().add(edit);
        return menu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init listview");
        assert listView != null : "fx:id\"listView\" was not injected: check your FXML file 'ListView.fxml'.";

        setCellFactory();
        listView.setContextMenu(createContextMenu());
        listView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
           //Dispatcher.getInstance().selectTask(newValue);
           Dispatcher.getInstance().dispatch(new TaskMessage("select_task", newValue));

        });

        allBtn.setUserData(new PriorityMatcher(Priority.Alle)); // "*"
        allBtn.setOnAction(toggleHandler);
        allBtn.setToggleGroup(filtersGroup);

        hurryBtn.setUserData(new PriorityMatcher(Priority.Eilt));
        hurryBtn.setOnAction(toggleHandler);
        hurryBtn.setToggleGroup(filtersGroup);

        openBtn.setUserData(new PriorityMatcher(Priority.Offen));
        openBtn.setOnAction(toggleHandler);
        openBtn.setToggleGroup(filtersGroup);

        noHurryBtn.setUserData(new PriorityMatcher(Priority.Eilt_nicht));
        noHurryBtn.setOnAction(toggleHandler);
        noHurryBtn.setToggleGroup(filtersGroup);
    }

    @FXML
    public void showAddPlayer() {
        Dispatcher.getInstance().dispatch(new TaskMessage("add_task"));
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
        listView.setItems(appState.getViewableTasks());
    }

    @FXML
    private void handleEditTask() {
         Dispatcher.getInstance().dispatch(new TaskMessage("edit_task"));
    }

    private void resetToggles(){
        List<ButtonBase> tggles = Arrays.asList(allBtn, hurryBtn, openBtn, noHurryBtn);
        tggles.stream().forEach(btn -> btn.setId("buttonNotSelected"));
    }
    public void handleAll() {
       resetToggles();
        allBtn.setId("buttonSelected");
    }
    public void handleHurry() {
        resetToggles();
        hurryBtn.setId("buttonSelected");
    }
    public void handleOpen() {
        resetToggles();
        openBtn.setId("buttonSelected");
    }
    public void handleNoHurry() {
        resetToggles();
        noHurryBtn.setId("buttonSelected");
    }

    @FXML
    private void handleNewMenuBtn() {
        Dispatcher.getInstance().clearView();
    }

    /**
     * Opens a FileChooser to let the user select an task list to load.
     */
    @FXML
    private void handleOpenMenuBtn() {

        FileChooser fileChooser = getFileChooser();

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainController.getStage());

        if (file != null) {
            Dispatcher.getInstance().loadTaskDataFromFile(file);
        }
    }

    private FileChooser getFileChooser() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    /**
     * Saves the file to the task file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveMenuBtn() {
        File taskFile = Dispatcher.getInstance().getTaskFilePath();
        if (taskFile != null) {
            Dispatcher.getInstance().saveTaskDataToFile(taskFile);
        } else {
            handleSaveAsMenuBtn();
        }
    }

    @FXML
    private void handleSaveAsMenuBtn() {
        FileChooser fileChooser = getFileChooser();

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainController.getStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            // Dispatcher.getInstance().dispatch(new PersistenceMessage("saveToFile_persistence"));
            Dispatcher.getInstance().saveTaskDataToFile(file);
        }
    }

    @FXML
    private void handleAboutMenuBtn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TodoApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Henning Wuehn");
        alert.showAndWait();
    }

    @FXML
    private void handleExitMenuBtn() {
        System.exit(0);
    }
}