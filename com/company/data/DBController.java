package com.company.data;

public class DBController {
    private Database database;
    private DBController instance;

    private DBController() {
        database = new JsonDatabase();
    }

    public DBController getInstance() {
        return this.instance;
    }
}
