package de.home.todoapp.service;

import de.home.todoapp.model.Task;
import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.view.EditDialogController;
import de.home.todoapp.view.FinishedTasksController;

public class TaskService {

    public static void removeTask(TaskAdministration taskAdministration) {
        taskAdministration.remove(taskAdministration.getCurrentTask());
    }

    public static void newTask(TaskAdministration taskAdministration) {
        Task newTask = EditDialogController.showAddPlayer(taskAdministration.getSorts());
        if (newTask != null) {
            taskAdministration.getTasks().add(newTask);
        }
    }

    public static void editTask(TaskAdministration taskAdministration) {
        Task selectedTask = taskAdministration.getCurrentTask();
        if (selectedTask != null) {
            Task newTask = EditDialogController.showEditDialog(selectedTask, taskAdministration.getSorts());
            if (newTask != null) {
                setEditedTask(selectedTask, newTask, taskAdministration);
            }
        }
    }

    private static void setEditedTask(Task oldT, Task newT, TaskAdministration taskAdministration) {
        int stelle = taskAdministration.getTasks().indexOf(oldT);
        taskAdministration.getTasks().set(stelle, newT);
        taskAdministration.setCurrentTask(newT);
    }

    public static void selectTask(Task newValue, TaskAdministration taskAdministration) {
        taskAdministration.setCurrentTask(newValue);
    }

    public static void showFinishedTasks() {
        FinishedTasksController.showFinished();
    }
}
