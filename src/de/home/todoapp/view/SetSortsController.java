package de.home.todoapp.view;

import de.home.todoapp.model.util.IMainController;
import de.home.todoapp.model.util.Sort;
import de.home.todoapp.service.Dispatcher;
import de.home.todoapp.service.PersistMessage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetSortsController implements Initializable {

    @FXML
    private ListView<Sort> sortListView;

    @FXML
    private Button addSortBtn;

    @FXML
    private Button removeSortBtn;

    @FXML
    private Button loadSortBtn;

    @FXML
    private Button saveSortBtn;

    @FXML
    private Button okSortBtn;

    @FXML
    private Button cancelSortBtn;

    @FXML
    private TextField inputTextField;

    private boolean okClicked = false;
    // Reference to the main application.
    private IMainController mainController;

    public SetSortsController() {
    }

    public static void showSorts(ObservableList<Sort> sorts) {
        System.out.println(sorts);
        showView("Edit Sorts",sorts);
    }

    public static SetSortsController showView(String title, ObservableList<Sort> sorts) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(SetSortsController.class.getResource("SetSorts.fxml"));

            Parent p = loader.load();

            Scene scene = new Scene(p);

            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

            SetSortsController controller = loader.getController();
            controller.setSorts(sorts);
            dialogStage.showAndWait();
            return controller;

        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private void setSorts(ObservableList<Sort> sorts) {
        sortListView.setItems(sorts);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void addSort(final ActionEvent event) {
        final Sort newSort = new Sort(inputTextField.getText());
        sortListView.getItems().add(newSort);
    }

    @FXML
    public void removeSort(final ActionEvent event) {
        if (sortListView.getItems().size() > 0) {
            sortListView.getItems().remove(sortListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void loadSorts(final ActionEvent event) {
        Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD_SORTS));
//        try {
//            final Unmarshaller unmarshaller = JAXBContext.newInstance(SortList.class).createUnmarshaller();
//            sorts.set((SortList) unmarshaller.unmarshal(new File("sorts.xml")));
//        } catch (final JAXBException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    public void saveSorts(final ActionEvent event) {
        Dispatcher.dispatch(new PersistMessage(PersistMessage.SAVE_SORTS));
//        try {
//            final Marshaller marshaller = JAXBContext.newInstance(SortList.class).createMarshaller();
//            marshaller.marshal(sorts.get(), new File("sorts.xml"));
//        } catch (final JAXBException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    private void handleOk(ActionEvent evt) {
        okClicked = true;
        if (okClicked) {
            hide(evt);
        }
    }

    @FXML
    public void cancel(ActionEvent evt) {
        hide(evt);
    }

    private void hide(ActionEvent evt) {
        ((Button) evt.getSource()).getScene().getWindow().hide();
    }
}