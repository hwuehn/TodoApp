package de.home.todoapp.service;

import de.home.todoapp.model.Task;
import de.home.todoapp.model.AppDB;

import java.util.Comparator;
import java.util.function.Predicate;

public class FilterService {

    public static void filter(Predicate<Task> filter, AppDB appDB) {
        appDB.sortProperty().set(Comparator.comparing(task -> task.getDaysBetween()));
        appDB.filterProperty().set(filter);
    }
}
