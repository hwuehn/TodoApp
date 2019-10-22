package de.home.todoapp;

import de.home.todoapp.model.Task;
import de.home.todoapp.view.ListViewController;
import de.home.todoapp.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Tasks.
     */
    //private ObservableList<Task> taskData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
//        taskData.add(new Task("Hans"));
//        taskData.add(new Task("Ruth"));
//        taskData.add(new Task("Heinz"));
//        taskData.add(new Task("Cornelia"));
//        taskData.add(new Task("Werner"));
//        taskData.add(new Task("Lydia"));
//        taskData.add(new Task("Anna"));
//        taskData.add(new Task("Stefan"));
//        taskData.add(new Task("Martin"));
    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
//    public ObservableList<Task> getTaskData() {
//        return taskData;
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TodoApp");

        // Set the application icon
        this.primaryStage.getIcons().add(new Image("file:resources/images/todo.png"));

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
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

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



