package de.home.todoapp.service;

import de.home.todoapp.MainApp;
import de.home.todoapp.model.AppDB;
import de.home.todoapp.model.Task;
import de.home.todoapp.model.util.FinishListXMLWrapper;
import de.home.todoapp.model.util.Sort;
import de.home.todoapp.model.util.SortListXMLWrapper;
import de.home.todoapp.model.util.TaskListXMLWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class PersistService {

    private static final String SORTLIST_XML = "./resources/save/sortList.xml";
    private static final String FINISHLIST_XML = "./resources/save/finishList.xml";

    public PersistService() {

    }

    public static void saveTaskDataToFile(File file, List<Task> tasks) {
        if (file == null) return;
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
            Dispatcher.dispatch(new PersistMessage(PersistMessage.SET_PATH,file,null));
            Dispatcher.dispatch(new PersistMessage(PersistMessage.SAVE_FINISHED));

        } catch (Exception e) {
        }
    }

    public static void loadTaskDataFromFile(File file, List<Task> tasks) {
        if (file == null) return;
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListXMLWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            TaskListXMLWrapper wrapper = (TaskListXMLWrapper) um.unmarshal(file);

            tasks.clear();
            tasks.addAll(wrapper.getTasks());

            Dispatcher.dispatch(new PersistMessage(PersistMessage.SET_PATH,file,null));

        } catch (Exception e) { // catches ANY exception
        }
    }

    public static void clearView(List<Task> tasks) {
        tasks.clear();
    }

    public static void exit() {
        System.exit(0);
    }

    public static void loadSorts() {

        try {
            final Unmarshaller unmarshaller = JAXBContext.newInstance(SortListXMLWrapper.class).createUnmarshaller();

            SortListXMLWrapper sortListXMLWrapper = (SortListXMLWrapper) unmarshaller.unmarshal(new File(SORTLIST_XML));
            List<Sort> sorts = sortListXMLWrapper.getSorts();
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOADED_SORTS,null, sorts));

        } catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void setTaskFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");
        }
        Dispatcher.dispatch(new PersistMessage(PersistMessage.SET_TITLE,file,null));

    }

    public static void loadTestData(AppDB appDB) {
        appDB.loadTestData();
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


    public static void CreateDummySorts() {
        List<String> sortnames = Arrays.asList("Feature", "Refactor", "Privat", "Fix");
        List<Sort> dummysorts = sortnames.stream().map(Sort::new).collect(Collectors.toList());
        saveSorts(dummysorts);
    }

    public static void saveFinished(List<Task> finished) {
        try {
            final Marshaller marshaller = JAXBContext.newInstance(FinishListXMLWrapper.class).createMarshaller();
            FinishListXMLWrapper finishListXMLWrapper = new FinishListXMLWrapper();
            finishListXMLWrapper.setFinished(finished);
            marshaller.marshal(finishListXMLWrapper, new File(FINISHLIST_XML));

        } catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void loadFinished() {
        if (FINISHLIST_XML == null) return;
        try {
            final Unmarshaller unmarshaller = JAXBContext.newInstance(FinishListXMLWrapper.class).createUnmarshaller();
            FinishListXMLWrapper finishListXMLWrapper = (FinishListXMLWrapper) unmarshaller.unmarshal(new File(FINISHLIST_XML));
            List<Task> finished = finishListXMLWrapper.getFinished();
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOADED_FINISHED, null, finished));

        } catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

}
