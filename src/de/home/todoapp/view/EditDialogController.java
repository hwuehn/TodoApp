package de.home.todoapp.view;

import de.home.todoapp.model.Task;
import de.home.todoapp.model.TasksModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditDialogController {

    @FXML private TextField inputNameField;
    @FXML private TextArea inputTextAreaField;
    @FXML private DatePicker finishDatePicker;
    @FXML private Button okBtn;
    @FXML private Button cancelBtn;
    @FXML private ComboBox<String> priorityCombo;

    private Stage dialogStage;
    private Task task;
    private boolean okClicked = false;
    private TasksModel model;

    public void setModel( TasksModel model ) {
        this.model = model;

        List<String> priorities =
                this.model.tasksProperty().get()
                        .stream()
                        .map( (p) -> p.getPriority() )
                        .distinct()
                        .collect(Collectors.toList());

        priorityCombo.setItems(FXCollections.observableArrayList( priorities ) );
    }

    @FXML
    private void initialize() {
    }

   public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }

    /**
     * Sets the task to be edited in the dialog.
     *
     * @param task
     */
    public void setPerson(Task task) {
        this.task = task;
        inputNameField.setText(task.getName());
        inputTextAreaField.setText(task.getInput());
        finishDatePicker.setValue(task.getFinishDate());
        priorityCombo.setValue(task.getPriority());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            task.setName(inputNameField.getText());
            task.setInput(inputTextAreaField.getText());
            task.setFinishDate(finishDatePicker.getValue());
            task.setPriority(priorityCombo.getSelectionModel().getSelectedItem());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void addPlayer(ActionEvent evt) {

        List<String> validationErrors = validate();

        if( validationErrors.isEmpty() ) {
            model.add( new Task(
                    inputNameField.getText(),
                    inputTextAreaField.getText(),
                    finishDatePicker.getValue(),
                    priorityCombo.getSelectionModel().getSelectedItem()));
            dialogStage.hide();
        }
    }

    @FXML
    public void cancel(ActionEvent evt) { hide(evt); }

    private void hide(ActionEvent evt) {
        ((Button)evt.getSource()).getScene().getWindow().hide();
    }

    private List<String> validate() {

        List<String> validationErrors = new ArrayList<>();

        if( inputTextAreaField.getText() == null ||
                inputTextAreaField.getText().isEmpty() ) {
            validationErrors.add("Player Name is required.");
        }

        if( priorityCombo.getSelectionModel().getSelectedItem() == null ) {
            validationErrors.add("Team is required.");
        }

        return validationErrors;
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";


        if (inputTextAreaField.getText() == null || inputTextAreaField.getText().length() == 0) {
            errorMessage += "No valid input!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
