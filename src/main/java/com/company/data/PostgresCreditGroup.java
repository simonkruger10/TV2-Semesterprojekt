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

    public ICreditGroup[] getCreditGroups() {
        List<ICreditGroup> creditGroups = new ArrayList<ICreditGroup>();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_group");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                CreditGroup creditGroup = createFromQueryResult(queryResult);
                creditGroups.add(creditGroup);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return creditGroups.toArray(new ICreditGroup[0]);
    }

    public ICreditGroup getCreditGroup(Integer id) {
        CreditGroup creditGroup = new CreditGroup();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_group WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            creditGroup = createFromQueryResult(queryResult);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return creditGroup;
    }

    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO credit_group (id, name, description) VALUES (?, ?, ?)");
            query.setInt(1, creditGroup.getID());
            query.setString(2, creditGroup.getName());
            query.setString(3, creditGroup.getDescription());
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return creditGroup;
    }

    public void updateCreditGroup(ICreditGroup creditGroup) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE credit_group SET name = ?, description = ? WHERE id=?;");
            query.setInt(3, creditGroup.getID());
            query.setString(1, creditGroup.getName());
            query.setString(2, creditGroup.getDescription());
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}