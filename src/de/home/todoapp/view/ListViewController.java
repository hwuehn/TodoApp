package de.home.todoapp.view;

import com.google.common.eventbus.Subscribe;
import de.home.todoapp.model.AppDB;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.util.IMainController;
import de.home.todoapp.model.util.ListViewCell;
import de.home.todoapp.model.util.Priority;
import de.home.todoapp.model.util.PriorityMatcher;
import de.home.todoapp.service.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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

    @FXML
    private MenuItem editSortMenuBtn;

    @FXML
    private ToggleButton showFinishedTasksBtn;

    @FXML
    private ToggleButton signInBtn;

    @FXML
    private ToggleButton printBtn;

    @FXML
    private ToggleButton mailBtn;

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

    private IMainController mainController;

    public ListViewController() {
    }

    private EventHandler
            <ActionEvent> toggleHandler = event -> {
        ToggleButton tb = (ToggleButton) event.getSource();
        Predicate<Task> filter = (Predicate<Task>) tb.getUserData();
        Dispatcher.dispatch(new FilterMessage(FilterMessage.FILTER,filter));
    };

    private ContextMenu createContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem finish = new MenuItem("Finish");
        MenuItem edit = new MenuItem("Edit");

        finish.setOnAction((evt) -> Dispatcher.dispatch(new TaskMessage("remove_task")));
        edit.setOnAction((evt) -> handleEditTask());
        menu.getItems().add(finish);
        menu.getItems().add(edit);
        return menu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Dispatcher.subscribe(this);
        assert listView != null : "fx:id\"listView\" was not injected: check your FXML file 'ListView.fxml'.";

        setCellFactory();
        listView.setContextMenu(createContextMenu());
        listView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
           Dispatcher.dispatch(new TaskMessage("select_task", newValue));
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
        Dispatcher.dispatch(new TaskMessage("add_task"));
    }

    @Subscribe
    public void subscribeAppDb(AppDB appDB){
        setAppState(appDB);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param controller
     */
    public void setMainController(IMainController controller) {
        this.mainController = controller;
    }

    public void setAppState(AppDB appState) {
        listView.setItems(appState.getViewableTasks());
    }

    @FXML
    private void handleEditTask() {
         Dispatcher.dispatch(new TaskMessage("edit_task"));
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
        Dispatcher.dispatch(new PersistMessage("new_project"));
    }

    @FXML
    private void handleOpenMenuBtn() {
        Dispatcher.dispatch(new DialogMessage(DialogMessage.LOAD_DIALOG,mainController.getStage()));
    }

    @FXML
    private void handleSaveMenuBtn() {
            Dispatcher.dispatch(new PersistMessage(PersistMessage.SAVE));
    }

    @FXML
    private void handleSaveAsMenuBtn() {
        Dispatcher.dispatch(new DialogMessage(DialogMessage.SAVE_AS_DIALOG, mainController.getStage()));
    }

    @FXML
    private void handleEditSortMenuBtn() {
        Dispatcher.dispatch(new SortMessage(SortMessage.EDIT_SORTS));
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
        Dispatcher.dispatch(new PersistMessage("exit_project"));
    }

    public void handleSignInMenuBtn(ActionEvent actionEvent) {
    }

    public void handleShowFinishedMenuBtn(ActionEvent actionEvent) {
        Dispatcher.dispatch(new TaskMessage(TaskMessage.FINISHED));
    }

    public void handlePrintMenuBtn(ActionEvent actionEvent) {
    }

    public void handleSyncMenuBtn(ActionEvent actionEvent) {
    }
}