package de.home.todoapp.model.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "sort")
public class Sort {

    private final StringProperty description = new SimpleStringProperty();

    public Sort() {
    }

    public Sort(String s) {
        description.setValue(s);
    }

    public final StringProperty descriptionProperty() {
        return this.description;
    }

    @XmlAttribute
    public final String getDescription() {
        return this.descriptionProperty().get();
    }

    public final void setDescription(final String description) {
        this.descriptionProperty().set(description);
    }

    @Override
    public String toString() {
        return description.getValue();
    }
}

