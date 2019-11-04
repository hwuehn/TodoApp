package de.home.todoapp.view;

import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.util.IMainController;
import de.home.todoapp.model.util.ListViewCell;
import de.home.todoapp.service.Dispatcher;
import de.home.todoapp.service.TaskMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinishedTasksController implements Initializable {

    @FXML
    private ListView<Task> finishListView;
    // Reference to the main application.
    private IMainController mainController;

    public FinishedTasksController() {
    }

    public static void showFinished() {
        FinishedTasksController controller = new FinishedTasksController();
        showView("Show finished tasks");
    }

    public static FinishedTasksController showView(String title) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(FinishedTasksController.class.getResource("FinishedTasks.fxml"));

            Parent p = loader.load();

            Scene scene = new Scene(p);

            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(new Image("file:resources/images/finish.png"));

            FinishedTasksController controller = loader.getController();

            dialogStage.showAndWait();
            return controller;

        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public void setCellFactory() {
        finishListView.setCellFactory(
                new Callback<ListView<Task>, ListCell<Task>>() {
                    @Override
                    public ListCell<Task> call(ListView<Task> listView) {
                        return new ListViewCell();
                    }
                }
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert finishListView != null : "fx:id\"finishListView\" was not injected: check your FXML file 'FinishedTasks.fxml'.";
        setCellFactory();
        finishListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Dispatcher.getInstance().dispatch(new TaskMessage("select_task", newValue));
        });
    }

    public void setMainController(IMainController controller) {
        this.mainController = controller;
    }

    public void setAppState(TaskAdministration appState) {
        finishListView.setItems(appState.getFinished());
    }

}
