package de.home.todoapp.view;

import de.home.todoapp.model.Priority;
import de.home.todoapp.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;


public class Data
{
    @FXML
    private GridPane GridPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label daysToFinishLabel;
    @FXML
    private Label inputLabel;
    @FXML
    private Label finishLabel;
    @FXML
    private Label sortLabel;
    @FXML
    private Label toLabel;

    public Data()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListCellItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            GridPane = fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Task task)
    {

        if (sortLabel != null) {
            //sortLabel.setId("name");
            sortLabel.setText(String.valueOf(task.getSort()));
        }

        toLabel.setText(" von ");

        nameLabel.setId("name");
        nameLabel.setText(task.getName());

        long number = task.getDaysBetween();

        if (number >= 7) {
            daysToFinishLabel.setId("over6Days");
            daysToFinishLabel.setText("In " + task.getDaysBetween() + " Tagen");
            task.setPriority(Priority.Eilt_nicht);
        }
        else if ((number > 2) & (number < 7)) {
            daysToFinishLabel.setId("over2Days");
            daysToFinishLabel.setText("In " + task.getDaysBetween() + " Tagen");
            task.setPriority(Priority.Offen);
        }
        else if ((number < 3) & (number >= 2)) {
            daysToFinishLabel.setId("under3DaysAnd");
            daysToFinishLabel.setText("In " + task.getDaysBetween() + " Tagen");
            task.setPriority(Priority.Eilt);
        } else if (number < 3) {
            daysToFinishLabel.setId("under3Days");
            task.setPriority(Priority.Eilt);
        } else {
            daysToFinishLabel.setId("underZero");
            daysToFinishLabel.setText("!!! Überfällig !!!");
            task.setPriority(Priority.Eilt);
        }

        switch ((int) task.getDaysBetween()) {
            case -1: daysToFinishLabel.setText("Gestern"); break;
            case 0: daysToFinishLabel.setText("Heute"); break;
            case 1: daysToFinishLabel.setText("Morgen"); break;
        }

        //daysToFinishLabel.setText("In " + String.valueOf(task.getDaysBetween()) + " Tagen");
        inputLabel.setText(task.getInput());
        finishLabel.setText(String.valueOf(task.getFinishDate()));

    }

    public GridPane getBox()
    {
        return GridPane;
    }
}