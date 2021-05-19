package com.company.data;

import com.company.common.*;
import com.company.data.DatabaseFacade;
import com.company.gui.entity.Production;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Postgresql implements DatabaseFacade {
    private static Connection connection = null;

    public Postgresql() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("" +
                            "jdbc:postgresql://localhost:5432/tv2_semesterprojekt",
                    "postgres",
                    "Alex1234");
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


    @Override
    public IProduction[] getProductions() {
        List<IProduction> productions = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM production");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Production production = new Production();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public IProduction addProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO production VALUES (name = ?, release_day = ?, release_month = ?, release_year = ?, description = ?, image = ?, producer = ?)");
            query.setString(1, production.getName());
            query.setInt(2, production.getReleaseDay());
            query.setInt(3, production.getReleaseMonth());
            query.setInt(4, production.getReleaseYear());
            if (production.getDescription() != null) {
                query.setString(5, production.getDescription());
            }
            if (production.getDescription() != null) {
                query.setString(6, production.getImage());
            } //skal checkes om der er et billede
            if (production.getProducer()!= null) {
                query.setString(7, String.valueOf(production.getProducer()));
            }
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public void updateProduction(IProduction production) {

    }


    @Override
    public ICredit[] getCredits() {
        return new ICredit[0];
    }

    @Override
    public ICredit getCredit(Integer uuid) {
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
