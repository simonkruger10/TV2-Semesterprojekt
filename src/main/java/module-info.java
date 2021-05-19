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
    opens com.company.presentation;
    opens com.company.presentation.entity;
    opens com.company.presentation.layout;
    opens com.company.presentation.layout.parts;
}
