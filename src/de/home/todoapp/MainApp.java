package de.home.todoapp;

import de.home.todoapp.util.Dispatcher;
import de.home.todoapp.view.IMainController;
import de.home.todoapp.view.ListViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application implements IMainController {

    //private TaskAdministration taskAdministration;

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage;
    private AnchorPane rootLayout;

    public MainApp() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("TodoApp");
        this.stage.getIcons().add(new Image("file:resources/images/todo.png"));
        //taskAdministration = new TaskAdministration();
        initRootLayout();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ListView.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);

            // Give the controller access to the main app.
            ListViewController controller = loader.getController();
            controller.setMainController(this);
            controller.setAppState(Dispatcher.getInstance().getTaskAdministration());
            Dispatcher.getInstance().loadTestData();

            System.out.println("set main controller");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }
}



