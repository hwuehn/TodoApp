package de.home.todoapp.view;

import de.home.todoapp.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a button bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Henning Wuehn
 */

public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    @FXML
    private ToggleButton allBtn;
    @FXML
    private ToggleButton hurryBtn;
    @FXML
    private ToggleButton openBtn;
    @FXML
    private ToggleButton noHurryBtn;
    @FXML
    private MenuButton otherBtn;

    @FXML
    private MenuItem newMenuBtn;

    @FXML
    private MenuItem loadMenuBtn;

    @FXML
    private MenuItem saveMenuBtn;

    @FXML
    private MenuItem saveAsMenuBtn;

    @FXML
    private MenuItem aboutMenuBtn;

    @FXML
    private MenuItem exitMenuBtn;


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
    @FXML
    private void handleNewMenuBtn() {
        mainApp.getObservableList().clear();
        mainApp.setTaskFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an task list to load.
     */
    @FXML
    private void handleOpenMenuBtn() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getStage());

        if (file != null) {
            mainApp.loadTaskDataFromFile(file);
        }
    }

    /**
     * Saves the file to the task file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveMenuBtn() {
        File taskFile = mainApp.getTaskFilePath();
        if (taskFile != null) {
            mainApp.saveTaskDataToFile(taskFile);
        } else {
            handleSaveAsMenuBtn();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAsMenuBtn() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveTaskDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAboutMenuBtn() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("TodoApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Henning Wuehn");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExitMenuBtn() {
        System.exit(0);
    }

}
