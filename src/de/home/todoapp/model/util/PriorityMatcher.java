package de.home.todoapp.model.util;

import de.home.todoapp.model.Task;

import java.util.function.Predicate;

public class PriorityMatcher implements Predicate<Task> {

    private Priority priority;

    public PriorityMatcher(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean test(Task task) {
        if( task == null || priority == null ) return false;
        if (priority.equals(Priority.Alle)) {  // for show all
            return true;
        }
        return priority.equals(task.getPriority());
    }

    @Override
    public String toString() {
        return "PriorityMatcher{" +
                "priority=" + priority +
                '}';
    }
}
