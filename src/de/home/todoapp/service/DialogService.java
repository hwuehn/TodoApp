package de.home.todoapp.service;

import de.home.todoapp.model.util.IMainController;
import javafx.stage.FileChooser;

import java.io.File;

public class DialogService {

    public final static FileChooser fileChooser = new FileChooser();

    // Reference to the main application.
    public static IMainController mainController;

    public static void showLoadDialog() {
        setExtensionFilter(fileChooser);
        File file = fileChooser.showOpenDialog(mainController.getStage());
        if (file != null) {
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD, file, null));
        }
    }

    public static void showSaveDialog() {
        setExtensionFilter(fileChooser);
        File file = fileChooser.showSaveDialog(mainController.getStage());
        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            Dispatcher.dispatch(new PersistMessage(PersistMessage.SAVE));
        }
    }

    private static void setExtensionFilter(FileChooser fileChooser) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
    }
}
