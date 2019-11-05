package de.home.todoapp.service;

import de.home.todoapp.model.AppDB;

public class DataMessage implements IMsg {
    public static final String REQUEST = "request";
    public static final String RESULT = "result";

    private String msgType;



    public DataMessage(String msgType) {
        this.msgType = msgType;

    }

    @Override
    public String getMsgType() {
        return msgType;
    }
}
