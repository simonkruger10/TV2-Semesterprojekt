package com.company.data;

import com.company.common.IProducer;
import com.company.data.entities.Producer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresProducer {
    private final Postgresql postgresql;

    public PostgresProducer(Postgresql postgresql) {
        this.postgresql = postgresql;
    }

    public IProducer[] getProducers() {
        List<IProducer> producers = new ArrayList<IProducer>();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM producer");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Producer producer = Producer.createFromQueryResult(queryResult);
                producer.setAccount(postgresql.getAccount(queryResult.getInt("account_id")));
                producers.add(producer);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return producers.toArray(new IProducer[0]);
    }

    public IProducer getProducer(Integer id) {
        Producer producer = new Producer();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM producer WHERE id=?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                producer = Producer.createFromQueryResult(queryResult);
                producer.setAccount(postgresql.getAccount(queryResult.getInt("account_id")));
            } else {
                throw (new Exception("Production with id \"" + id + "\" not found"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producer;
    }

    public IProducer addProducer(IProducer producer) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO producer (id, name, logo, account_id) VALUES (?,?,?,?)");
            query.setInt(1, producer.getID());
            query.setString(2, producer.getName());
            query.setString(3, producer.getLogo());
            query.setInt(4, producer.getAccount().getID());
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return producer;
    }

    /**
     * Given a IProducer object that already exists in the database (has an ID),
     * will update all (except ID) of the stored values to match the values of the given object
     *
     * @param producer update database values of producer with @param's ID, to the value of @param's values.
     */
    public void updateProducer(IProducer producer) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE producer SET name=?, logo=?, account_id=? WHERE id = ?");
            query.setString(1, producer.getName());
            query.setString(2, producer.getLogo());
            query.setInt(3, producer.getAccount().getID());
            query.setInt(4, producer.getID());
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}