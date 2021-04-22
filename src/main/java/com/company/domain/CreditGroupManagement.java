package com.company.domain;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.company.tools.*;
import static com.company.tools.trueContains;

public class CreditGroupManagement implements ICreditGroupManagement {
    private final AccountManagement aMgt = new AccountManagement();
    private final List<CreditGroupDTO> creditGroups = new ArrayList<>();

    @Override
    public ICreditGroup[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public ICreditGroup[] search(String[] words, int maxResults) {
        final List<Pair<CreditGroupDTO, Integer>> result = new ArrayList<>();

        for (CreditGroupDTO credit : creditGroups) {
            int matchCount = 0;

            for (String word : words) {
                if (trueContains(credit.getName(), word)) {
                    matchCount += 1;
                }
            }

            if (matchCount > 0) {
                result.add(new Pair<>(credit, matchCount));

                if (result.size() >= maxResults) {
                    break;
                }
            }
        }

        result.sort(new Comparator<Pair<CreditGroupDTO, Integer>>() {
            @Override
            public int compare(Pair<CreditGroupDTO, Integer> pair1, Pair<CreditGroupDTO, Integer> pair2) {
                // -1 - less than, 1 - greater than, 0 - equal
                return pair1.getValue().compareTo(pair2.getValue());
            }
        });

        ICreditGroup[] list = new ICreditGroup[result.size()];
        for (int i = 0; i < result.size(); i++) {
            list[i] = result.get(i).getKey();
        }
        return list;
    }


    @Override
    public ICreditGroup[] getByName(String name) {
        final List<CreditGroupDTO> result = new ArrayList<>();

        for (CreditGroupDTO creditGroup : creditGroups) {
            if (trueEquals(creditGroup.getName(), name)) {
                result.add(creditGroup);
            }
        }

        return result.toArray(new ICreditGroup[0]);
    }


    @Override
    public ICreditGroup getByUUID(String uuid) {
        for (CreditGroupDTO creditGroup : creditGroups) {
            if (trueEquals(creditGroup.getUUID(), uuid)) {
                return creditGroup;
            }
        }

        return null;
    }


    @Override
    public ICreditGroup create(ICreditGroup creditGroup) {
        // TODO: Hvem har adgang???
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("The user is not allowed to create credit groups.");
        }

        controlsRequirements(creditGroup);

        CreditGroupDTO newCreditGroup = new CreditGroupDTO();
        newCreditGroup.copyCreditGroup(creditGroup);

        creditGroups.add(newCreditGroup);

        return newCreditGroup;
    }


    @Override
    public void update(ICreditGroup creditGroup) {
        assert creditGroup != null;

        update(creditGroup.getUUID(), creditGroup);
    }

    @Override
    public void update(String uuid, ICreditGroup creditGroup) {
        // TODO: Hvem har adgang???
        if (!aMgt.isAdmin()) {
            throw new AccessControlException("The user is not allowed to create credit groups.");
        }

        controlsRequirements(creditGroup);

        CreditGroupDTO creditGroupDTO = (CreditGroupDTO) getByUUID(uuid);
        if (creditGroupDTO == null) {
            throw new RuntimeException("Could not the credit group by the specified uuid.");
        }
        creditGroupDTO.copyCreditGroup(creditGroup);
    }

    private void controlsRequirements(ICreditGroup creditGroup) {
        if (creditGroup == null || isNullOrEmpty(creditGroup.getName())) {
            throw new RuntimeException("Group name are required.");
        }
    }
}
