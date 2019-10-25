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

    public Task(String name, String input, LocalDate date, Priority priority) {
        this.name = new SimpleStringProperty(name);
        this.input = new SimpleStringProperty(input);
        this.priority = priority;
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.finishDate = new SimpleObjectProperty<LocalDate>(date);


    }

    public enum Priority { ALLE, EILT, OFFEN, EILT_Nicht }

    public long getDaysBetween() {
        long days = getFinishDate().getDayOfYear() - LocalDate.now().getDayOfYear();
        return days;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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
        return daysToFinish;
    }

    public void setDaysToFinish(long daysToFinish) {
        this.daysToFinish = daysToFinish;
    }
}
