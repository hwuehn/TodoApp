package de.home.todoapp.view;

import de.home.todoapp.model.Task;
import javafx.collections.ObservableList;

public interface IAppState {
    ObservableList<Task> getViewableTasks();
}
