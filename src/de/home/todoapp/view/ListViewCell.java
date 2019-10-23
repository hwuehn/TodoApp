package de.home.todoapp.view;

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
    }
}