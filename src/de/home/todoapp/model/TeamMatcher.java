package de.home.todoapp.model;

import java.util.function.Predicate;

public class TeamMatcher implements Predicate<Task> {

    private String priority;
    public TeamMatcher(String priority) {
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
