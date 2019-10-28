package de.home.todoapp.util;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Sort;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.XMLWrapper;
import de.home.todoapp.view.EditDialogController;
import de.home.todoapp.view.ListViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
