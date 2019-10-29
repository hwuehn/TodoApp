package de.home.todoapp.service;

import de.home.todoapp.view.EditDialogController;

public class NewTaskService {

    public void newTask() {
        EditDialogController controller = new EditDialogController();
        controller.showAddPlayer();
    }
}
