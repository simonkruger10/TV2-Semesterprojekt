package com.company.domain;

import com.company.common.IAccount;
import com.company.common.ICredit;
import com.company.common.IProducer;
import com.company.common.IProduction;
import com.company.data.Database;
import com.company.domain.dto.Production;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.company.common.Tools.*;

public class ProductionManagement implements IProductionManagement {
    private final AccountManagement aMgt = new AccountManagement();
    private final ProducerManagement pMgt = new ProducerManagement();

    @Override
    public IProduction[] list() {
        return list(0, 10);
    }

    @Override
    public IProduction[] list(int start) {
        return list(start, 10);
    }

    @Override
    public IProduction[] list(int start, int max) {
        final List<IProduction> list = new ArrayList<>();

        for (IProduction production: Database.getInstance().getProductions(max, start)) {
            list.add(new Production(production));
        }

        return list.toArray(new IProduction[0]);
    }


    @SuppressWarnings("unused")
    @Override
    public IProduction[] search(String[] words) {
        return search(words, 10);
    }

    @Override
    public IProduction[] search(String[] words, int maxResults) {
        final List<Pair<Production, Integer>> result = new ArrayList<>();

        onMaxResults:
        for (String word : words) {
            // TODO: Investigate whether linear search is the right one to use
            int matchCount = 0;

            for (IProduction production : Database.getInstance().searchProductions(word)) {
                // TODO: Investigate whether linear search is the right one to use
                if (trueContains(production.getName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(production.getDescription(), word)) {
                    // TODO: Count the number of matches in the entire description instead of counting 1 match
                    matchCount += 1;
                }

                result.add(new Pair<>(new Production(production), matchCount));

                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break onMaxResults;
                }
            }
        }

        return result.stream()                                      //Iterate
                .sorted((Comparator.comparing(Pair::getValue)))     //Sort after Value -> machCount
                .map(Pair::getKey)                                  //Convert Pair<Key, Value> to Key
                .toArray(IProduction[]::new);                       //Convert the List<Key> into Key[]
    }


    @SuppressWarnings("unused")
    @Override
    public IProduction[] getByName(String name) {
        assert name != null;

        final List<Production> result = new ArrayList<>();

        for (IProduction production : Database.getInstance().searchProductions(name)) {
            if (trueEquals(production.getName(), name)) {
                result.add(new Production(production));
            }
        }

        return result.toArray(new IProduction[0]);
    }

    @Override
    public IProduction[] getProductionByCredit(ICredit credit) {
        assert credit != null;

        final List<Production> result = new ArrayList<>();

        for (IProduction production : Database.getInstance().getProductionByCredit(credit)) {
            result.add(new Production(production));
        }

        return result.toArray(new IProduction[0]);
    }

    @Override
    public IProduction[] getProducedBy(IProducer producer) {
        assert producer != null;
        assert producer.getID() != null;

        IProduction[] dbProductions = Database.getInstance().getProductions(producer);
        IProduction[] productions = new IProduction[dbProductions.length];
        for (int i = 0; i < dbProductions.length; i++) {
            productions[i] = new Production(dbProductions[i]);
        }

        return productions;
    }

    @Override
    public IProduction getByID(Integer id) {
        assert id != null;

        return new Production(Database.getInstance().getProduction(id));
    }


    @SuppressWarnings("unused")
    @Override
    public IProduction create(IProduction production) {
        controlsRequirements(production);

        IProducer producer = pMgt.getByID(production.getProducer().getID());
        if (producer == null) {
            throw new RuntimeException("Could not find the producer with specified producer id.");
        }

        IAccount producerAccount = producer.getAccount();
        if (!aMgt.isAdmin() && (producerAccount == null ||
                !trueEquals(producerAccount.getID(), aMgt.getCurrentUser().getID()))) {
            throw new AccessControlException("Insufficient permission.");
        }

        // TODO: Check for duplicates

        production = Database.getInstance().addProduction(production);

        return new Production(production);
    }


    @SuppressWarnings("unused")
    @Override
    public void update(IProduction production) {
        IProduction oldProduction = getByID(production.getID());

        if (oldProduction == null) {
            throw new RuntimeException("Could not find production with specified id.");
        }

        IAccount producerAccount = pMgt.getByID(oldProduction.getProducer().getID()).getAccount();
        if (!aMgt.isAdmin() && (producerAccount == null ||
                !trueEquals(producerAccount.getID(), aMgt.getCurrentUser().getID()))) {
            throw new AccessControlException("Insufficient permission.");
        }

        controlsRequirements(production);

        // TODO: Check for duplicates

        Database.getInstance().updateProduction(production);
    }

    @Override
    public Integer count() {
        return Database.getInstance().countProductions();
    }

    private void controlsRequirements(IProduction production) {
        if (production == null || isNullOrEmpty(production.getName()) || isNullOrEmpty(production.getImage()) ||
                isNullOrEmpty(production.getDescription()) || production.getReleaseYear() == null ||
                production.getReleaseMonth() == null || production.getReleaseDay() == null ||
                production.getCredits() == null || production.getCredits().length == 0 ||
                production.getProducer() == null) {
            throw new RuntimeException("Production name, one producer and one credit is required.");
        }
    }
}
