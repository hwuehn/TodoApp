package de.home.todoapp.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Model class for a Task.
 *
 * @author Henning Wuehn
 */
public class Task {

    private final StringProperty name;
    private final StringProperty input;
    private final ObjectProperty<LocalDate> finishDate;
    private final ObjectProperty<LocalDate> today;
    private final ObjectProperty<LocalDate> daysToFinish;
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
        this.finishDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2019,10,29)) ;
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.daysToFinish = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        //this.daysToFinish = LocalDate.from((TemporalAccessor) finishDate).minus((TemporalAmount) today);
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

    public ObjectProperty<LocalDate> finishDateProperty() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate.set(finishDate);
    }

    public LocalDate getToday() {
        return today.get();
    }

    public ObjectProperty<LocalDate> todayProperty() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today.set(today);
    }

    public ObjectProperty<LocalDate> getDaysToFinish() {
        return daysToFinish;
    }

    @Override
    public String toString() {
        return name + "\n" +
               input + "\t" + finishDate + "\n" +
               daysToFinish;
    }

//    @Override
//    public String toString() {
//        return "Task{" +
//                "name=" + name +
//                ", input=" + input +
//                ", finishDate=" + finishDate +
//                ", today=" + today +
//                ", daysToFinish=" + daysToFinish +
//                '}';
//    }
}
