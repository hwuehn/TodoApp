package de.home.todoapp.service;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.home.todoapp.model.AppDB;
import de.home.todoapp.model.Task;
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

    public static void subscribe(Object o) {
        System.out.println("Dispatcher.subscribe");
        System.out.println("o = " + o);
        getInstance().eventBus.register(o);
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
                FilterService.filter(msg.filter, appDB);
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }
    @Subscribe
    public void dispatchDataMessage(DataMessage msg) {
        switch (msg.getMsgType()){

            case DataMessage.REQUEST:
                eventBus.post(appDB);
                break;

            default:
        }
    }

    @Subscribe
    public void dispatchPersistMessage(PersistMessage msg){
        switch (msg.getMsgType()) {

            case PersistMessage.LOAD_SORTS:
                PersistService.loadSorts();
                break;
            case PersistMessage.LOADED_SORTS:
//                setSorts(((PersistMessage<Sort>) msg).payload);
                setSorts(((PersistMessage<Sort>) msg).payload);
                break;
            case PersistMessage.LOAD_FINISHED:
                PersistService.loadFinished();
                break;
            case PersistMessage.LOADED_FINISHED:
                setFinished(((PersistMessage<Task>) msg).payload);
                break;
            case PersistMessage.SAVE:
                PersistService.saveTaskDataToFile(pathOrDefaultPath(msg), appDB.getTasks());
                break;
            case PersistMessage.LOAD:
                PersistService.loadTaskDataFromFile(pathOrDefaultPath(msg), appDB.getTasks());
                break;
            case PersistMessage.NEW:
                PersistService.clearView(appDB.getTasks());
                break;
            case PersistMessage.EXIT:
                PersistService.exit();
                break;

            case PersistMessage.SET_PATH:
                PersistService.setTaskFilePath(msg.file);
                break;
            case PersistMessage.LOAD_TESTDATA:
                PersistService.loadTestData(appDB);
                break;
            case PersistMessage.SAVE_SORTS:
                PersistService.saveSorts(appDB.getSorts());
                break;
            case PersistMessage.SET_TITLE:
                setTitle(msg.file);
                break;
            case PersistMessage.SAVE_FINISHED:
                PersistService.saveFinished(appDB.getFinished());
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }


    public File pathOrDefaultPath(PersistMessage msg) {
        return msg.file == null ? PersistService.getTaskFilePath() : msg.file;
    }

    @Subscribe
    public void dispatchSortMessage(SortMessage msg) {
        switch (msg.getMsgType()) {

            case SortMessage.EDIT_SORTS:
                SortService.showEditSorts(appDB);
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void dispatchTaskMessage(TaskMessage msg) {
        switch (msg.getMsgType()) {

            case TaskMessage.SELECT:
                TaskService.selectTask(msg.newTask, appDB);
                break;
            case TaskMessage.EDIT:
                TaskService.editTask(appDB);
                break;
            case TaskMessage.REMOVE:
                TaskService.removeTask(appDB);
                break;
            case TaskMessage.ADD:
                TaskService.newTask(appDB);
                break;
            case TaskMessage.FINISHED:
                TaskService.showFinishedTasks(appDB.getFinished());
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

    private void setFinished(List<Task> finished) {
        appDB.getFinished().clear();
        appDB.getFinished().setAll(finished);
    }

    private void setTitle(File file) {
        String path = file != null ? " - " + file.getName() : "";
        appDB.setTitle("TodoApp" + path);
    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

}
