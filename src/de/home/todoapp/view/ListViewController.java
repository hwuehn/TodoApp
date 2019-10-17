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

    @FXML
    private ListView<Task> listView;
    @FXML private ResourceBundle resources;
    @FXML private URL location;

    private List<Task> stringList     = new ArrayList<>(10);

    /**
     * The data as an observable list of Tasks.
     */
    private ObservableList<Task> observableList = FXCollections.observableArrayList();

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
        // Initialize the task view with the two columns.
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'ListView.fxml'.";

        setListView();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Returns the data as an observable list of Tasks.
     * @return
     */
    public ObservableList<Task> getObservableList() {
        return observableList;
    }

    public void setListView(){

        // Add some sample data
        stringList.add(new Task("Hans"));
        stringList.add(new Task("Ruth"));
        stringList.add(new Task("Heinz"));
        stringList.add(new Task("Cornelia"));
        stringList.add(new Task("Werner"));
        stringList.add(new Task("Lydia"));
        stringList.add(new Task("Anna"));
        stringList.add(new Task("Stefan"));
        stringList.add(new Task("Martin"));

        observableList.setAll(stringList);

        listView.setItems(observableList);

        listView.setCellFactory(
                new Callback<ListView<Task>, ListCell<Task>>() {
                    @Override
                    public ListCell<Task> call(ListView<Task> listView) {
                        return new ListViewCell();
                    }
                });
    }

}//ListViewController