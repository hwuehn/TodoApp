package de.home.todoapp.view;

import de.home.todoapp.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListViewController implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private ListView<Task> listView;


    private ObservableList<Task> observableList = FXCollections.observableArrayList();

    public void setListView() {

        mainApp.getTaskList().add(new Task("Bob Schmidt"));
        mainApp.getTaskList().add(new Task("Klaus Buzze"));
        mainApp.getTaskList().add(new Task("Tobi Fubzz"));

        observableList.setAll(mainApp.getTaskList());

        listView.setItems(observableList);

        listView.setCellFactory(
                new Callback<ListView<Task>, ListCell<Task>>() {
                    @Override
                    public ListCell<Task> call(ListView<Task> listView) {
                        return new ListViewCell();
                    }
                }
        );
    }

    // Reference to the main application.
    private MainApp mainApp;

    public ListViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert listView != null : "fx:id\"listView\" was not injected: check your FXML file 'ListView.fxml'.";

        setListView();
    }

//    public void createCellFactory() {
//        listView.setCellFactory(param -> {
//            return new ListCell<Task>() {
//                @Override
//                protected void updateItem(Task item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty || item.getName() == null || item == null) {
//                        setText(null);
//                    } else {
//                        setText(item.getName() + "\t\t\t\t\t\t\t\t\t"  + "Noch " + item.getDaysBetween() + " Tage Zeit"  +"\n" +
//                                item.getInput() +  "\t\t\t\t\t\t" + "Frist: " +item.getFinishDate());
//
//                    }
//                }
//            };
//        });
//    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}