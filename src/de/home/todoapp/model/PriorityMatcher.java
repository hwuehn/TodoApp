package de.home.todoapp.model;

import java.util.function.Predicate;

public class PriorityMatcher implements Predicate<Task> {

    private Priority priority;

    public PriorityMatcher(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean test(Task task) {
        if( task == null || priority == null ) return false;
        if( priority.equals("*") ) {  // for show all
            return true;
        }
        return priority.equals(task.getPriority());
    }
}
