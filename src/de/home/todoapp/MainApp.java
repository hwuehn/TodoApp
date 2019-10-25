package de.home.todoapp;

import de.home.todoapp.model.TaskListWrapper;
import de.home.todoapp.view.EditDialogController;
import de.home.todoapp.view.ListViewController;
import de.home.todoapp.model.Task;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage;
    private AnchorPane rootLayout;

    private ObservableList<Task> observableList = FXCollections.observableArrayList();

    public ObservableList<Task> getObservableList() {
        return observableList;
    }

    /**
     * Constructor
     */
    public MainApp() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        this.stage.setTitle("TodoApp");

        // Set the application icon
        this.stage.getIcons().add(new Image("file:resources/images/todo.png"));

        initRootLayout();
    }

    /**
     * Initializes the root layout and tries to load the last opened todoFile.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ListView.fxml"));
            rootLayout =  loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);

            // Give the controller access to the main app.
            ListViewController controller = loader.getController();
            controller.setMainApp(this);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getTaskFilePath();
        if (file != null) {
            loadTaskDataFromFile(file);
        }
    }

    /**
     * Opens a dialog to edit details for the specified task. If the user
     * clicks OK, the changes are saved into the provided task object and true
     * is returned.
     *
     * @param task object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showEditDialog(Task task) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the task into the controller.
            EditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(task);

            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getStage() {
        return stage;
    }

     /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setTaskFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            stage.setTitle("TodoApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            stage.setTitle("TodoApp");
        }
    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public void loadTaskDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            TaskListWrapper wrapper = (TaskListWrapper) um.unmarshal(file);

            observableList.clear();
            observableList.addAll(wrapper.getTasks());

            // Save the file path to the registry.
            setTaskFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

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
                    .newInstance(TaskListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our task data.
            TaskListWrapper wrapper = new TaskListWrapper();
            wrapper.setTasks(observableList);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setTaskFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}



