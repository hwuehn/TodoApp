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

}
