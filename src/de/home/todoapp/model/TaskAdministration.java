package de.home.todoapp.model;

import de.home.todoapp.view.IMainController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.function.Predicate;

public class TaskAdministration implements IMainController, IAppState {

    private final ObservableList<Task> tasks =
            FXCollections.observableArrayList();

    public ReadOnlyObjectProperty<ObservableList<Task>> tasksProperty() {
        return new SimpleObjectProperty<>(tasks);

    }

    private final FilteredList<Task> viewableTasks = new FilteredList<>(tasks);

    public ReadOnlyObjectProperty<ObservableList<Task>> viewableTasksProperty() {
        return new SimpleObjectProperty<>(viewableTasks);
    }

    // Wrap the FilteredList in a SortedList.
    private final SortedList<Task> sortedTasks = new SortedList<>(viewableTasks);

    public ReadOnlyObjectProperty<ObservableList<Task>> sortedTasksProperty() {
        return new SimpleObjectProperty<>(sortedTasks);
    }

    public ObjectProperty<Predicate<? super Task>> filterProperty() {
        return viewableTasks .predicateProperty();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(Task task) {
        tasks.remove(task);
    }

    public void edit(Task task) {

    }

    public void loadTestData() {

        tasks.clear();
        viewableTasks.clear();
        sortedTasks.clear();

        add(new Task("Henning", "Feature: ", "Aufgabe 1", LocalDate.of(2019, 10, 26), "EILT"));
        add(new Task("Henning", "Fix: ", "Aufgabe 2", LocalDate.of(2019, 10, 26), "EILT"));
        add(new Task("Henning", "Refactor: ", "Aufgabe 3", LocalDate.of(2019, 10, 30), "OFFEN"));
        add(new Task("Henning", "Feature: ", "Aufgabe 4", LocalDate.of(2019, 11, 10), "OFFEN"));
        add(new Task("Henning", "Fix: ", "Aufgabe 5", LocalDate.of(2019, 12, 31), "EILT_Nicht"));

    }

    public FilteredList<Task> getViewableTasks() {
        return viewableTasks;
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public boolean showEditDialog(Task task) {
        return false;
    }
}

