package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.data.Database;
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
        ICreditGroup[] creditGroups = Database.getInstance().getCreditGroups();

        final List<ICreditGroup> list = new ArrayList<>();
        for (int i = start; i < creditGroups.length && list.size() < max; i++) {
            list.add(new CreditGroupDTO(creditGroups[i]));
        }

        return list.toArray(new ICreditGroup[0]);
    }


    @Override
    public ICreditGroup[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public ICreditGroup[] search(String[] words, int maxResults) {
        final List<Pair<CreditGroupDTO, Integer>> result = new ArrayList<>();

        for (ICreditGroup creditGroup : Database.getInstance().getCreditGroups()) {
            // TODO: Investigate whether linear search is the right one to use
            int matchCount = 0;

            for (String word : words) {
                // TODO: Investigate whether linear search is the right one to use
                if (trueContains(creditGroup.getName(), word)) {
                    matchCount += 1;
                }
            }

            if (matchCount > 0) {
                result.add(new Pair<>(new CreditGroupDTO(creditGroup), matchCount));

                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break;
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

        for (ICreditGroup creditGroup : Database.getInstance().getCreditGroups()) {
            if (trueEquals(creditGroup.getName(), name)) {
                return new CreditGroupDTO(creditGroup);
            }
        }
        return null;
    }


    @Override
    public ICreditGroup getByUUID(String uuid) {
        assert uuid != null;

        return new CreditGroupDTO(Database.getInstance().getCreditGroup(uuid));
    }


    @Override
    public ICreditGroup create(ICreditGroup creditGroup) {
        // TODO: Check the access is correct
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        controlsRequirements(creditGroup);

        if (getByName(creditGroup.getName()) != null) {
            throw new RuntimeException("The credit group name is in use.");
        }

        creditGroup = Database.getInstance().addCreditGroup(creditGroup);

        return new CreditGroupDTO(creditGroup);
    }


    @Override
    public void update(ICreditGroup creditGroup) {
        assert creditGroup != null;

        // TODO: Check the access is correct
        if (!aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        CreditGroupDTO oldCreditGroup = (CreditGroupDTO) getByUUID(creditGroup.getUUID());
        if (oldCreditGroup == null) {
            throw new RuntimeException("Could not find credit group with specified uuid.");
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