module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.json;
    opens com.company.gui;
    opens com.company;
    opens com.company.common;
    opens com.company.domain;
    opens com.company.gui.entity;
    opens com.company.gui.parts;
    opens com.company.domain.descriptions;
}
