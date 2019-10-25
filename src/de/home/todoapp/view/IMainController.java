package de.home.todoapp.view;

import de.home.todoapp.model.Task;
import javafx.stage.Stage;

public interface IMainController {
    Stage getStage();
    boolean showEditDialog(Task task);
}
