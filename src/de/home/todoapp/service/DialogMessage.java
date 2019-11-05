package de.home.todoapp.service;

import java.io.File;

public class DialogMessage implements IMsg {

    public final static String LOAD_DIALOG = "load_Dialog";
    public final static String SAVE_DIALOG = "saveAs_Dialog";

    private final String msgType;
    // private final String filePattern;
    private final File files;

    public DialogMessage(String msgType, File files) {
        this.msgType = msgType;
        //this.filePattern = filePattern;
        this.files = files;
    }

    public DialogMessage(String msgType) {
        this(msgType, null);
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "DialogMessage{" +
                "msgType='" + msgType + '\'' +
                ", files=" + files +
                '}';
    }
}
