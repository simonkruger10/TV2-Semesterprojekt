package com.company.gui;

public enum Type {
    MESSAGE("message"),
    PRODUCER("producer"),
    RECENTLY_AND_REVIEW("recently and review"),
    LOGIN("login"),
    SEARCH("search"),
    PRODUCTION("production"),
    CREDIT("credit"),
    CREDIT_GROUP("credit group"),
    ACCOUNT("account");

    private final String name;

    Type(String name) {
        this.name = name;
    }
}
