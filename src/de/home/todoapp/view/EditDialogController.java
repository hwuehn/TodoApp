package de.home.todoapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class EditDialogController {

    @FXML
    private Label inputLabel;
    @FXML
    private Label finishDateLabel;
    @FXML
    private TextArea inputTextAreaField;
    @FXML
    private DatePicker fishDatePicker;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private boolean okClicked = false;
}
