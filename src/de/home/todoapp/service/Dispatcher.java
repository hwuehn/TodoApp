package de.home.todoapp.service;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.XMLWrapper;
import de.home.todoapp.view.EditDialogController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.prefs.Preferences;

public class Dispatcher {

    private final TaskAdministration taskAdministration;

    private Dispatcher(){
        taskAdministration = new TaskAdministration();
    }

    public void removeTask() {

        taskAdministration.remove(taskAdministration.getCurrentTask());
    }

    public void newTask() {

        Task newTask = EditDialogController.showAddPlayer();
        if (newTask != null){
            taskAdministration.getTasks().add(newTask);
        }
    }

    public void editTask() {
        Task selectedTask = taskAdministration.getCurrentTask();
        if (selectedTask != null) {
            Task newTask = EditDialogController.showEditDialog(selectedTask);
            if (newTask != null) {
                setEditedTask(selectedTask,newTask);
            }
        }
    }

    public void setEditedTask(Task oldT,Task newT) {

        int stelle= taskAdministration.getTasks().indexOf(oldT);
        taskAdministration.getTasks().set(stelle,newT);
        taskAdministration.setCurrentTask(newT);
    }

    public void filter(Predicate<Task> filter) {
        taskAdministration.sortProperty().set(Comparator.comparing(task -> task.getDaysBetween()));
        taskAdministration.filterProperty().set( filter );
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
                wrapper.setTasks(taskAdministration.getTasks());
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
        taskAdministration.getTasks().clear();
    }

    public void selectTask(Task newValue) {
        taskAdministration.setCurrentTask(newValue);
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
                    .newInstance(XMLWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            XMLWrapper wrapper = (XMLWrapper) um.unmarshal(file);

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
