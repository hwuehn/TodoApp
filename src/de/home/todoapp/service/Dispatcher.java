package de.home.todoapp.service;

import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.util.Sort;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Dispatcher {

    private final TaskAdministration taskAdministration;

    private Dispatcher(){
        taskAdministration = new TaskAdministration();
    }


    private void dispatchTaskMsg(IMsg msg) {

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

        switch (msg.getMsgType()){
            case FilterMessage.FILTER:
                FilterService.filter(((FilterMessage) msg).filter, getTaskAdministration());
                break;
            case SortMessage.EDIT_SORTS:
                SortService.showEditSorts(getTaskAdministration());
                break;
            case TaskMessage.SELECT:
                TaskService.selectTask(((TaskMessage) msg).newTask, getTaskAdministration());
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
            case PersistMessage.LOAD_SORTS:
                PersistenceService.loadSorts();
                break;
            case PersistMessage.LOADED_SORTS:
                setSorts(((PersistMessage<Sort>) msg).payload);
                break;
            case PersistMessage.SAVE:
                PersistenceService.saveTaskDataToFile(PersistenceService.getTaskFilePath(), getTaskAdministration().getTasks());
                break;
            case PersistMessage.LOAD:
                PersistenceService.loadTaskDataFromFile(PersistenceService.getTaskFilePath(), getTaskAdministration().getTasks());
                break;
            case PersistMessage.NEW:
                PersistenceService.clearView(getTaskAdministration().getTasks());
                break;
            case PersistMessage.EXIT:
                PersistenceService.exit();
                break;
//            case PersistMessage.GET_PATH:
//                PersistenceService.getTaskFilePath();
//                break;
            case PersistMessage.SET_PATH:
                PersistenceService.setTaskFilePath(((PersistMessage) msg).file, getTaskAdministration());
                break;
            case PersistMessage.LOAD_TESTDATA:
                PersistenceService.loadTestData(getTaskAdministration());
                break;
            case PersistMessage.SAVE_SORTS:
                PersistenceService.saveSorts(getTaskAdministration().getSorts());
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }

    }

    private void setSorts(List<Sort> sortList) {
        taskAdministration.getSorts().clear();
        taskAdministration.getSorts().setAll(sortList);

    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

}
