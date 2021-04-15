package com.company.data;

import com.company.data.*;

public class DatabaseController implements Database{
    private Database database;
    private DatabaseController instance;

    private DatabaseController() {
        database = new JsonDatabase();
    }

    public DatabaseController getInstance() {
        if(this.instance == null) {
            this.instance = new DatabaseController();
        }
        return this.instance;
    }

    @Override
    public void getProductions() {

    }

    @Override
    public void addProduction(Production production) {

    }

    @Override
    public void getCredits(Production production) {

    }

    @Override
    public void addCredit(Production production) {

    }

    @Override
    public boolean validLogin(String username, String password) {
        return false;
    }
}
