package de.home.todoapp.service;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class DialogService {

    public static void showLoadDialog(DialogMessage msg) {
        FileChooser fileChooser = new FileChooser();
        Stage stage=msg.getStage();
        setExtensionFilter(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD, file, null));
        }
    }

    public static void showSaveAsDialog(DialogMessage msg) {
        FileChooser fileChooser = new FileChooser();
        Stage stage=msg.getStage();
        setExtensionFilter(fileChooser);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            Dispatcher.dispatch(new PersistMessage(PersistMessage.SAVE,file,null));
        }
    }

    private static void setExtensionFilter(FileChooser fileChooser) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
    }
}
