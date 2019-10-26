package de.home.todoapp.model;

import javafx.beans.property.*;

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
    private SimpleLongProperty daysToFinish;
    private StringProperty priority;

    /**
     * Default constructor.
     */
    public Task() {
        this.name = new SimpleStringProperty();
        this.input = new SimpleStringProperty();
        this.priority = new SimpleStringProperty();
        this.today = new SimpleObjectProperty<LocalDate>();
        this.finishDate = new SimpleObjectProperty<LocalDate>();
        this.daysToFinish = new SimpleLongProperty();

    }

    public Task(String name, String input, LocalDate date, String priority) {
        this.name = new SimpleStringProperty(name);
        this.input = new SimpleStringProperty(input);
        this.priority = new SimpleStringProperty(priority);
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.finishDate = new SimpleObjectProperty<LocalDate>(date);
        this.daysToFinish = new SimpleLongProperty(getDaysBetween());
    }

    public long getDaysBetween() {
        long days = getFinishDate().getDayOfYear() - LocalDate.now().getDayOfYear();
        return days;
    }

    public String getPriority() {
        return priority.get();
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
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

    public SimpleLongProperty daysToFinishProperty() {
        return daysToFinish;
    }

    public void setDaysToFinish(long daysToFinish) {
        this.daysToFinish.set(daysToFinish);
    }
}
