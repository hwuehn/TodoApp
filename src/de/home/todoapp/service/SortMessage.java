package de.home.todoapp.service;

public class SortMessage implements IMsg {

    public static final String EDIT_SORTS = "editSorts_task";

    public final String msgType;
    public final String oldSort;
    public final String newSort;

    public SortMessage(String msgType, String newSort, String oldSort) {
        this.msgType = msgType;
        this.oldSort = oldSort;
        this.newSort = newSort;
    }

    public SortMessage(String msgType, String newSort) {
        this(msgType, newSort, null);
    }

    public SortMessage(String msgType) {
        this(msgType, null, null);
    }

    public String getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "SortMessage{" +
                "msgType='" + msgType + '\'' +
                ", oldSort='" + oldSort + '\'' +
                ", newSort='" + newSort + '\'' +
                '}';
    }
}
