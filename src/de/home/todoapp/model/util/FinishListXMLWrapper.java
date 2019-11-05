package de.home.todoapp.model.util;

import de.home.todoapp.model.Task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "tasks")
public class FinishListXMLWrapper {

    private List<Task> finished;

    //@XmlElement(name = "task")
    public List<Task> getFinished() {
        return finished;
    }

    public void setFinished(List<Task> finished) {
        this.finished = finished;
    }
}

