package com.company.data.entities;

import com.company.common.IAccount;
import com.company.common.IProducer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Producer extends Identifier implements IProducer {
    private String name = null;
    private String logo = null;
    private IAccount account = null;

    public Producer() {
    }

    public Producer(IProducer producer) {
        this.setCopyOf(producer);
    }

    /**
     * Takes a ResultSet containing one or more rows from the Producer Table, reads the values "id, name, logo"
     * and inserts them into a new instance of this Producer class. Then returns that object.
     *
     * Pre-requisite: the ResultSet must have had at least one ".next()" call.
     *
     * @param queryResult a ResultSet containing one or more rows from the Producer Table, reads the values "id, name, logo"
     * @return A Producer object containing an id, name and logo value.
     * @throws SQLException
     */
    public static Producer createFromQueryResult(ResultSet queryResult) throws SQLException {
        Producer producer = new Producer();
        producer.setID(queryResult.getInt("id"));
        producer.setName(queryResult.getString("name"));
        producer.setLogo(queryResult.getString("logo"));
        return producer;
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    @Override
    public IAccount getAccount() {
        return account;
    }

    public void setAccount(IAccount account) {
        this.account = account;
    }


    public void setCopyOf(IProducer producer) {
        assert producer != null;

        this.setName(producer.getName());
        this.setLogo(producer.getLogo());
        this.setAccount(new Account(producer.getAccount()));
    }
}
