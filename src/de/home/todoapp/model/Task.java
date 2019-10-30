package de.home.todoapp.model;

import de.home.todoapp.model.util.Priority;
import de.home.todoapp.model.util.Sort;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Task {

    private StringProperty name;
    private StringProperty input;
    private SimpleObjectProperty<LocalDate> finishDate;
    private SimpleObjectProperty<LocalDate> today;
    private SimpleLongProperty daysToFinish;
    private SimpleObjectProperty<Priority> priority;
    private SimpleObjectProperty<Sort> sort;

    public Task() {
        this.name = new SimpleStringProperty();
        this.input = new SimpleStringProperty();
        this.priority = new SimpleObjectProperty<Priority>();
        this.today = new SimpleObjectProperty<LocalDate>();
        this.finishDate = new SimpleObjectProperty<LocalDate>();
        this.daysToFinish = new SimpleLongProperty();
        this.sort = new SimpleObjectProperty<Sort>();
    }

    public Task(String name, Sort sort, String input, LocalDate date) {
        this.name = new SimpleStringProperty(name);
        this.sort = new SimpleObjectProperty<Sort>(sort);
        this.input = new SimpleStringProperty(input);
        this.priority = new SimpleObjectProperty<Priority>();
        this.today = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.finishDate = new SimpleObjectProperty<LocalDate>(date);
        this.daysToFinish = new SimpleLongProperty(getDaysBetween());
    }

    public long getDaysBetween() {
        long days = DAYS.between(LocalDate.now(), getFinishDate());
        return days;
    }

    public Sort getSort() {
        return sort.get();
    }

    public void setSort(Sort sort) {
        this.sort.set(sort);
    }

    public Priority getPriority() {
        return priority.get();
    }

    public void setPriority(Priority priority) {
        this.priority.set(priority);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getInput() {
        return input.get();
    }

    public void setInput(String input) {
        this.input.set(input);
    }

    @XmlJavaTypeAdapter(de.home.todoapp.model.util.LocalDateAdapter.class)
    public LocalDate getFinishDate() {
        return finishDate.get();
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate.set(finishDate);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name=" + name.getValue() +
                ", input=" + input.getValue() +
                ", finishDate=" + finishDate.getValue() +
                ", today=" + today.getValue() +
                ", daysToFinish=" + daysToFinish.getValue() +
                ", priority=" + priority.getValue() +
                ", sort=" + sort.getValue() +
                '}';
    }
}
