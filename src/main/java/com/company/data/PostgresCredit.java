package com.company.data;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.data.entities.Credit;
import com.company.data.entities.CreditGroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresCredit {
    public static Credit createFromQueryResult(ResultSet queryResult, CreditType type) throws SQLException {
        return createFromQueryResult(queryResult, type, "name");
    }

    public static Credit createFromQueryResult(ResultSet queryResult, CreditType type, String unitName) throws SQLException {
        Credit credit = new Credit();
        credit.setID(queryResult.getInt("id"));
        credit.setType(type);
        if (type == CreditType.UNIT) {
            credit.setName(queryResult.getString(unitName));
        } else {
            credit.setFirstName(queryResult.getString("f_name"));
            credit.setLastName(queryResult.getString("l_name"));
            credit.setImage(queryResult.getString("image"));
            credit.setEmail(queryResult.getString("email"));
        }

        return credit;
    }

    void attachCreditGroupsToCredit(Credit credit) throws SQLException {
        PreparedStatement query = Postgresql.connection.prepareStatement(
                "SELECT 'credit_person' as type, cg.id as cg_id, cg.name as cg_name, cg.description " +
                        "FROM production_credit_person_relation as pcpr " +
                        "         JOIN credit_group cg on cg.id = pcpr.credit_group_id " +
                        "WHERE pcpr.credit_person_id = ? " +
                        "UNION " +
                        "SELECT 'credit_unit' as type, cg.id as cg_id, cg.name as cg_name, cg.description " +
                        "FROM production_credit_unit_relation as pcur " +
                        "         JOIN credit_group cg on cg.id = pcur.credit_group_id " +
                        "WHERE pcur.credit_unit_id = ?"
        );
        query.setInt(1, credit.getID());
        query.setInt(2, credit.getID());

        ResultSet queryResult = query.executeQuery();
        while (queryResult.next()) {
            CreditGroup creditGroup = new CreditGroup();
            creditGroup.setID(queryResult.getInt("cg_id"));
            creditGroup.setName(queryResult.getString("cg_name"));
            creditGroup.setDescription(queryResult.getString("description"));
            credit.addCreditGroup(creditGroup);
        }
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
                Credit credit;
                if (queryResult.getString("type").equals("credit_person")) {
                    credit = createFromQueryResult(queryResult, CreditType.PERSON);
                } else {
                    credit = createFromQueryResult(queryResult, CreditType.UNIT, "f_name");
                }
                credits.add(credit);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return credits.toArray(new ICredit[0]);
    }

    public ICredit[] getCredits(ICreditGroup creditGroup) {
        List<ICredit> credits = new ArrayList<>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(
                    "SELECT 'credit_person' as type, c.id, c.f_name as f_name, c.l_name, c.email, c.image " +
                            "FROM production_credit_person_relation as pcpr " +
                            "         JOIN credit_person c on c.id = pcpr.credit_person_id " +
                            "WHERE pcpr.credit_group_id = ? " +
                            "UNION " +
                            "SELECT 'credit_unit' as type, c.id, c.name as f_name, null as l_name, null as email, null as image " +
                            "FROM production_credit_unit_relation as pcur " +
                            "         JOIN credit_unit c on c.id = pcur.credit_unit_id " +
                            "WHERE pcur.credit_group_id = ? " +
                            "ORDER BY f_name, l_name"
            );
            query.setInt(1, creditGroup.getID());
            query.setInt(2, creditGroup.getID());

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Credit credit;
                if (queryResult.getString("type").equals("credit_person")) {
                    credit = createFromQueryResult(queryResult, CreditType.PERSON);
                } else {
                    credit = createFromQueryResult(queryResult, CreditType.UNIT, "f_name");
                }
                credits.add(credit);
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
                attachCreditGroupsToCredit(credit);
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
                attachCreditGroupsToCredit(resultCredit);
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
        List<ICredit> credits = new ArrayList<>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(
                    "SELECT 'credit_person' as type, cp.id, cp.f_name, cp.l_name, cp.image, cp.email FROM credit_person as cp " +
                            "WHERE LOWER(cp.f_name) LIKE ? OR LOWER(cp.l_name) LIKE ? " +
                            "UNION " +
                            "SELECT 'credit_unit' as type, cu.id, cu.name as f_name, null as l_name, null as image, null as email FROM credit_unit as cu " +
                            "WHERE LOWER(cu.name) LIKE ?"
            );
            query.setString(1, "%" + word.toLowerCase() + "%");
            query.setString(2, "%" + word.toLowerCase() + "%");
            query.setString(3, "%" + word.toLowerCase() + "%");

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                if (queryResult.getString("type").equals("credit_person")) {
                    credits.add(createFromQueryResult(queryResult, CreditType.PERSON));
                } else {
                    credits.add(createFromQueryResult(queryResult, CreditType.UNIT));
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return credits.toArray(new ICredit[0]);
    }

    public Integer countCredits() {
        int count = 0;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT count(id) FROM credit_unit");
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                count = queryResult.getInt("count");
            }

            query = Postgresql.connection.prepareStatement("SELECT count(id) FROM credit_person");
            queryResult = query.executeQuery();
            if (queryResult.next()) {
                count = queryResult.getInt("count");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return count;
    }
}