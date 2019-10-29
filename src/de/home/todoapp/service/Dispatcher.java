package de.home.todoapp.service;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.XMLWrapper;
import de.home.todoapp.view.EditDialogController;
import javafx.scene.control.Alert;

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

    public void removeTask(Task selectedTask) {
        taskAdministration.remove(selectedTask);
    }

    public void newTask() {
        EditDialogController controller = new EditDialogController();
        controller.showAddPlayer();
    }

    public void addNewTask(Task task) {
        taskAdministration.add(task);
    }

    public void editTask(Task selectedTask) {
        taskAdministration.setCurrentTask(selectedTask);
        if (selectedTask != null) {
            EditDialogController controller = new EditDialogController();
            boolean okClicked = controller.showEditDialog(selectedTask);

            if (okClicked) {
                selectedTask.setName(selectedTask.getName());
                selectedTask.setSort(selectedTask.getSort());
                selectedTask.setInput(selectedTask.getInput());
                selectedTask.setFinishDate(selectedTask.getFinishDate());
                selectedTask.setPriority(selectedTask.getPriority());

            }
        }
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
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());
            alert.showAndWait();
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
