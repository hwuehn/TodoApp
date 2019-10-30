package de.home.todoapp.service;

import de.home.todoapp.model.Task;

public class TaskMessage implements IMsg {
    public String getMsgType() {
        return msgType;
    }

    public final String msgType;
    public final Task oldTask;
    public final Task newTask;




    public TaskMessage(String msgType, Task newTask, Task oldTask) {
        this.msgType = msgType;
        this.oldTask = oldTask;
        this.newTask = newTask;
    }
    public TaskMessage(String msgType, Task newTask) {
        this(msgType, newTask, null);
    }
    public TaskMessage(String msgType) {
        this(msgType, null,null );
    }

    @Override
    public String toString() {
        return "TaskMessage{" +
                "msgType='" + msgType + '\'' +
                "\n, oldTask=" + oldTask +
                "\n, newTask=" + newTask +
                '}';
    }
}
