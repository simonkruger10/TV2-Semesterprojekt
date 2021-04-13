package com.company;

import com.company.data.Database;
import com.company.data.JsonDatabase;

public class DBController {
    private Database database;

    public DBController() {
        database = new JsonDatabase();
    }
}
