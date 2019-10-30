package de.home.todoapp.service;

import de.home.todoapp.model.Task;

import java.util.function.Predicate;

public class FilterMessage implements IMsg {
    public final static String FILTER = "filter_task";

    private final String msgType;
    public final Predicate<Task> filter;

    public FilterMessage(String msgType, Predicate<Task> filter) {
        this.msgType = msgType;
        this.filter = filter;
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "FilterMessage{" +
                "msgType='" + msgType + '\'' +
                ", filter=" + filter +
                '}';
    }
}
