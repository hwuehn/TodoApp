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
    private Label daysToLabel;

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

        int number = (int) task.getDaysBetween();

        if (number >= 7)
            daysToLabel.setId("over6Days");

        daysToLabel.setText("In " + String.valueOf(task.getDaysBetween()) + " Tagen");
        inputLabel.setText(task.getInput());
        finishLabel.setText(String.valueOf(task.getFinishDate()));
    }

    public VBox getBox()
    {
        return vBox;
    }
}