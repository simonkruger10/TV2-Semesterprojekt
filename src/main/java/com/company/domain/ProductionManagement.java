package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.IProduction;
import com.company.data.Database;
import com.company.domain.descriptions.Production;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.company.common.Tools.*;

public class ProductionManagement implements IProductionManagement {
    private final AccountManagement aMgt = new AccountManagement();

    @Override
    public IProduction[] list() {
        return list(0, 20);
    }

    @Override
    public IProduction[] list(int start) {
        return list(start, 20);
    }

    @Override
    public IProduction[] list(int start, int max) {
        IProduction[] productions = Database.getInstance().getProductions();

        final List<IProduction> list = new ArrayList<>();
        for (int i = start; i < productions.length && list.size() < max; i++) {
            list.add(new Production(productions[i]));
        }

        return list.toArray(new IProduction[0]);
    }


    @Override
    public IProduction[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public IProduction[] search(String[] words, int maxResults) {
        final List<Pair<Production, Integer>> result = new ArrayList<>();

        for (IProduction production : Database.getInstance().getProductions()) {
            // TODO: Investigate whether linear search is the right one to use
            int matchCount = 0;

            for (String word : words) {
                // TODO: Investigate whether linear search is the right one to use
                if (trueContains(production.getName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(production.getDescription(), word)) {
                    // TODO: Count the number of matches in the entire description instead of counting 1 match
                    matchCount += 1;
                }
            }

            if (matchCount > 0) {
                result.add(new Pair<>(new Production(production), matchCount));

                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break;
                }
            }
        }

        return result.stream()                                      //Iterate
                .sorted((Comparator.comparing(Pair::getValue)))     //Sort after Value -> machCount
                .map(Pair::getKey)                                  //Convert Pair<Key, Value> to Key
                .toArray(IProduction[]::new);                       //Convert the List<Key> into Key[]
    }


    @Override
    public IProduction[] getByName(String name) {
        assert name != null;

        final List<Production> result = new ArrayList<>();

        for (IProduction production : Database.getInstance().getProductions()) {
            if (trueEquals(production.getName(), name)) {
                result.add(new Production(production));
            }
        }

        return result.toArray(new IProduction[0]);
    }


    @Override
    public IProduction getByID(Integer id) {
        assert id != null;

        return new Production(Database.getInstance().getProduction(id));
    }


    @Override
    public IProduction create(IProduction production) {
        controlsAccess();

        controlsRequirements(production);

        // TODO: Check for duplicates

        production = Database.getInstance().addProduction(production);

        return new Production(production);
    }


    @Override
    public void update(IProduction production) {
        controlsAccess();

        if (getByID(production.getID()) == null) {
            throw new RuntimeException("Could not find production with specified id.");
        }

        controlsRequirements(production);

        // TODO: Check for duplicates

        Database.getInstance().updateProduction(production);
    }


    private void controlsAccess() {
        // TODO: Check the access is correct
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }
    }

    private void controlsRequirements(IProduction production) {
        if (production == null || isNullOrEmpty(production.getName()) || production.getCredits() == null
                || production.getProducer() == null) {
            throw new RuntimeException("Production name, one producer and one credit is required.");
        }
    }
}
