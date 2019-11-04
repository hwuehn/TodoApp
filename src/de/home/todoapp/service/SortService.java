package de.home.todoapp.service;

import de.home.todoapp.model.TaskAdministration;
import de.home.todoapp.view.SetSortsController;

public class SortService {

    public static void showEditSorts(TaskAdministration taskAdministration) {
        SetSortsController.showSorts(taskAdministration.getSorts());
    }
}
