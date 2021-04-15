package com.company.data;
package com.company;

import com.company.data.*;

public class DBController implements Database{
    private Database database;
    private DBController instance;

    private DBController() {
        database = new JsonDatabase();
    }

    public DBController getInstance() {
        if(this.instance == null) {
            this.instance = new DBController();
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
