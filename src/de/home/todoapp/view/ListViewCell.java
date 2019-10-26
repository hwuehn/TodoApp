package de.home.todoapp.view;

import de.home.todoapp.model.Task;
import de.home.todoapp.view.Data;
import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<Task>
{
    @Override
    public void updateItem(Task task, boolean empty)
    {
        super.updateItem(task,empty);
        if(task != null)
        {
            Data data = new Data();
            data.setInfo(task);
            setGraphic(data.getBox());
        }
        if(task == null) {
            setGraphic(null);
        }
    }
}