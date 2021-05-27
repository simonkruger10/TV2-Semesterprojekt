package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.ICreditGroup;
import com.company.data.Database;
import com.company.domain.dto.CreditGroup;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.company.common.Tools.*;

public class CreditGroupManagement implements ICreditGroupManagement {
    private final AccountManagement aMgt = new AccountManagement();

    @Override
    public ICreditGroup[] list() {
        return list(0, 20);
    }

    @Override
    public ICreditGroup[] list(int start) {
        return list(start, 20);
    }

    @Override
    public ICreditGroup[] list(int start, int max) {
        final List<ICreditGroup> list = new ArrayList<>();

        for (ICreditGroup creditGroups: Database.getInstance().getCreditGroups(max, start)) {
            list.add(new CreditGroup(creditGroups));
        }

        return list.toArray(new ICreditGroup[0]);
    }


    @Override
    public ICreditGroup[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public ICreditGroup[] search(String[] words, int maxResults) {
        final List<Pair<CreditGroup, Integer>> result = new ArrayList<>();

        onMaxResults:
        for (String word : words) {
            // TODO: Investigate whether linear search is the right one to use
            int matchCount = 0;

            for (ICreditGroup creditGroup : Database.getInstance().searchCreditGroups(word)) {
                // TODO: Investigate whether linear search is the right one to use
                if (trueContains(creditGroup.getName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(creditGroup.getDescription(), word)) {
                    matchCount += 1;
                }

                result.add(new Pair<>(new CreditGroup(creditGroup), matchCount));

                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break onMaxResults;
                }
            }
        }

        return result.stream()                                      //Iterate
                .sorted((Comparator.comparing(Pair::getValue)))     //Sort after Value -> machCount
                .map(Pair::getKey)                                  //Convert Pair<Key, Value> to Key
                .toArray(ICreditGroup[]::new);                      //Convert the List<Key> into Key[]
    }


    @Override
    public ICreditGroup getByName(String name) {
        assert name != null;

        for (ICreditGroup creditGroup : Database.getInstance().searchCreditGroups(name)) {
            if (trueEquals(creditGroup.getName(), name)) {
                return new CreditGroup(creditGroup);
            }
        }
        return null;
    }


    @Override
    public ICreditGroup getByID(Integer id) {
        assert id != null;

        return new CreditGroup(Database.getInstance().getCreditGroup(id));
    }


    @Override
    public ICreditGroup create(ICreditGroup creditGroup) {
        // TODO: These access requirements are incorrect. A producer can only create a credit group when the producer
        //       create or update own production. Solution: implement an overloading method of create method
        //       with augment production: IProduction
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        controlsRequirements(creditGroup);

        if (getByName(creditGroup.getName()) != null) {
            throw new RuntimeException("The credit group name is in use.");
        }

        creditGroup = Database.getInstance().addCreditGroup(creditGroup);

        return new CreditGroup(creditGroup);
    }


    @Override
    public void update(ICreditGroup creditGroup) {
        assert creditGroup != null;

        // Only administrators can change a credit group
        if (!aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        CreditGroup oldCreditGroup = (CreditGroup) getByID(creditGroup.getID());
        if (oldCreditGroup == null) {
            throw new RuntimeException("Could not find credit group with specified id.");
        }

        if (!trueEquals(oldCreditGroup.getName(), creditGroup.getName())
                && getByName(creditGroup.getName()) != null) {
            throw new RuntimeException("The credit group name is in use.");
        }

        controlsRequirements(creditGroup);

        Database.getInstance().updateCreditGroup(creditGroup);
    }


    private void controlsRequirements(ICreditGroup creditGroup) {
        if (creditGroup == null || isNullOrEmpty(creditGroup.getName())) {
            throw new RuntimeException("Credit group name is required.");
        }
    }
}
