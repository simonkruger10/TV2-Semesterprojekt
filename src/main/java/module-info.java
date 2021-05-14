module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires org.postgresql.jdbc;
    opens com.company;
    opens com.company.common;
    opens com.company.domain;
    opens com.company.domain.descriptions;
    opens com.company.gui;
    opens com.company.gui.entity;
    opens com.company.gui.layout;
    opens com.company.gui.layout.parts;
}
