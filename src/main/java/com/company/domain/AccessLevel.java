package com.company.domain;

import com.company.common.IAccessLevel;

public enum AccessLevel implements IAccessLevel {
    GUEST("Guest"),
    CONSUMER("Consumer"),
    PRODUCER("Producer"),
    ADMINISTRATOR("System Administrator"),
    EMPLOYEES("Employees");

    private final String levelString;

    AccessLevel(String levelString) {
        this.levelString = levelString;
    }

    public String toString() {
        return levelString;
    }
}
