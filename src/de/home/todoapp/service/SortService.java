package de.home.todoapp.service;

import de.home.todoapp.model.AppDB;
import de.home.todoapp.view.SetSortsController;

public class SortService {

    public static void showEditSorts(AppDB appDB) {
        SetSortsController.showSorts(appDB.getSorts());
    }
}
