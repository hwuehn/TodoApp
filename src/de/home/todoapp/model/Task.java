package de.home.todoapp.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

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
        this.input = new SimpleStringProperty("some commits to do");
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.finishDate = new SimpleObjectProperty<LocalDate>((LocalDate.of(2019, 10, 29)));


    }

    public long getDaysBetween() {
        long days =  ChronoUnit.DAYS.between((Temporal) input, (Temporal) finishDate);
        System.out.println(days);
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

    public void setDaysToFinish(int daysToFinish) {
        this.daysToFinish = daysToFinish;
    }
}