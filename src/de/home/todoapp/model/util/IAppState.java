package de.home.todoapp.model.util;

import de.home.todoapp.model.Task;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public interface IAppState {
    ObservableList<Task> getViewableTasks();

    ObservableValue<? extends ObservableList<Task>> viewableTasksProperty();
}
