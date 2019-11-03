package de.home.todoapp.model.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "sorts")
public class SortList {

    ListProperty<Sort> sorts = new SimpleListProperty<>(FXCollections.observableArrayList());

    public ListProperty<Sort> sortsProperty() {
        return this.sorts;
    }

    @XmlElement(name = "sort")
    public ObservableList<Sort> getSorts() {
        return sorts.get();
    }

    public void setSorts(final ObservableList<Sort> sorts) {
        this.sorts.set(sorts);
    }

}