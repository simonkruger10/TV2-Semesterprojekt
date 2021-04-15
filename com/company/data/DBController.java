package com.company;

import com.company.data.Database;
import com.company.data.JsonDatabase;

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
