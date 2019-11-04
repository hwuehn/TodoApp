package de.home.todoapp.service;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.util.SortList;
import de.home.todoapp.model.util.TaskListXMLWrapper;
import de.home.todoapp.view.EditDialogController;
import de.home.todoapp.view.FinishedTasksController;
import de.home.todoapp.view.SetSortsController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.prefs.Preferences;

public class Dispatcher {

    private static final String SORTLIST_XML = "./resources/save/sortList.xml";
    private final TaskAdministration taskAdministration;


    private Dispatcher(){

        taskAdministration = new TaskAdministration();

    }

    private void removeTask() {
        taskAdministration.remove(taskAdministration.getCurrentTask());
    }

    private void newTask() {
        Task newTask = EditDialogController.showAddPlayer();
        if (newTask != null){
            taskAdministration.getTasks().add(newTask);
        }
    }

    private void editTask() {
        Task selectedTask = taskAdministration.getCurrentTask();
        if (selectedTask != null) {
            Task newTask = EditDialogController.showEditDialog(selectedTask);
            if (newTask != null) {
                setEditedTask(selectedTask,newTask);
            }
        }
    }

    private void setEditedTask(Task oldT, Task newT) {
        int stelle = taskAdministration.getTasks().indexOf(oldT);
        taskAdministration.getTasks().set(stelle, newT);
        taskAdministration.setCurrentTask(newT);
    }

    public void filter(Predicate<Task> filter) {
        taskAdministration.sortProperty().set(Comparator.comparing(task -> task.getDaysBetween()));
        taskAdministration.filterProperty().set(filter);
    }

    public void saveTaskDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListXMLWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our task data.
            try {
                TaskListXMLWrapper wrapper = new TaskListXMLWrapper();
                wrapper.setTasks(taskAdministration.getTasks());
                // Marshalling and saving XML to the file.
                m.marshal(wrapper, file);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Save the file path to the registry.
        } catch (Exception e) {
            setTaskFilePath(file);
        }
    }

    /**
     * Returns the task file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getTaskFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        return filePath != null ? new File(filePath) : null;
    }

    public void clearView() {
        taskAdministration.getTasks().clear();
    }

    private void selectTask(Task newValue) {
        taskAdministration.setCurrentTask(newValue);
    }

    public void dispatch(IMsg msg) {
        System.out.println(msg);
        switch (msg.getMsgType()){
            case TaskMessage.SELECT:
                selectTask(((TaskMessage) msg).newTask);
                break;
            case TaskMessage.EDIT:
                editTask();
                break;
            case TaskMessage.REMOVE:
                removeTask();
                break;
            case TaskMessage.ADD:
                newTask();
                break;
            case TaskMessage.EDIT_SORTS:
                showEditSorts();
                break;
            case TaskMessage.FINISHED:
                showFinishedTasks();
                break;
            case FilterMessage.FILTER: filter(((FilterMessage) msg).filter ); break;
            case PersistMessage.SAVE: saveTaskDataToFile(getTaskFilePath());break;
            case PersistMessage.LOAD: loadTaskDataFromFile(((PersistMessage) msg).file);break;
            case PersistMessage.NEW:
                clearView();
                break;
            case PersistMessage.EXIT:
                System.exit(0);
                break;
            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    private void showEditSorts() {
        SetSortsController.showSorts();
    }

    private void showFinishedTasks() {
        FinishedTasksController.showFinished();
    }

    public void loadSorts() {
        try {
            final Unmarshaller unmarshaller = JAXBContext.newInstance(SortList.class).createUnmarshaller();

            taskAdministration.getSorts().set((SortList) unmarshaller.unmarshal(new File(SORTLIST_XML)));

        } catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

    public void saveSorts() {
        try {
            final Marshaller marshaller = JAXBContext.newInstance(SortList.class).createMarshaller();

            marshaller.marshal(taskAdministration.getSorts().get(), new File(SORTLIST_XML));
        } catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

    public static Dispatcher getInstance() {
        return Holder.INSTANCE;
    }

    public TaskAdministration getTaskAdministration() {
        return taskAdministration;
    }

    public void loadTaskDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListXMLWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            TaskListXMLWrapper wrapper = (TaskListXMLWrapper) um.unmarshal(file);

            taskAdministration.getTasks().clear();
            taskAdministration.getTasks().addAll(wrapper.getTasks());

            // Save the file path to the registry.
            setTaskFilePath(file);

        } catch (Exception e) { // catches ANY exception
        }
    }

    public void setTaskFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            taskAdministration.setTitle("TodoApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            taskAdministration.setTitle("TodoApp");
        }
    }

    public void loadTestData() {
        taskAdministration.loadTestData();
    }
}
