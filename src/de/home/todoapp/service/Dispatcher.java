package de.home.todoapp.service;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Administration;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.util.XMLWrapper;
import de.home.todoapp.view.EditDialogController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.prefs.Preferences;

public class Dispatcher {

    private final Administration administration;

    private Dispatcher(){
        administration = new Administration();
    }

    private void removeTask() {
        administration.remove(administration.getCurrentTask());
    }

    private void newTask() {
        Task newTask = EditDialogController.showAddPlayer();
        if (newTask != null){
            administration.getTasks().add(newTask);
        }
    }

    private void editTask() {
        Task selectedTask = administration.getCurrentTask();
        if (selectedTask != null) {
            Task newTask = EditDialogController.showEditDialog(selectedTask);
            if (newTask != null) {
                setEditedTask(selectedTask,newTask);
            }
        }
    }

    private void setEditedTask(Task oldT, Task newT) {
        int stelle = administration.getTasks().indexOf(oldT);
        administration.getTasks().set(stelle, newT);
        administration.setCurrentTask(newT);
    }

    public void filter(Predicate<Task> filter) {
        administration.sortProperty().set(Comparator.comparing(task -> task.getDaysBetween()));
        administration.filterProperty().set(filter);
    }

    public void saveTaskDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(XMLWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our task data.
            try {
                XMLWrapper wrapper = new XMLWrapper();
                wrapper.setTasks(administration.getTasks());
                // Marshalling and saving XML to the file.
                m.marshal(wrapper, file);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Save the file path to the registry.
        } catch (Exception e) {
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
        administration.getTasks().clear();
    }

    private void selectTask(Task newValue) {
        administration.setCurrentTask(newValue);
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

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

    public static Dispatcher getInstance() {
        return Holder.INSTANCE;
    }

    public Administration getAdministration() {
        return administration;
    }

    public void loadTaskDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(XMLWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            XMLWrapper wrapper = (XMLWrapper) um.unmarshal(file);

            administration.getTasks().clear();
            administration.getTasks().addAll(wrapper.getTasks());

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
            administration.setTitle("TodoApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            administration.setTitle("TodoApp");
        }
    }

    public void loadTestData() {
        administration.loadTestData();
    }
}
