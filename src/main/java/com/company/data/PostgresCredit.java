package com.company.data;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.data.mapper.Credit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PostgresCredit {
    public PostgresCredit() {
    }

    public static Credit createFromQueryResult(ResultSet queryResult, CreditType type) throws SQLException {
        Credit credit = new Credit();
        credit.setType(type);
        credit.setFirstName(queryResult.getString("name"));
        credit.setMiddleName(queryResult.getString("m_name"));
        credit.setLastName(queryResult.getString("l_name"));
        String image = queryResult.getString("image");
        if (image != null) {
            credit.setImage(credit.getImage());
        }
        credit.setEmail(queryResult.getString("email"));
        credit.setID(queryResult.getInt("id"));

        return credit;
    }

    public ICredit[] getCredits() {
        HashMap<Integer, ICredit> credits = new HashMap<Integer, ICredit>();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_person");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Credit credit = createFromQueryResult(queryResult, CreditType.PERSON);
                credits.put(credit.getID(), credit);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_unit");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                int id = queryResult.getInt("id");
                Credit credit = (Credit) credits.get(id);
                credit.setName(queryResult.getString("name"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return credits.values().toArray((new ICredit[0]));
    }

    public ICredit getCredit(Integer id, CreditType type) {
        Credit credit = new Credit();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_person WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next())
                credit = createFromQueryResult(queryResult, CreditType.PERSON);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM credit_unit WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next())
                credit.setName(queryResult.getString("name"));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return credit;
    }

    public ICredit addCredit(ICredit credit) {
        ICredit resultCredit = null;
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(
                    "INSERT INTO credit_person(name, m_name, l_name, image, email) VALUES (?,?,?,?,?) RETURNING *");
            query.setString(1, credit.getFirstName());
            query.setString(2, credit.getMiddleName());
            query.setString(3, credit.getLastName());
            query.setString(4, credit.getImage());
            query.setString(5, credit.getEmail());
            ResultSet resultSet = query.executeQuery();
            resultCredit = createFromQueryResult(resultSet, CreditType.PERSON);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return resultCredit;
    }

    //TODO I am not sure this works, and it needs to be tested!
    public void updateCredit(ICredit credit) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE credit_person SET name =?, m_name=?, l_name=?, image=?, email=?");
            query.setString(1, credit.getFirstName());
            query.setString(2, credit.getMiddleName());
            query.setString(3, credit.getLastName());
            query.setString(4, credit.getImage());
            query.setString(5, credit.getEmail());
            query.executeQuery();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}