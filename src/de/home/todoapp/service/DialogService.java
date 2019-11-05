package de.home.todoapp.service;

import de.home.todoapp.model.util.IMainController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class DialogService {

    public final static FileChooser fileChooser = new FileChooser();


    public static void showLoadDialog(DialogMessage msg) {
        Stage stage=msg.getStage();
        setExtensionFilter(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD, file, null));
        }
    }

    public static void showSaveDialog(DialogMessage msg) {
        Stage stage=msg.getStage();
        setExtensionFilter(fileChooser);
        File file = fileChooser.showSaveDialog(stage);
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
