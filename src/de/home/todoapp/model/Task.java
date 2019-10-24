package de.home.todoapp.model;

import de.home.todoapp.util.DateUtil;
import javafx.beans.property.*;

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
    private long daysToFinish = 0;
    private Priority priority;

    /**
     * Default constructor.
     */
    public Task() {
        this(null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param name
     */
    public Task(String name) {
        this.name = new SimpleStringProperty(name);


        // Some initial dummy data, just for convenient testing.
        this.input = new SimpleStringProperty("some commits to do and something more. a lot stuff to fix");
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.finishDate = new SimpleObjectProperty<LocalDate>((LocalDate.of(2019, 10, 29)));


    }

    public Task (String name, String input, LocalDate date) {
        this.name = new SimpleStringProperty(name);
        this.input = new SimpleStringProperty(input);
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.finishDate = new SimpleObjectProperty<LocalDate>(date);
    }

    public long getDaysBetween() {
        long days = getFinishDate().getDayOfYear() - LocalDate.now().getDayOfYear();
        return days;
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

    public LocalDate getFinishDate() {
        return finishDate.get();
    }

    public SimpleObjectProperty<LocalDate> finishDateProperty() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate.set(finishDate);
    }

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
        return daysToFinish;
    }

    public void setDaysToFinish(long daysToFinish) {
        this.daysToFinish = daysToFinish;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}