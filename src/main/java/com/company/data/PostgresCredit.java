package com.company.data;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.data.entities.Credit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresCredit {
    public static Credit createFromQueryResult(ResultSet queryResult, CreditType type) throws SQLException {
        Credit credit = new Credit();
        credit.setID(queryResult.getInt("id"));
        credit.setType(type);
        if (type == CreditType.UNIT) {
            credit.setName(queryResult.getString("name"));
        } else {
            credit.setFirstName(queryResult.getString("f_name"));
            credit.setLastName(queryResult.getString("l_name"));
            credit.setImage(queryResult.getString("image"));
            credit.setEmail(queryResult.getString("email"));
        }

        return credit;
    }

    public ICredit[] getCredits(Integer limit, Integer offset) {
        List<ICredit> credits = new ArrayList<>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(
                    "SELECT 'credit_person' as type, cp.id, cp.f_name, cp.l_name, cp.image, cp.email FROM credit_person as cp " +
                            "UNION " +
                            "SELECT 'credit_unit' as type, cu.id, cu.name as f_name, null as l_name, null as image, null as email FROM credit_unit as cu " +
                            "ORDER BY f_name, l_name " +
                            "LIMIT ? OFFSET ?"
            );
            query.setInt(1, limit);
            query.setInt(2, offset);

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                if (queryResult.getString("type").equals("credit_person")) {
                    credits.add(createFromQueryResult(queryResult, CreditType.PERSON));
                } else {
                    Credit credit = new Credit();
                    credit.setID(queryResult.getInt("id"));
                    credit.setType(CreditType.UNIT);
                    credit.setName(queryResult.getString("f_name"));
                    credits.add(credit);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return credits.toArray(new ICredit[0]);
    }

    public ICredit getCredit(Integer id, CreditType type) {
        Credit credit = null;

        try {
            PreparedStatement query;
            if (type == CreditType.PERSON) {
                query = Postgresql.connection.prepareStatement("SELECT * FROM credit_person WHERE id = ?");
            } else {
                query = Postgresql.connection.prepareStatement("SELECT * FROM credit_unit WHERE id = ?");
            }
            query.setInt(1, id);

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                credit = createFromQueryResult(queryResult, type);
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return credit;
    }

    public ICredit addCredit(ICredit credit) {
        Credit resultCredit = null;

        try {
            PreparedStatement query;
            if (credit.getType() == CreditType.PERSON) {
                query = Postgresql.connection.prepareStatement("INSERT INTO credit_person(f_name, l_name, image, email) VALUES (?,?,?,?) RETURNING *");
                query.setString(1, credit.getFirstName());
                query.setString(2, credit.getLastName());
                query.setString(3, credit.getImage());
                query.setString(4, credit.getEmail());
            } else {
                query = Postgresql.connection.prepareStatement("INSERT INTO credit_unit(name) VALUES (?) RETURNING *");
                query.setString(1, credit.getName());
            }

            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                resultCredit = createFromQueryResult(resultSet, credit.getType());
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultCredit;
    }

    //TODO I am not sure this works, and it needs to be tested!
    public void updateCredit(ICredit credit) {
        try {
            PreparedStatement query;
            if (credit.getType() == CreditType.PERSON) {
                query = Postgresql.connection.prepareStatement("UPDATE credit_person SET f_name=?, l_name=?, image=?, email=? WHERE id=?");
                query.setString(1, credit.getFirstName());
                query.setString(2, credit.getLastName());
                query.setString(3, credit.getImage());
                query.setString(4, credit.getEmail());
                query.setInt(5, credit.getID());
            } else {
                query = Postgresql.connection.prepareStatement("UPDATE credit_unit SET name=? WHERE id=?");
                query.setString(1, credit.getName());
                query.setInt(2, credit.getID());
            }

            query.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public boolean deleteCredit(Integer id) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(
                    "with numOfDeletedRows as (DELETE FROM credit_person WHERE id=? returning 1)" +
                            "select count(*) from numOfDeletedRows");
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            resultSet.next();
            int rowsDeleted = resultSet.getInt("count");

            if (rowsDeleted > 1) {
                throw new RuntimeException("More than one line was deleted with id: '" + id + "'");
            }
            return rowsDeleted == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }

    public ICredit[] searchCredits(String word) {
        return new ICredit[0];
    }
}