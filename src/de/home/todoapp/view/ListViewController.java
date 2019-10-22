package de.home.todoapp.view;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ListViewController implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    private ObservableList<Task> tasks = FXCollections.observableArrayList(
            new Task("Hans"),
            new Task("Heinz"),
            new Task("Ruth" ),
            new Task("Cornelia" ),
            new Task("Werner" ),
            new Task("Lydia" ),
            new Task("Anna" ),
            new Task("Stefan" ),
            new Task("Martin" ));

    @FXML private ListView<Task> listView = new ListView<>(tasks);

    @FXML
    Pane pane;

    // Reference to the main application.
    private MainApp mainApp;

    public ListViewController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createCellFactory();
        listView.setItems(tasks);
    }

    public void createCellFactory() {
        listView.setCellFactory(param -> {
            return new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item.getName() == null || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName() + " \n" +
                                item.getInput() + "\t"  +  "\n"
                                + "\t" +item.getFinishDate());

                    }
                }
            };
        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}