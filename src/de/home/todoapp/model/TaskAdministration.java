package de.home.todoapp.model;

import de.home.todoapp.view.IMainController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Predicate;

public class TaskAdministration implements IMainController, IAppState {

    private final StringProperty title = new SimpleStringProperty();
    private final ObjectProperty<Task> currentTask = new SimpleObjectProperty<Task>();
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public ReadOnlyObjectProperty<ObservableList<Task>> tasksProperty() {
        return new SimpleObjectProperty<>(tasks);

    }

    private final SortedList<Task> sortedTasks = new SortedList<>(tasks);

    public ReadOnlyObjectProperty<ObservableList<Task>> sortedTasksProperty() {
        return new SimpleObjectProperty<>(sortedTasks);
    }

    public ObjectProperty<Comparator<? super Task>> sortProperty() { return sortedTasks .comparatorProperty(); }

    private final FilteredList<Task> viewableTasks = new FilteredList<>(sortedTasks);

    public ReadOnlyObjectProperty<ObservableList<Task>> viewableTasksProperty() {
        return new SimpleObjectProperty<>(viewableTasks);
    }

    public ObjectProperty<Predicate<? super Task>> filterProperty() {
        return viewableTasks .predicateProperty();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(Task task) {
        System.out.println("vor remove");
        tasks.remove(task);
        System.out.println("nach remove");
    }

    public void edit(Task task) {

    }

    //todo: auslagern in persistenz service


    public SortedList<Task> getSortedTasks() {
        return sortedTasks;
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

    public void setTitle(String s) {
        title.setValue(s);
    }

    public void loadTestData() {

        tasks.clear();
        sortedTasks.clear();
        viewableTasks.clear();

        add(new Task("Henning", Sort.Feature, "Aufgabe 1", LocalDate.of(2019, 10, 26)));
        add(new Task("Henning", Sort.Fix, "Aufgabe 2", LocalDate.of(2019, 10, 26)));
        add(new Task("Henning", Sort.Refactor, "Aufgabe 3", LocalDate.of(2019, 10, 30)));
        add(new Task("Henning", Sort.Feature, "Aufgabe 4", LocalDate.of(2019, 11, 10)));
        add(new Task("Henning", Sort.Refactor, "Aufgabe 5", LocalDate.of(2019, 12, 31)));

        sortedTasks.comparatorProperty().set(Comparator.comparing(task -> task.getDaysBetween()));
    }

    public void setCurrentTask(Task selectedTask) {
        currentTask.setValue(selectedTask);
    }
}

