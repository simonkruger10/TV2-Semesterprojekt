package com.company.common;

public enum AccessLevel {
    // TODO: Find out where AccessLevel should be located
    GUEST("Guest", -1),
    CONSUMER("Consumer", 1),
    PRODUCER("Producer", 2),
    EMPLOYEES("Employees", 4),
    ADMINISTRATOR("System Administrator", 8);

    private final String levelString;
    private final int level;

    AccessLevel(String levelString, int level) {
        this.levelString = levelString;
        this.level = level;
    }

    @Override
    public String toString() {
        return levelString;
    }

    public int getLevel() {
        return level;
    }

    public boolean equals(AccessLevel accessLevel) {
        assert accessLevel != null;

        return accessLevel.getLevel() == level;
    }

    public boolean notEquals(AccessLevel accessLevel) {
        assert accessLevel != null;

        return accessLevel.getLevel() != level;
    }

    public boolean greater(AccessLevel accessLevel) {
        assert accessLevel != null;

        return accessLevel.getLevel() < level;
    }

    public boolean less(AccessLevel accessLevel) {
        assert accessLevel != null;

        return accessLevel.getLevel() > level;
    }
}
