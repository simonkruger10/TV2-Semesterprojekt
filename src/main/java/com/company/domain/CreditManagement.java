package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.ICredit;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.company.common.Tools.*;

public class CreditManagement implements ICreditManagement {
    private final List<CreditDTO> credits = new ArrayList<>();
    private final AccountManagement aMgt = new AccountManagement();

    @Override
    public ICredit[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public ICredit[] search(String[] words, int maxResults) {
        final List<Pair<CreditDTO, Integer>> result = new ArrayList<>();

        for (CreditDTO credit : credits) {
            int matchCount = 0;

            for (String word : words) {
                if (trueContains(credit.getFirstName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(credit.getMiddleName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(credit.getLastName(), word)) {
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

        result.sort(new Comparator<Pair<CreditDTO, Integer>>() {
            @Override
            public int compare(Pair<CreditDTO, Integer> pair1, Pair<CreditDTO, Integer> pair2) {
                // -1 - less than, 1 - greater than, 0 - equal
                return pair1.getValue().compareTo(pair2.getValue());
            }
        });

        ICredit[] list = new ICredit[result.size()];
        for (int i = 0; i < result.size(); i++) {
            list[i] = result.get(i).getKey();
        }
        return list;
    }


    @Override
    public ICredit[] getByName(String firstName) {
        return getByName(firstName, null, null);
    }

    @Override
    public ICredit[] getByName(String firstName, String lastName) {
        return getByName(firstName, lastName, null);
    }

    @Override
    public ICredit[] getByName(String firstName, String middleName, String lastName) {
        assert firstName != null || middleName != null || lastName != null;

        final List<CreditDTO> result = new ArrayList<>();

        for (CreditDTO credit : credits) {
            if ((firstName == null || trueEquals(credit.getFirstName(), firstName))
                    && (middleName == null || trueEquals(credit.getMiddleName(), middleName))
                    && (lastName == null || trueEquals(credit.getLastName(), lastName))) {
                result.add(credit);
            }
        }

        return result.toArray(new ICredit[0]);
    }


    @Override
    public ICredit[] getByGroup(String groupName) {
        assert groupName != null;

        final List<CreditDTO> result = new ArrayList<>();

        for (CreditDTO credit : credits) {
            if (credit.getCreditGroup().getName().equalsIgnoreCase(groupName)) {
                result.add(credit);
            }
        }

        return result.toArray(new ICredit[0]);
    }


    @Override
    public ICredit getByUUID(String uuid) {
        assert uuid != null;

        for (CreditDTO credit : credits) {
            if (credit.getUUID().equals(uuid)) {
                return credit;
            }
        }

        return null;
    }


    @Override
    public ICredit create(ICredit credit) {
        controlsAccess();

        controlsRequirements(credit);

        CreditDTO newCredit = new CreditDTO();
        newCredit.setCopyOf(credit);

        credits.add(newCredit);

        return newCredit;
    }


    @Override
    public void update(ICredit credit) {
        assert credit != null;

        update(credit.getUUID(), credit);
    }

    @Override
    public void update(String uuid, ICredit credit) {
        controlsAccess();

        controlsRequirements(credit);

        CreditDTO creditDTO = (CreditDTO) getByUUID(uuid);
        if (creditDTO == null) {
            throw new RuntimeException("Could not the credit by the specified uuid.");
        }
        creditDTO.setCopyOf(credit);
    }


    private void controlsAccess() {
        // TODO: Hvem har adgang???
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("The user is not allowed to create credits.");
        }
    }

    private void controlsRequirements(ICredit credit) {
        if (credit == null || isNullOrEmpty(credit.getFirstName()) || credit.getCreditGroup() == null) {
            throw new RuntimeException("First name and credit group are required.");
        }

        if (!(credit.getCreditGroup() instanceof CreditDTO)) {
            throw new RuntimeException("The credit group must be an instance of the CreditDTO class.");
        }
    }
}
