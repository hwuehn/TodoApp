package de.home.todoapp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Model class for a Task.
 *
 * @author Henning Wuehn
 */
public class Task {

    private StringProperty name;
    private StringProperty input;
    private SimpleObjectProperty<LocalDate> finishDate;
    private SimpleObjectProperty<LocalDate> today;
    private SimpleIntegerProperty daysToFinish;
    private SimpleObjectProperty<Priority> priority;
    private SimpleObjectProperty<Sort> sort;

    /**
     * Default constructor.
     */
    public Task() {
        this.name = new SimpleStringProperty();
        this.input = new SimpleStringProperty();
        this.priority = new SimpleObjectProperty<Priority>();
        this.today = new SimpleObjectProperty<LocalDate>();
        this.finishDate = new SimpleObjectProperty<LocalDate>();
        this.daysToFinish = new SimpleIntegerProperty();
        this.sort = new SimpleObjectProperty<Sort>();

    }

    public Task(String name, Sort sort, String input, LocalDate date) {
        this.name = new SimpleStringProperty(name);
        this.sort = new SimpleObjectProperty<Sort>(sort);
        this.input = new SimpleStringProperty(input);
        this.priority = new SimpleObjectProperty<Priority>();
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.finishDate = new SimpleObjectProperty<LocalDate>(date);
        this.daysToFinish = new SimpleIntegerProperty(getDaysBetween());
    }

    public int getDaysBetween() {
        int days = getFinishDate().getDayOfYear() - LocalDate.now().getDayOfYear();
        return days;
    }

    public Sort getSort() {
        return sort.get();
    }

    public void setSort(Sort sort) {
        this.sort.set(sort);
    }

    public SimpleObjectProperty<Sort> sortProperty() {
        return sort;
    }

    public Priority getPriority() {
        return priority.get();
    }

    public SimpleObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority.set(priority);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getInput() {
        return input.get();
    }

    public StringProperty inputProperty() {
        return input;
    }

    public void setInput(String input) {
        this.input.set(input);
    }

    @XmlJavaTypeAdapter(de.home.todoapp.util.LocalDateAdapter.class)
    public LocalDate getFinishDate() {
        return finishDate.get();
    }

    public SimpleObjectProperty<LocalDate> finishDateProperty() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate.set(finishDate);
    }

    @XmlJavaTypeAdapter(de.home.todoapp.util.LocalDateAdapter.class)
    public LocalDate getToday() {
        return today.get();
    }

    public SimpleObjectProperty<LocalDate> todayProperty() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today.set(today);
    }

    public long getDaysToFinish() {
        return daysToFinish.get();
    }

    public SimpleIntegerProperty daysToFinishProperty() {
        return daysToFinish;
    }

    public void setDaysToFinish(int daysToFinish) {
        this.daysToFinish.set(daysToFinish);
    }
}
