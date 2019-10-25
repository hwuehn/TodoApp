package de.home.todoapp.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.util.function.Predicate;

public class TasksModel {

    private final ObservableList<Task> tasks =
            FXCollections.observableArrayList();

    public ReadOnlyObjectProperty<ObservableList<Task>> tasksProperty() {
        return new SimpleObjectProperty<>(tasks);

    }

    private final FilteredList<Task> viewableTasks = new FilteredList<>(tasks);

    public ReadOnlyObjectProperty<ObservableList<Task>> viewableTasksProperty() {

        return new SimpleObjectProperty<>(viewableTasks);
    }

    public ObjectProperty<Predicate<? super Task>> filterProperty() {
        return viewableTasks .predicateProperty();
    }

    public void add(Task p) {
        tasks.add(p);
    }

    public void remove( Task p ) {
        tasks.remove( p );
    }

    public void loadTestData() {

        tasks.clear();
        viewableTasks.clear();

        add(new Task("Henning", "Aufgabe 1", LocalDate.of(2019,10,26), "EILT"));
        add(new Task("Henning", "Aufgabe 2", LocalDate.of(2019,10,26), "EILT"));
        add(new Task("Henning", "Aufgabe 3", LocalDate.of(2019,10,30), "OFFEN"));
        add(new Task("Henning", "Aufgabe 4", LocalDate.of(2019,11,10), "OFFEN"));
        add(new Task("Henning", "Aufgabe 5", LocalDate.of(2019,12,31), "EILT_Nicht"));

    }


    public FilteredList<Task> getViewableTasks() {
        return viewableTasks;
    }
}
