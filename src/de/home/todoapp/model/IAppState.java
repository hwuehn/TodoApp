package de.home.todoapp.model;

import javafx.collections.ObservableList;

public interface IAppState {
    ObservableList<Task> getViewableTasks();
}
