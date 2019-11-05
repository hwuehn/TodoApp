package de.home.todoapp.service;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.util.Sort;

import java.util.List;

public class Dispatcher {

    private final TaskAdministration taskAdministration;
    private final EventBus eventBus;

    private Dispatcher(){
        taskAdministration = new TaskAdministration();
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
                FilterService.filter(msg.filter, getTaskAdministration());
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
                PersistenceService.saveTaskDataToFile(PersistenceService.getTaskFilePath(), taskAdministration.getTasks());
                break;
            case PersistMessage.LOAD:
                PersistenceService.loadTaskDataFromFile(PersistenceService.getTaskFilePath(), taskAdministration.getTasks());
                break;
            case PersistMessage.NEW:
                PersistenceService.clearView(taskAdministration.getTasks());
                break;
            case PersistMessage.EXIT:
                PersistenceService.exit();
                break;

            case PersistMessage.SET_PATH:
                PersistenceService.setTaskFilePath(msg.file, taskAdministration);
                break;
            case PersistMessage.LOAD_TESTDATA:
                PersistenceService.loadTestData(taskAdministration);
                break;
            case PersistMessage.SAVE_SORTS:
                PersistenceService.saveSorts(taskAdministration.getSorts());
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void dispatchSortMessage(SortMessage msg) {
        switch (msg.getMsgType()) {

            case SortMessage.EDIT_SORTS:
                SortService.showEditSorts(getTaskAdministration());
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void dispatchTaskMessage(TaskMessage msg) {
        switch (msg.getMsgType()) {

            case TaskMessage.SELECT:
                TaskService.selectTask(msg.newTask, getTaskAdministration());
                break;
            case TaskMessage.EDIT:
                TaskService.editTask(getTaskAdministration());
                break;
            case TaskMessage.REMOVE:
                TaskService.removeTask(getTaskAdministration());
                break;
            case TaskMessage.ADD:
                TaskService.newTask(getTaskAdministration());
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

    public TaskAdministration getTaskAdministration() {
        return taskAdministration;
    }
    public static void dispatch(IMsg msg){
        getInstance().dispatch_(msg);
    }

    private void dispatch_(IMsg msg) {
        System.out.println(msg);
        eventBus.post(msg);
    }

    private void setSorts(List<Sort> sortList) {
        taskAdministration.getSorts().clear();
        taskAdministration.getSorts().setAll(sortList);

    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

}
