package com.company.data;

import com.company.common.*;
import com.company.data.entities.Credit;
import com.company.data.entities.CreditGroup;
import com.company.data.entities.Production;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresProduction {
    private final Postgresql postgresql;
    private final int batchMaxSize = 1000;

    public PostgresProduction(Postgresql postgresql) {
        this.postgresql = postgresql;
    }

    public static Production createFromQueryResult(ResultSet queryResult) throws SQLException {
        Production production = new Production();
        production.setID(queryResult.getInt("id"));
        production.setName(queryResult.getString("name"));
        production.setDescription(queryResult.getString("description"));
        production.setImage(queryResult.getString("Image"));
        production.setReleaseDay(queryResult.getInt("release_day"));
        production.setReleaseMonth(queryResult.getInt("release_month"));
        production.setReleaseYear(queryResult.getInt("release_year"));

        return production;
    }

    /**
     * Finds all credits belonging to the given production, and creates a Credit object to each one.
     * Then finds out what each credit is credited for (Its' CreditGroup(s)), creating CreditGroup objects
     * and attaches them to each credit.
     *
     * @param production A Production with at least an ID. The Production to be given credits from the database.
     * @throws SQLException
     */
    void attachCreditsToProduction(Production production) throws SQLException {
        PreparedStatement query = Postgresql.connection.prepareStatement(
                "SELECT 'credit_person' as type, c.id as c_id, c.f_name as c_name, c.l_name, c.email, c.image, cg.id as cg_id, cg.name as cg_name, cg.description " +
                        "FROM production_credit_person_relation as pcpr " +
                        "         JOIN credit_person c on c.id = pcpr.credit_person_id " +
                        "         JOIN credit_group cg on cg.id = pcpr.credit_group_id " +
                        "WHERE pcpr.production_id = ? " +
                        "UNION " +
                        "SELECT 'credit_unit' as type, c.id as c_id, c.name as c_name, null as l_name, null as email, null as image, cg.id as cg_id, cg.name as cg_name, cg.description " +
                        "FROM production_credit_unit_relation as pcur " +
                        "         JOIN credit_unit c on c.id = pcur.credit_unit_id " +
                        "         JOIN credit_group cg on cg.id = pcur.credit_group_id " +
                        "WHERE pcur.production_id = ?"
        );
        query.setInt(1, production.getID());
        query.setInt(2, production.getID());

        ResultSet queryResult = query.executeQuery();
        while (queryResult.next()) {
            // Creating CreditGroup
            CreditGroup creditGroup = new CreditGroup();
            creditGroup.setID(queryResult.getInt("cg_id"));
            creditGroup.setName(queryResult.getString("cg_name"));
            creditGroup.setDescription(queryResult.getString("description"));

            // Creating credit
            Credit credit = new Credit();
            credit.setID(queryResult.getInt("c_id"));
            if (queryResult.getString("type").equals("credit_unit")) {
                credit.setType(CreditType.UNIT);
                credit.setName(queryResult.getString("c_name"));
            } else {
                credit.setType(CreditType.PERSON);
                credit.setFirstName(queryResult.getString("c_name"));
                credit.setLastName(queryResult.getString("l_name"));
                credit.setEmail(queryResult.getString("email"));
                credit.setImage(queryResult.getString("image"));
            }
            credit.addCreditGroup(creditGroup);

            production.setCredit(credit);
        }
    }

    /**
     * Sets the values of the given query's arguments to the value of the given production's fields' values
     *
     * @param production Production which will be saved or updated
     * @param query      The query which saves or updates a Production in the database. The Query's arguments must be:
     *                   name, release_day, release_month, release_year, description, image, producer_id
     * @throws SQLException
     */
    void setArguments(IProduction production, PreparedStatement query) throws SQLException {
        //Argument order: name, release_day, release_month, release_year, description, image, producer_id
        query.setString(1, production.getName());
        query.setInt(2, production.getReleaseDay());
        query.setInt(3, production.getReleaseMonth());
        query.setInt(4, production.getReleaseYear());
        query.setString(5, production.getDescription());
        query.setString(6, production.getImage());
        query.setString(7, String.valueOf(production.getProducer()));
    }

    private void addCreditRelations(String sql, IProduction production, CreditType creditType) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(sql);

            int batchCount = 0;
            for (ICredit credit : production.getCredits()) {
                for (ICreditGroup creditGroup : credit.getCreditGroups()) {
                    if (credit.getType().equals(creditType)) {
                        query.setInt(1, production.getID());
                        query.setInt(2, credit.getID());
                        query.setInt(3, creditGroup.getID());
                        query.addBatch();

                        if (++batchCount % batchMaxSize == 0) {
                            query.executeBatch();
                        }
                    }
                }
            }

            query.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public IProduction[] getProductions(Integer limit, Integer offset) {
        List<IProduction> productions = new ArrayList<IProduction>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM production LIMIT ? OFFSET ?");
            query.setInt(1, limit);
            query.setInt(2, offset);

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                productions.add(createFromQueryResult(queryResult));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productions.toArray(new IProduction[0]);
    }

    public IProduction getProduction(Integer id) {
        Production production = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM production WHERE id =?");
            query.setInt(1, id);

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                production = createFromQueryResult(queryResult);
                production.setProducer(postgresql.getProducer(queryResult.getInt("producer_id")));
                attachCreditsToProduction(production);
            } // TODO: throw exception instead of return null

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return production;
    }

    public IProduction addProduction(IProduction production) {
        Production createdProduction = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO production (name, release_day, release_month, release_year, description, image, producer_id) VALUES (?,?,?,?,?,?,?) RETURNING *");
            setArguments(production, query);

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                createdProduction = createFromQueryResult(queryResult);
                createdProduction.setProducer(postgresql.getProducer(queryResult.getInt("producer_id")));

                addCreditRelations("INSERT INTO production_credit_person_relation (production_id, credit_person_id, credit_group_id) VALUES (?,?,?)",
                        production, CreditType.PERSON);
                addCreditRelations("INSERT INTO production_credit_unit_relation (production_id, credit_unit_id, credit_group_id) VALUES (?,?,?)",
                        production, CreditType.UNIT);

                attachCreditsToProduction(createdProduction);
            } // TODO: throw exception instead of return null

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return createdProduction;
    }

    public IProduction[] getProductionByCredit(ICredit credit) {
        List<IProduction> creditedFor = new ArrayList<>();

        try {
            PreparedStatement query;
            if (credit.getType().equals(CreditType.PERSON)) {
                query = Postgresql.connection.prepareStatement(
                        "SELECT p.* " +
                                "FROM production_credit_person_relation as pcpr " +
                                "         JOIN production p on p.id = pcpr.production_id " +
                                "WHERE pcpr.credit_person_id = ? "
                );
            } else {
                query = Postgresql.connection.prepareStatement(
                        "SELECT p.* " +
                                "FROM production_credit_unit_relation as pcur " +
                                "         JOIN production p on p.id = pcur.production_id " +
                                "WHERE pcur.credit_unit_id = ?"
                );
            }
            query.setInt(1, credit.getID());

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                creditedFor.add(createFromQueryResult(queryResult));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return creditedFor.toArray(new IProduction[0]);
    }

    public void updateProduction(IProduction production) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(
                    "UPDATE production SET name =?, release_day =?, release_month =?, release_year =?, description =?, image=?, producer_id =? WHERE id=?"
            );
            setArguments(production, query);
            query.setInt(8, production.getID());
            query.execute();

            query = Postgresql.connection.prepareStatement("DELETE FROM production_credit_person_relation WHERE production_id = ?");
            query.setInt(1, production.getID());
            query.execute();

            addCreditRelations("INSERT INTO production_credit_person_relation (production_id, credit_person_id, credit_group_id) VALUES (?,?,?)",
                    production, CreditType.PERSON);

            query = Postgresql.connection.prepareStatement("DELETE FROM production_credit_unit_relation WHERE production_id = ?");
            query.setInt(1, production.getID());
            query.execute();

            addCreditRelations("INSERT INTO production_credit_unit_relation (production_id, credit_unit_id, credit_group_id) VALUES (?,?,?)",
                    production, CreditType.UNIT);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public IProduction[] searchProductions(String word) {
        return new IProduction[0];
    }
}