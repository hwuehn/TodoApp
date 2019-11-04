package de.home.todoapp.view;

import de.home.todoapp.model.Task;
import de.home.todoapp.model.util.Sort;
import de.home.todoapp.service.Dispatcher;
import de.home.todoapp.service.TaskMessage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditDialogController implements Initializable {

    private static final String SORTLIST_XML = "C:\\Users\\hensche\\Projekte\\TodoApp\\resources\\save\\sortList.xml";
    @FXML private TextField inputNameField;
    @FXML private TextArea inputTextAreaField;
    @FXML private DatePicker finishDatePicker;
    @FXML private Button okBtn;
    @FXML private Button cancelBtn;
    @FXML
    private Button editSortsBtn;

    @FXML
    private ComboBox<Sort> sortCombo = new ComboBox<>();

    private boolean okClicked = false;

    public static Task showAddPlayer(ObservableList<Sort> sorts) {
        EditDialogController controller = showView("Edit Task", null, sorts);
        return controller.getTask();
    }

    public static Task showEditDialog(Task task, ObservableList<Sort> sorts) {
        EditDialogController controller = showView("Edit Task", task,sorts);
        return controller.getTask();
    }

    public static EditDialogController showView(String title, Task task, ObservableList<Sort> sorts) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(EditDialogController.class.getResource("EditDialog.fxml"));

            Parent p = loader.load();

            Scene scene = new Scene(p);

            Stage dialogStage = new Stage();
            dialogStage.setScene( scene );
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

            EditDialogController controller = loader.getController();

            if (task != null) controller.setTask(task);
            if (sorts != null) controller.setSorts(sorts);
            dialogStage.showAndWait();
            return  controller;
        } catch(IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private void setSorts(ObservableList<Sort> sorts) {
        System.out.println("set sorts");
        System.out.println(sorts);
        sortCombo.setItems(sorts);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }



    /**
     * Sets the task to be edited in the dialog.
     *
     * @param task
     */
    public void setTask(Task task) {
        inputNameField.setText(task.getName());
        sortCombo.getSelectionModel().select(task.getSort());
        inputTextAreaField.setText(task.getInput());
        finishDatePicker.setValue(task.getFinishDate());
    }

    public Task getTask() {
        Task task = new Task();
        task.setName(inputNameField.getText());
        task.setSort(sortCombo.getSelectionModel().getSelectedItem());
        task.setInput(inputTextAreaField.getText());
        task.setFinishDate(finishDatePicker.getValue());
        return okClicked ? task : null;
    }

    @FXML
    private void handleOk(ActionEvent evt) {
        if (isInputValid()) {
            okClicked = true;
            hide(evt);
        }
    }

    @FXML
    public void handleEditSortBtn(ActionEvent evt) {

        Dispatcher.dispatch(new TaskMessage(TaskMessage.EDIT_SORTS));
    }

    @FXML
    public void cancel(ActionEvent evt) { hide(evt); }

    private void hide(ActionEvent evt) {
        ((Button)evt.getSource()).getScene().getWindow().hide();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (inputTextAreaField.getText() == null || inputTextAreaField.getText().length() == 0) {
            errorMessage += "No valid input!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
