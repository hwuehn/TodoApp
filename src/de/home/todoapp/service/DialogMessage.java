package de.home.todoapp.service;

import javafx.stage.Stage;

import java.io.File;

public class DialogMessage implements IMsg {

    public final static String LOAD_DIALOG = "load_Dialog";
    public final static String SAVE_AS_DIALOG = "saveAs_Dialog";

    private final String msgType;
    private final File files;
    private Stage stage;

    public DialogMessage(String msgType, File files,Stage stage) {
        this.msgType = msgType;
        this.files = files;
        this.stage = stage;
    }

    public DialogMessage(String msgType) {
        this(msgType, null,null);
    }



    public DialogMessage(String msgType, Stage stage) {
        this(msgType,null,stage);
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

    public Stage getStage() {
        return stage;
    }
}
