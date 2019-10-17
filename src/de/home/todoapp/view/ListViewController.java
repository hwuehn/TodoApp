package de.home.todoapp.view;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.ListViewCell;
import de.home.todoapp.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListViewController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private ListView<Task> taskData;

    // Reference to the main application.
    private MainApp mainApp;

        /**
         * The constructor.
         * The constructor is called before the initialize() method.
         */
    public ListViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the task view.

       // setListView();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        taskData.setItems(mainApp.getTaskData());
    }
    


//    public void setListView(){
//
//        taskData.setCellFactory(
//                listView -> new ListViewCell());
//    }

}//ListViewController