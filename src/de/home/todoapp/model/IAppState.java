package de.home.todoapp.model;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public interface IAppState {
    ObservableList<Task> getViewableTasks();

    ObservableValue<? extends ObservableList<Task>> viewableTasksProperty();
}
