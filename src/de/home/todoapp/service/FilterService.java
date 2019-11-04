package de.home.todoapp.service;

import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;

import java.util.Comparator;
import java.util.function.Predicate;

public class FilterService {

    public static void filter(Predicate<Task> filter, TaskAdministration taskAdministration) {
        taskAdministration.sortProperty().set(Comparator.comparing(task -> task.getDaysBetween()));
        taskAdministration.filterProperty().set(filter);
    }
}
