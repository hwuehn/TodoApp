package de.home.todoapp.service;

import de.home.todoapp.model.TaskAdministration;

import java.io.File;

public class Dispatcher {

    private final TaskAdministration taskAdministration;

    private Dispatcher(){
        taskAdministration = new TaskAdministration();
    }

    public static Dispatcher getInstance() {
        return Holder.INSTANCE;
    }

    public TaskAdministration getTaskAdministration() {
        return taskAdministration;
    }

    public File dispatch(IMsg msg) {
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
                PersistenceService.loadSorts(getTaskAdministration().getSorts());
                break;
            case PersistMessage.SAVE:
                PersistenceService.saveTaskDataToFile(PersistenceService.getTaskFilePath(), getTaskAdministration().getTasks());
                break;
            case PersistMessage.LOAD:
                PersistenceService.loadTaskDataFromFile(((PersistMessage) msg).file, getTaskAdministration().getTasks());
                break;
            case PersistMessage.NEW:
                PersistenceService.clearView(getTaskAdministration().getTasks());
                break;
            case PersistMessage.EXIT:
                PersistenceService.exit();
                break;
            case PersistMessage.GET_PATH:
                PersistenceService.getTaskFilePath();
                break;
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
        return null;
    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

}
