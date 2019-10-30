package de.home.todoapp.service;

import java.io.File;

public class PersistMessage implements IMsg {
    public static final String SAVE = "save_project";
    public static final String LOAD = "load_project";
    //public static final String SAVEAS = "saveAs_project";
    public static final String NEW = "new_project";
    public static final String EXIT = "exit_project";

    private final String msgType;
    public final File file;

    public PersistMessage(String msgType) {
        this(msgType,null);
    }

    public PersistMessage(String msgType, File file) {
        this.msgType = msgType;
        this.file = file;
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "PersistMessage{" +
                "msgType='" + msgType + '\'' +
                ", file=" + file +
                '}';
    }
}
