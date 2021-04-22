package com.company.domain;

import com.company.common.IAccessLevel;

public enum AccessLevel implements IAccessLevel {
    GUEST("Guest", -1),
    CONSUMER("Consumer", 1),
    PRODUCER("Producer", 2),
    EMPLOYEES("Employees", 3),
    ADMINISTRATOR("System Administrator", 4);

    private final String levelString;
    private final int level;

    AccessLevel(String levelString, int level) {
        this.levelString = levelString;
        this.level = level;
    }

    public String toString() {
        return levelString;
    }

    public int getLevel() {
        return level;
    }

    public boolean equals(AccessLevel accessLevel) {
        return accessLevel.getLevel() == level;
    }

    public boolean notEquals(AccessLevel accessLevel) {
        return accessLevel.getLevel() != level;
    }

    public boolean greater(AccessLevel accessLevel) {
        return accessLevel.getLevel() < level;
    }

    public boolean less(AccessLevel accessLevel) {
        return accessLevel.getLevel() > level;
    }
}
