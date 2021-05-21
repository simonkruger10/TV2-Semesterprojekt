package com.company.data;

import com.company.common.*;
import com.company.presentation.entity.Credit;
import com.company.presentation.entity.CreditGroup;
import com.company.presentation.entity.Production;

import javax.xml.transform.Result;
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
            production.setCredit(this.getCredit(queryResult2.getInt("credit_id"), CreditType.PERSON));
        }

        PreparedStatement query3 = connection.prepareStatement("SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        query3.setInt(1, production.getID());
        ResultSet queryResult3 = query.executeQuery();
        while (queryResult2.next()) {
            production.setCredit(this.getCredit(queryResult3.getInt("credit_id"), CreditType.UNIT));
        }
    }

    private void setProduction(IProduction production, PreparedStatement query) throws SQLException {
        query.setString(1, production.getName());
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
                production.setImage(production.getImage());
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
            query.setInt(8, production.getID());
            setProduction(production, query);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setCredit(ResultSet queryResult, Credit credit) throws SQLException {
        credit.setFirstName(queryResult.getString("name"));
        credit.setMiddleName(queryResult.getString("m_name"));
        credit.setLastName(queryResult.getString("l_name"));
        String image = queryResult.getString("image");
        if (image != null) {
            credit.setImage(credit.getImage());
        }
        credit.setEmail(queryResult.getString("email"));
    }

    @Override
    public ICredit[] getCredits() {
        List<ICredit> credits = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_person");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Credit credit = new Credit();
                credit.setID(queryResult.getInt("id"));
                setCredit(queryResult, credit);
                credits.add(credit);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Credit credit = new Credit();
                credit.setID(queryResult.getInt("id"));
                credit.setName(queryResult.getString("name"));
                credits.add(credit);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credits.toArray((new ICredit[0]));
    }

    @Override
    public ICredit getCredit(Integer id, CreditType type) {
        Credit credit = new Credit();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_person WHERE =?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            setCredit(queryResult, credit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit WHERE =?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            credit.setName(queryResult.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credit;
    }

    @Override
    public ICredit addCredit(ICredit credit) {
        if (credit.getType() == CreditType.PERSON) {
            try {
                PreparedStatement query = connection.prepareStatement("INSERT INTO credit_person(name, m_name, l_name, images, email) VALUES (?,?,?,?,?)");
                query.setString(1, credit.getFirstName());
                query.setString(2, credit.getMiddleName());
                query.setString(3, credit.getLastName());
                query.setString(4, credit.getImage());
                query.setString(5, credit.getEmail());
                query.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (credit.getType() == CreditType.UNIT) {
            try {
                PreparedStatement query = connection.prepareStatement("INSERT INTO credit_unit(name) VALUES (?)");
                query.setString(1, credit.getName());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return credit;
    }

    @Override
    public void updateCredit(ICredit credit) {
        if (credit.getType() == CreditType.PERSON) try {
            PreparedStatement query = connection.prepareStatement("UPDATE credit_person SET name =?, m_name=?, l_name=?, image=?, email=?,");
            query.setString(1, credit.getFirstName());
            query.setString(2, credit.getMiddleName());
            query.setString(3, credit.getLastName());
            query.setString(4, credit.getImage());
            query.setString(5, credit.getEmail());
            query.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (credit.getType() == CreditType.UNIT) {
            try {
                PreparedStatement query = connection.prepareStatement("UPDATE credit_unit SET name=?");
                query.setString(1, credit.getName());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


    }


    @Override
    public ICreditGroup[] getCreditGroups() {
        List<ICreditGroup> creditGroups = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_group");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()){
                CreditGroup creditGroup = new CreditGroup();
                creditGroup.setName(queryResult.getString("name"));
                creditGroup.setDescription(queryResult.getString("description"));
                creditGroups.add(creditGroup);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return creditGroups.toArray(new ICreditGroup[0]);
    }

    @Override
    public ICreditGroup getCreditGroup(Integer id) {
        CreditGroup creditGroup = new CreditGroup();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_group WHERE id = ?");
            query.setInt(1,id);
            ResultSet queryResult = query.executeQuery();
            creditGroup.setName(queryResult.getString("name"));
            creditGroup.setDescription(queryResult.getString("description"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return creditGroup;
    }

    @Override
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO credit_group (id, name, description) VALUES (?, ?, ?)");
            query.setInt(1,creditGroup.getID());
            query.setString(2,creditGroup.getName());
            query.setString(3,creditGroup.getDescription());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return creditGroup;
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroup) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE credit_group SET name = ?, description = ? WHERE id=?;");
            query.setInt(3,creditGroup.getID());
            query.setString(1,creditGroup.getName());
            query.setString(2, creditGroup.getDescription());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
