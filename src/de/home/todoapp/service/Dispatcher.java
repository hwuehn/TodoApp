package de.home.todoapp.service;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.home.todoapp.model.AppDB;
import de.home.todoapp.model.util.Sort;

import java.io.File;
import java.util.List;

public class Dispatcher {

    private final AppDB appDB;
    private final EventBus eventBus;

    private Dispatcher(){
        appDB = new AppDB();
        eventBus = new EventBus();
        eventBus.register(this);
    }

    @Subscribe
    public void dispatchDialogMessage(DialogMessage msg) {
        switch (msg.getMsgType()) {

            case DialogMessage.LOAD_DIALOG:
                DialogService.showLoadDialog(msg);
                break;
            case DialogMessage.SAVE_AS_DIALOG:
                DialogService.showSaveAsDialog(msg);
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }


    @Subscribe
    public void dispatchFilterMessage(FilterMessage msg) {
        switch (msg.getMsgType()){

            case FilterMessage.FILTER:
                FilterService.filter(msg.filter, getAppDB());
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void dispatchPersistMessage(PersistMessage msg){
        switch (msg.getMsgType()) {

            case PersistMessage.LOAD_SORTS:
                PersistenceService.loadSorts();
                break;
            case PersistMessage.LOADED_SORTS:
//                setSorts(((PersistMessage<Sort>) msg).payload);
                setSorts(((PersistMessage<Sort>) msg).payload);
                break;
            case PersistMessage.SAVE:
                File f= msg.file == null ? PersistenceService.getTaskFilePath(): msg.file;
                PersistenceService.saveTaskDataToFile(f, appDB.getTasks());
                break;
            case PersistMessage.LOAD:
                PersistenceService.loadTaskDataFromFile(PersistenceService.getTaskFilePath(), appDB.getTasks());
                break;
            case PersistMessage.NEW:
                PersistenceService.clearView(appDB.getTasks());
                break;
            case PersistMessage.EXIT:
                PersistenceService.exit();
                break;

            case PersistMessage.SET_PATH:
                PersistenceService.setTaskFilePath(msg.file);
                break;
            case PersistMessage.LOAD_TESTDATA:
                PersistenceService.loadTestData(appDB);
                break;
            case PersistMessage.SAVE_SORTS:
                PersistenceService.saveSorts(appDB.getSorts());
                break;
            case PersistMessage.SET_TITLE:
                setTitle(msg.file);
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void dispatchSortMessage(SortMessage msg) {
        switch (msg.getMsgType()) {

            case SortMessage.EDIT_SORTS:
                SortService.showEditSorts(getAppDB());
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void dispatchTaskMessage(TaskMessage msg) {
        switch (msg.getMsgType()) {

            case TaskMessage.SELECT:
                TaskService.selectTask(msg.newTask, getAppDB());
                break;
            case TaskMessage.EDIT:
                TaskService.editTask(getAppDB());
                break;
            case TaskMessage.REMOVE:
                TaskService.removeTask(getAppDB());
                break;
            case TaskMessage.ADD:
                TaskService.newTask(getAppDB());
                break;
            case TaskMessage.FINISHED:
                TaskService.showFinishedTasks();
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        System.out.println("!!! No subscriber for message: !!!");
        System.out.println(deadEvent.getEvent().toString());
    }

    public static Dispatcher getInstance() {
        return Holder.INSTANCE;
    }

    public AppDB getAppDB() {
        return appDB;
    }
    public static void dispatch(IMsg msg){
        getInstance().dispatch_(msg);
    }

    private void dispatch_(IMsg msg) {
        System.out.println(msg);
        eventBus.post(msg);
    }

    private void setSorts(List<Sort> sortList) {
        appDB.getSorts().clear();
        appDB.getSorts().setAll(sortList);

    }

    private void setTitle(File file) {
        if (file != null) {
            appDB.setTitle("TodoApp - " + file.getName());
        } else {
            appDB.setTitle("TodoApp");
        }
    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

}
