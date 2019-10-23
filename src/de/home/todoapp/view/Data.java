package de.home.todoapp.view;

import de.home.todoapp.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class Data
{
    @FXML
    private VBox vBox;

    @FXML
    private HBox hBoxTop;

    @FXML
    private Label nameLabel;

    @FXML
    private Label daysToFinishLabel;

    @FXML
    private HBox hBoxBottom;

    @FXML
    private Label inputLabel;

    @FXML
    private Label finishLabel;


    public Data()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListCellItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            vBox = fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Task task)
    {
        nameLabel.setText(task.getName());

        long number = task.getDaysBetween();

        if (number >= 7) {
            daysToFinishLabel.setId("over6Days");
        }
        else if ((number > 2) & (number < 7)) {
            daysToFinishLabel.setId("over2Days");
        }
        else if (number < 3) {
            daysToFinishLabel.setId("under3Days");
        }

        switch ((int) task.getDaysBetween()) {
            case -1: daysToFinishLabel.setText("Gestern"); break;
            case 0: daysToFinishLabel.setText("Heute"); break;
            case 1: daysToFinishLabel.setText("Morgen"); break;
        }

        daysToFinishLabel.setText("In " + String.valueOf(task.getDaysBetween()) + " Tagen");
        inputLabel.setText(task.getInput());
        finishLabel.setText(String.valueOf(task.getFinishDate()));

    }

    public VBox getBox()
    {
        return vBox;
    }
}