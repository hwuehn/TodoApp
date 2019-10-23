package de.home.todoapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a task.
 *
 * @author Henning Wuehn
 */
public class EditDialogController {

    @FXML private TextField inputNameField;
    @FXML private TextArea inputTextAreaField;
    @FXML private DatePicker finishDatePicker;
    @FXML private Button okBtn;
    @FXML private Button cancelBtn;

    private Stage dialogStage;
    private Task task;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

        // Set the dialog icon.
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
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
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

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (inputNameField.getText() == null || inputNameField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }
        if (inputTextAreaField.getText() == null || inputTextAreaField.getText().length() == 0) {
            errorMessage += "No valid input!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
