package de.home.todoapp.model;

import java.util.function.Predicate;

public class PriorityMatcher implements Predicate<Task> {

    private String priority;
    public PriorityMatcher(String priority) {
        this.priority = priority;
    }

    @Override
    public boolean test(Task task) {
        if( task == null || priority == null ) return false;
        if( priority.equals("*") ) {  // for show all
            return true;
        }
        return priority.equalsIgnoreCase(task.getPriority());
    }
}
