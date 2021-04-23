module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.json;
    opens com.company.gui to javafx.graphics, javafx.fxml;
    opens com.company;
    opens com.company.common;
}
