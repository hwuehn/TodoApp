package de.home.todoapp;

import de.home.todoapp.view.ListViewController;
import de.home.todoapp.view.RootLayoutController;
import de.home.todoapp.view.Task;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage;
    private BorderPane rootLayout;

    private List<Task> taskList = new ArrayList<>(5);

    private ObservableList<Task> observableList = FXCollections.observableArrayList();

    public ObservableList<Task> getObservableList() {
        return observableList;
    }

    /**
     * Constructor
     */
    public MainApp() {
        taskList.add(new Task("Bob Schmidt"));
        taskList.add(new Task("Klaus Buzze"));
        taskList.add(new Task("Tobi Fubzz"));

        observableList.setAll(taskList);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        this.stage.setTitle("TodoApp");

        // Set the application icon
        this.stage.getIcons().add(new Image("file:resources/images/todo.png"));

        initRootLayout();

        showListView();
    }

    /**
     * Initializes the root layout and tries to load the last opened todoFile.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Rootlayout.fxml"));
            rootLayout =  loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the task overview inside the root layout.
     */
    public void showListView() {
        try {
            // Load task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ListView.fxml"));
            AnchorPane taskOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(taskOverview);

            // Give the controller access to the main app.
            ListViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



