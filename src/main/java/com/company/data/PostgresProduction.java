package com.company.data;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.IProduction;
import com.company.data.entities.Credit;
import com.company.data.entities.CreditGroup;
import com.company.data.entities.Production;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostgresProduction {
    private final Postgresql postgresql;

    public PostgresProduction(Postgresql postgresql) {
        this.postgresql = postgresql;
    }

    public static Production createFromQueryResult(ResultSet queryResult) throws SQLException {
        Production production = new Production();
        production.setID(queryResult.getInt("id"));
        production.setName(queryResult.getString("name"));
        production.setDescription(queryResult.getString("description"));
        String image = queryResult.getString("Image");
        if (image != null) {
            production.setImage(production.getImage());
        }
        production.setReleaseDay(queryResult.getInt("release_day"));
        production.setReleaseMonth(queryResult.getInt("release_month"));
        production.setReleaseYear(queryResult.getInt("release_year"));

        if (image != null) {
            production.setImage(production.getImage());
        }

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
        //Find all the credits that are in this production.
        PreparedStatement queryProductionCreditIDs = Postgresql.connection.prepareStatement(
                "SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        queryProductionCreditIDs.setInt(1, production.getID());
        ResultSet resultProductionCreditIDs = queryProductionCreditIDs.executeQuery();

        while (resultProductionCreditIDs.next()) {
            production.setCredit(postgresql.getCredit(resultProductionCreditIDs.getInt("credit_id"), CreditType.PERSON));
        }

        //Attach each credit with its credit group id for this production.
        PreparedStatement queryCreditGroupID = Postgresql.connection.prepareStatement(
                "SELECT credit_id, credit_group_id FROM production_credit_person_relation WHERE production_id = ?");
        queryCreditGroupID.setInt(1, production.getID());
        ResultSet creditIdCreditGroupPairs = queryCreditGroupID.executeQuery();

        HashMap<Integer, List<Integer>> ccgIdPair = new HashMap<Integer, List<Integer>>();
        while (creditIdCreditGroupPairs.next()) {
            int cid = creditIdCreditGroupPairs.getInt("credit_id");
            int cGroupId = creditIdCreditGroupPairs.getInt("credit_group_id");
            ccgIdPair.computeIfAbsent(cid, k -> new ArrayList<Integer>()); //If the List is null, initialize one.
            ccgIdPair.get(cid).add(cGroupId);
        }

        //TODO this pull is faster up until the credit_group table becomes too large.
        PreparedStatement queryCreditGroup = Postgresql.connection.prepareStatement("SELECT * FROM credit_group");
        ResultSet creditGroupResults = queryCreditGroup.executeQuery();

        //Save all CreditGroups in a map
        HashMap<Integer, CreditGroup> idToCreditGroup = new HashMap<Integer, CreditGroup>();
        while (creditGroupResults.next()) {
            CreditGroup cg = PostgresCreditGroup.createFromQueryResult(creditGroupResults);
            idToCreditGroup.put(cg.getID(), cg);
        }

        //for each credit, find all its credit groups and assign them to the credit.
        for (ICredit c : production.getCredits()) {
            for (int creditGroupID : ccgIdPair.get(c.getID())) {         //Iterate over all the IDs of the CreditGroup, of which the credit is assigned.
                CreditGroup cg = idToCreditGroup.get(creditGroupID);    //Get the CreditGroup matching that id
                ((Credit) c).addCreditGroup(cg);                        //Assign the credit that CreditGroup.
            }
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

    public IProduction[] getProductions() {
        List<IProduction> productions = new ArrayList<IProduction>();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM production");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Production production = createFromQueryResult(queryResult);
                production.setProducer(postgresql.getPostgresProducer().getProducer(queryResult.getInt("producer_id")));
                //attachCreditsToProduction(production);
                productions.add(production);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return productions.toArray(new IProduction[0]);
    }

    public IProduction getProduction(Integer id) {
        Production production = new Production();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM production WHERE ID =?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            queryResult.next(); //TODO Maybe check to see for more than 1 results and notify of database corruption/duplication.
            production = createFromQueryResult(queryResult);
            production.setProducer(postgresql.getPostgresProducer().getProducer(queryResult.getInt("producer_id")));
            attachCreditsToProduction(production);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return production;
    }

    public IProduction addProduction(IProduction production) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO production (name, release_day, release_month, release_year, description, image, producer_id) VALUES (?,?,?,?,?,?,?)");
            setArguments(production, query);
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return production;
    }

    public void updateProduction(IProduction production) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement(
                    "UPDATE production SET name =?, release_day =?, release_month =?, " +
                            "release_year =?, description =?, image=?, producer_id =? WHERE ID=?");
            query.setInt(8, production.getID());
            setArguments(production, query);
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}