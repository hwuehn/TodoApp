package de.home.todoapp;

<<<<<<< HEAD
import de.home.todoapp.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
=======
import de.home.todoapp.view.ListViewController;
import de.home.todoapp.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
>>>>>>> secondary/master
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> secondary/master
import java.io.IOException;

public class MainApp extends Application {

<<<<<<< HEAD

=======
>>>>>>> secondary/master
    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;
<<<<<<< HEAD
    private AnchorPane rootLayout;
=======
    private BorderPane rootLayout;
>>>>>>> secondary/master

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TodoApp");

        // Set the application icon
        this.primaryStage.getIcons().add(new Image("file:resources/images/todo.png"));

        initRootLayout();

<<<<<<< HEAD
=======
        showListView();
    }

    /**
     * Constructor
     */
    public MainApp() {
>>>>>>> secondary/master

    }

    /**
     * Initializes the root layout and tries to load the last opened todoFile.
     */
    public void initRootLayout() {
<<<<<<< HEAD
        try{
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Rootlayout.fxml"));
            rootLayout = (AnchorPane) loader.load();
=======
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Rootlayout.fxml"));
            rootLayout =  loader.load();
>>>>>>> secondary/master

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
<<<<<<< HEAD

        // TODO Try to load last opened todoFile.
//        File file = getTodoFilePath();
//        if (file != null) {
//            loadTodoDataFromFile(file);
//        }



    }




}
=======
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



>>>>>>> secondary/master
