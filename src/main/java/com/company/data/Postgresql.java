package com.company.data;

import com.company.common.*;
import com.company.data.DatabaseFacade;
import com.company.presentation.entity.Credit;
import com.company.presentation.entity.Production;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Postgresql implements DatabaseFacade {
    private static Connection connection = null;

    public Postgresql() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tv2_semesterprojekt_f3f70b5a", "tv2_dbuser_f3f70b5a", "4A03069D");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkAccess() {
        return false;
    }


    @Override
    public IProducer[] getProducers() {
        return new IProducer[0];
    }

    @Override
    public IProducer getProducer(Integer id) {
        return null;
    }

    @Override
    public IProducer addProducer(IProducer producer) {
        return null;
    }

    @Override
    public void updateProducer(IProducer producer) {

    }

    private void creditQuery(Production production, PreparedStatement query) throws SQLException {
        PreparedStatement query2 = connection.prepareStatement("SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        query2.setInt(1, production.getID());
        ResultSet queryResult2 = query.executeQuery();
        while (queryResult2.next()) {
            production.setCredit(this.getCredit(queryResult2.getInt("credit_id")));
        }

        PreparedStatement query3 = connection.prepareStatement("SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        query3.setInt(1, production.getID());
        ResultSet queryResult3 = query.executeQuery();
        while (queryResult2.next()) {
            production.setCredit(this.getCredit(queryResult3.getInt("credit_id")));
        }
    }
    private void setProduction(IProduction production, PreparedStatement query) throws SQLException {
        query.setString(1,production.getName());
        query.setInt(2, production.getReleaseDay());
        query.setInt(3, production.getReleaseMonth());
        query.setInt(4, production.getReleaseYear());
        query.setString(5, production.getDescription());
        query.setString(6, production.getImage());
        query.setString(7, String.valueOf(production.getProducer()));
    }

    @Override
    public IProduction[] getProductions() {
        List<IProduction> productions = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM production");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
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
                production.setProducer(this.getProducer(queryResult.getInt("producer_id")));

                creditQuery(production, query);
                productions.add(production);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return productions.toArray(new IProduction[0]);
    }

    @Override
    public IProduction getProduction(Integer id) {
        Production production = new Production();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM production WHERE ID =?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            production.setName(queryResult.getString("name"));
            production.setDescription(queryResult.getString("description"));
            production.setReleaseDay(queryResult.getInt("release_day"));
            production.setReleaseMonth(queryResult.getInt("release_month"));
            production.setReleaseYear(queryResult.getInt("release_year"));
            String image = queryResult.getString("image");
            if (image != null) {
                production.setDescription(production.getImage());
            }
            creditQuery(production, query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public IProduction addProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO production (name, release_day, release_month, release_year, description, image, producer) VALUES (?,?,?,?,?,?,?)");
            setProduction(production, query);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public void updateProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE production SET name =?, release_day =?, release_month =?, release_year =?, description =?, image=?, producer =? WHERE ID=?");
            query.setInt(8,production.getID());
            setProduction(production, query);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }




    @Override
    public ICredit[] getCredits() {
        ArrayList<ICredit> credits = new ArrayList<ICredit>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_person as credit_unit");
            ResultSet queryResult = query.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ICredit getCredit(Integer id) {
        new Credit();
        return null;
    }

    @Override
    public ICredit addCredit(ICredit credit) {
        return null;
    }

    @Override
    public void updateCredit(ICredit credit) {

    }


    @Override
    public ICreditGroup[] getCreditGroups() {
        return new ICreditGroup[0];
    }

    @Override
    public ICreditGroup getCreditGroup(Integer id) {
        return null;
    }

    @Override
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        return null;
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroup) {

    }


    @Override
    public IAccount[] getAccounts() {
        return new IAccount[0];
    }

    @Override
    public IAccount getAccount(Integer id) {
        return null;
    }

    @Override
    public IAccount addAccount(IAccount account, String hashedPassword) {
        return null;
    }

    @Override
    public void updateAccount(IAccount account) {

    }

    @Override
    public void updateAccount(IAccount account, String hashedPassword) {

    }


    @Override
    public IAccount login(IAccount account, String password) {
        return null;
    }
}
