package de.home.todoapp.service;

import de.home.todoapp.model.Task;

public class TaskMessage implements IMsg {

    public static final String SELECT = "select_task";
    public static final String EDIT = "edit_task";
    public static final String REMOVE = "remove_task";
    public static final String ADD = "add_task";

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

    public String getMsgType() {
        return msgType;
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
