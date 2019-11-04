package de.home.todoapp.model;

import de.home.todoapp.model.util.IMainController;
import de.home.todoapp.model.util.Sort;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Predicate;

public class TaskAdministration implements IMainController {

    private final StringProperty title = new SimpleStringProperty();
    private final ObjectProperty<Task> currentTask = new SimpleObjectProperty<Task>();
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private final ObservableList<Task> finished = FXCollections.observableArrayList();
    private final ObservableList<Sort> sorts = FXCollections.observableArrayList();

    public ObservableList<Sort> getSorts() {
        return sorts;
    }

    public ReadOnlyObjectProperty<ObservableList<Task>> finishedProperty() {
        return new SimpleObjectProperty<>(finished);
    }

    public ObservableList<Task> getFinished() {
        return finished;
    }

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
        tasks.remove(task);
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

        add(new Task("Henning", new Sort("Refactor"), "Aufgabe 1", LocalDate.of(2019, 10, 26)));
        add(new Task("Henning", new Sort("Fix"), "Aufgabe 2", LocalDate.of(2019, 10, 26)));
        add(new Task("Henning", new Sort("Refactor"), "Aufgabe 3", LocalDate.of(2019, 10, 30)));
        add(new Task("Henning", new Sort("Feature"), "Aufgabe 4", LocalDate.of(2019, 11, 10)));
        add(new Task("Henning", new Sort("Refactor"), "Aufgabe 5", LocalDate.of(2019, 12, 31)));

        sortedTasks.comparatorProperty().set(Comparator.comparing(task -> task.getDaysBetween()));

    }

    public void setCurrentTask(Task selectedTask) {
        currentTask.setValue(selectedTask);
    }

    public Task getCurrentTask() {
        return currentTask.get();
    }

}

