package de.home.todoapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private DatePicker fishDatePicker;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private boolean okClicked = false;
}
