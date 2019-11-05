package de.home.todoapp.service;

import de.home.todoapp.model.Task;
import de.home.todoapp.model.AppDB;
import de.home.todoapp.view.EditDialogController;
import de.home.todoapp.view.FinishedTasksController;

public class TaskService {

    public static void removeTask(AppDB appDB) {
        appDB.remove(appDB.getCurrentTask());
    }

    public static void newTask(AppDB appDB) {
        Task newTask = EditDialogController.showAddPlayer(appDB.getSorts());
        if (newTask != null) {
            appDB.getTasks().add(newTask);
        }
    }

    public static void editTask(AppDB appDB) {
        Task selectedTask = appDB.getCurrentTask();
        if (selectedTask != null) {
            Task newTask = EditDialogController.showEditDialog(selectedTask, appDB.getSorts());
            if (newTask != null) {
                setEditedTask(selectedTask, newTask, appDB);
            }
        }
    }

    private static void setEditedTask(Task oldT, Task newT, AppDB appDB) {
        int stelle = appDB.getTasks().indexOf(oldT);
        appDB.getTasks().set(stelle, newT);
        appDB.setCurrentTask(newT);
    }

    public static void selectTask(Task newValue, AppDB appDB) {
        appDB.setCurrentTask(newValue);
    }

    public static void showFinishedTasks() {
        FinishedTasksController.showFinished();
    }
}
