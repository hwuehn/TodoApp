package de.home.todoapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Helper class to wrap a list of tasks. This is used for saving the
 * list of tasks to XML.
 *
 * @author Henning Wuehn
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "tasks")
public class XMLWrapper {

    private List<Task> tasks;

    //@XmlElement(name = "task")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
