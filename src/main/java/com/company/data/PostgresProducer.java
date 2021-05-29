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

    /**
     * Takes a ResultSet containing one or more rows from the Producer Table, reads the values "id, name, logo"
     * and inserts them into a new instance of this Producer class. Then returns that object.
     *
     * Pre-requisite: the ResultSet must have had at least one ".next()" call.
     *
     * @param queryResult a ResultSet containing one or more rows from the Producer Table, reads the values "id, name, logo"
     * @return A Producer object containing an id, name and logo value.
     */
    public static Producer createFromQueryResult(ResultSet queryResult) throws SQLException {
        Producer producer = new Producer();
        producer.setID(queryResult.getInt("id"));
        producer.setName(queryResult.getString("name"));
        producer.setLogo(queryResult.getString("logo"));
        return producer;
    }

    public IProducer[] getProducers(Integer limit, Integer offset) {
        List<IProducer> producers = new ArrayList<>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM producer LIMIT ? OFFSET ?");
            query.setInt(1, limit);
            query.setInt(2, offset);

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                producers.add(createFromQueryResult(queryResult));
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
                producer = createFromQueryResult(queryResult);
                producer.setAccount(postgresql.getAccount(queryResult.getInt("account_id")));
            } // TODO: throw exception instead of return null
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return producer;
    }

    public IProducer addProducer(IProducer producer) {
        Producer createdProducer = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO producer (name, logo, account_id) VALUES (?,?,?) RETURNING *");
            query.setString(1, producer.getName());
            query.setString(2, producer.getLogo());
            query.setInt(3, producer.getAccount().getID());

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                createdProducer = createFromQueryResult(queryResult);
                createdProducer.setAccount(postgresql.getAccount(queryResult.getInt("account_id")));
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return createdProducer;
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

            query.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public IProducer[] searchProducers(String word) {
        List<IProducer> producers = new ArrayList<>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM producer WHERE LOWER(name) LIKE ?");
            query.setString(1, "%" + word.toLowerCase() + "%");

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                producers.add(createFromQueryResult(queryResult));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return producers.toArray(new IProducer[0]);
    }

    public Integer countProducers() {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT count(id) FROM producer");
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                return queryResult.getInt("count");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }
}