package de.home.todoapp.service;

import java.io.File;

public class PersistMessage implements IMsg {
    public static final String SAVE_SORTS = "saveSorts_project";
    public static final String SAVE = "save_project";
    public static final String LOAD_SORTS = "loadSorts_project";
    public static final String LOAD = "load_project";
    //public static final String SAVEAS = "saveAs_project";
    public static final String NEW = "new_project";
    public static final String EXIT = "exit_project";
    private static final String SORTLIST_XML = "./resources/save/sortList.xml";
    public File file;
    public File sortFile = new File(SORTLIST_XML);
    private String msgType;

    public PersistMessage(String msgType) {
        this(msgType, null, null);
    }

    public PersistMessage(String msgType, File file, File sortFile) {
        this.msgType = msgType;
        this.file = file;
        this.sortFile = sortFile;
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
                ", sortFile=" + sortFile +
                '}';
    }
}
