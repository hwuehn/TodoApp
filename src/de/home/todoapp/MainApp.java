package de.home.todoapp;

import com.google.common.eventbus.Subscribe;
import de.home.todoapp.model.AppDB;
import de.home.todoapp.model.util.IMainController;
import de.home.todoapp.service.DataMessage;
import de.home.todoapp.service.Dispatcher;
import de.home.todoapp.service.PersistMessage;
import de.home.todoapp.view.ListViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application implements IMainController {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage;
    private AnchorPane rootLayout;

    public MainApp() {
        //PersistService.CreateDummySorts();
    }

    @Override
    public void start(Stage stage)  {
        this.stage = stage;
        stage.setTitle("TodoApp");
        stage.getIcons().add(new Image("file:resources/images/todo.png"));
        Dispatcher.subscribe(this);
        initRootLayout();

        Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD));
        Dispatcher.dispatch(new PersistMessage( PersistMessage.LOAD_SORTS));
        Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD_FINISHED));
    }

    @Subscribe
    public void subscribeAppDb(AppDB appDB){
        stage.titleProperty().bind(appDB.titleProperty());
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

            Dispatcher.dispatch(new DataMessage(DataMessage.REQUEST));
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD_TESTDATA));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }
}



