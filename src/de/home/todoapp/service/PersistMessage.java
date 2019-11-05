package de.home.todoapp.service;

import java.io.File;
import java.util.List;

public class PersistMessage<T> implements IMsg {

    public static final String LOADED_SORTS = "loadSorts_success";
    private static final String SORTLIST_XML = "./resources/save/sortList.xml";

    public static final String SET_PATH = "setPath_project";
    public static final String SAVE_SORTS = "saveSorts_project";
    public static final String SAVE = "save_project";
    public static final String LOAD_SORTS = "loadSorts_project";
    public static final String LOAD = "load_project";
    //public static final String SAVEAS = "saveAs_project";
    public static final String NEW = "new_project";
    public static final String EXIT = "exit_project";
    public static final String LOAD_TESTDATA = "loadTestData_project";
    public static final String GET_PATH = "getPath_project";
    public static final String SET_TITLE = "setTitle_project";


    public File file;


    public List<T> payload;
    private String msgType;

    public PersistMessage(String msgType) {
        this(msgType, null, null);
    }

    public  PersistMessage(String msgType, File file, List<T> payload) {
        this.msgType = msgType;
        this.file = file;
        this.payload = payload;
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "PersistMessage{" +
                "file=" + file +
                ", payload=" + payload +
                ", msgType='" + msgType + '\'' +
                '}';
    }
}
