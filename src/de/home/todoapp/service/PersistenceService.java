package de.home.todoapp.service;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.model.util.Sort;
import de.home.todoapp.model.util.SortListXMLWrapper;
import de.home.todoapp.model.util.TaskListXMLWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;

public class PersistenceService {

    private static final String SORTLIST_XML = "./resources/save/sortList.xml";
    private final TaskAdministration taskAdministration;

    public PersistenceService(TaskAdministration taskAdministration) {
        this.taskAdministration = taskAdministration;
    }

    public static void saveTaskDataToFile(File file, List<Task> tasks) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListXMLWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our task data.
            try {
                TaskListXMLWrapper wrapper = new TaskListXMLWrapper();
                wrapper.setTasks(tasks);
                // Marshalling and saving XML to the file.
                m.marshal(wrapper, file);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Save the file path to the registry.
        } catch (Exception e) {
            setTaskFilePath(file, new TaskAdministration());
        }
    }

    public static void loadTaskDataFromFile(File file, List<Task> tasks) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListXMLWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            TaskListXMLWrapper wrapper = (TaskListXMLWrapper) um.unmarshal(file);

            tasks.clear();
            tasks.addAll(wrapper.getTasks());

            // Save the file path to the registry.
            setTaskFilePath(file, new TaskAdministration());

        } catch (Exception e) { // catches ANY exception
        }
    }

    public static void clearView(List<Task> tasks) {
        tasks.clear();
    }

    public static void exit() {
        System.exit(0);
    }

    public static void loadSorts(List<Sort> sorts) {

        try {
            final Unmarshaller unmarshaller = JAXBContext.newInstance(SortListXMLWrapper.class).createUnmarshaller();

            SortListXMLWrapper sortListXMLWrapper = (SortListXMLWrapper) unmarshaller.unmarshal(new File(SORTLIST_XML));
            sorts.clear();
            //taskAdministration.getSorts().removeAll();
            sorts.addAll(sortListXMLWrapper.getSorts());
            //taskAdministration.getSorts().setAll(sortListXMLWrapper.getSorts());

        } catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void setTaskFilePath(File file, TaskAdministration taskAdministration) {
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

    public static void loadTestData(TaskAdministration taskAdministration) {
        taskAdministration.loadTestData();
    }

    public static void saveSorts(List<Sort> sorts) {
        try {
            final Marshaller marshaller = JAXBContext.newInstance(SortListXMLWrapper.class).createMarshaller();
            SortListXMLWrapper sortListXMLWrapper = new SortListXMLWrapper();
            sortListXMLWrapper.setSorts(sorts);
            marshaller.marshal(sortListXMLWrapper, new File(SORTLIST_XML));

        } catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the task file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public static File getTaskFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        return filePath != null ? new File(filePath) : null;
    }


}
