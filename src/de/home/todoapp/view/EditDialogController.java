package de.home.todoapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditDialogController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label todoLabel;
    @FXML
    private Label finishDateLabel;
    @FXML
    private TextField inputtextField;
    @FXML
    private TextArea inputTextAreaField;
    @FXML
    private DatePicker finishDatePicker;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Task task;

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

        inputtextField.setText(task.getName());
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
            task.setName(inputtextField.getText());
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

        if (inputtextField.getText() == null || inputtextField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }
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
