package com.company.data;

import com.company.common.ICreditGroup;
import com.company.data.entities.CreditGroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresCreditGroup {
    public PostgresCreditGroup() {
    }

    public static CreditGroup createFromQueryResult(ResultSet queryResult) throws SQLException {
        CreditGroup cg = new CreditGroup();
        cg.setDescription(queryResult.getString("description"));
        cg.setName(queryResult.getString("name"));
        cg.setID(queryResult.getInt("id"));

        return cg;
    }

    public ICreditGroup[] getCreditGroups(Integer limit, Integer offset) {
        List<ICreditGroup> creditGroups = new ArrayList<ICreditGroup>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_group LIMIT ? OFFSET ?");
            query.setInt(1, limit);
            query.setInt(2, offset);

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                creditGroups.add(createFromQueryResult(queryResult));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return creditGroups.toArray(new ICreditGroup[0]);
    }

    public ICreditGroup getCreditGroup(Integer id) {
        CreditGroup creditGroup = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_group WHERE id = ?");
            query.setInt(1, id);

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                creditGroup = createFromQueryResult(queryResult);
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return creditGroup;
    }

    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        CreditGroup createdCreditGroup = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO credit_group (name, description) VALUES (?, ?) RETURNING *");
            query.setString(1, creditGroup.getName());
            query.setString(2, creditGroup.getDescription());

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                createdCreditGroup = createFromQueryResult(queryResult);
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return createdCreditGroup;
    }

    public void updateCreditGroup(ICreditGroup creditGroup) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE credit_group SET name = ?, description = ? WHERE id=?;");
            query.setInt(3, creditGroup.getID());
            query.setString(1, creditGroup.getName());
            query.setString(2, creditGroup.getDescription());

            query.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public ICreditGroup[] searchCreditGroups(String word) {
        List<ICreditGroup> producers = new ArrayList<>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_group WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ?");
            query.setString(1, "%" + word.toLowerCase() + "%");
            query.setString(2, "%" + word.toLowerCase() + "%");

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                producers.add(createFromQueryResult(queryResult));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return producers.toArray(new ICreditGroup[0]);
    }

    public Integer countCreditGroups() {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT count(id) FROM credit_group");
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