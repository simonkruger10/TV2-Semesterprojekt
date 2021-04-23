package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.ICredit;
import com.company.common.IProduction;
import com.company.data.Database;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.*;

import static com.company.common.Tools.*;

public class CreditManagement implements ICreditManagement {
    private final AccountManagement aMgt = new AccountManagement();

    @Override
    public ICredit[] list() {
        return list(0, 20);
    }

    @Override
    public ICredit[] list(int start) {
        return list(start, 20);
    }

    @Override
    public ICredit[] list(int start, int max) {
        ICredit[] credits = Database.getInstance().getCredits();

        final List<ICredit> list = new ArrayList<>();
        for (int i = start; i < credits.length && list.size() < max; i++) {
            list.add(new CreditDTO(credits[i]));
        }

        return list.toArray(new ICredit[0]);
    }


    @Override
    public ICredit[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public ICredit[] search(String[] words, int maxResults) {
        final List<Pair<CreditDTO, Integer>> result = new ArrayList<>();

        for (ICredit credit : Database.getInstance().getCredits()) {
            // TODO: Investigate whether linear search is the right one to use
            int matchCount = 0;

            for (String word : words) {
                // TODO: Investigate whether linear search is the right one to use
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
                result.add(new Pair<>(new CreditDTO(credit), matchCount));

                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break;
                }
            }
        }

        return result.stream()                                      //Iterate
                .sorted((Comparator.comparing(Pair::getValue)))     //Sort after Value -> machCount
                .map(Pair::getKey)                                  //Convert Pair<Key, Value> to Key
                .toArray(ICredit[]::new);                           //Convert the List<Key> into Key[]
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

        for (ICredit credit : Database.getInstance().getCredits()) {
            // TODO: Investigate whether linear search is the right one to use
            if ((firstName == null || trueEquals(credit.getFirstName(), firstName))
                    && (middleName == null || trueEquals(credit.getMiddleName(), middleName))
                    && (lastName == null || trueEquals(credit.getLastName(), lastName))) {
                result.add(new CreditDTO(credit));
            }
        }

        return result.toArray(new ICredit[0]);
    }


    @Override
    public ICredit[] getByGroup(String groupName) {
        assert groupName != null;

        final List<CreditDTO> result = new ArrayList<>();

        for (ICredit credit : Database.getInstance().getCredits()) {
            // TODO: Investigate whether linear search is the right one to use
            if (credit.getCreditGroup().getName().equalsIgnoreCase(groupName)) {
                result.add(new CreditDTO(credit));
            }
        }

        return result.toArray(new ICredit[0]);
    }


    @Override
    public ICredit getByUUID(String uuid) {
        assert uuid != null;

        return new CreditDTO(Database.getInstance().getCredit(uuid));
    }


    @Override
    public ICredit create(ICredit credit) {
        controlsAccess();

        controlsRequirements(credit);

        // TODO: Check for duplicates

        credit = Database.getInstance().addCredit(credit);

        return new CreditDTO(credit);
    }


    @Override
    public void update(ICredit credit) {
        controlsAccess();

        if (getByUUID(credit.getUUID()) == null) {
            throw new RuntimeException("Could not find credit with specified uuid.");
        }

        controlsRequirements(credit);

        // TODO: Check for duplicates

        Database.getInstance().updateCredit(new CreditDTO(credit));
    }


    private void controlsAccess() {
        // TODO: Check the access is correct
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }
    }

    private void controlsRequirements(ICredit credit) {
        if (credit == null || isNullOrEmpty(credit.getFirstName())
                || credit.getCreditGroup() == null || credit.getCreditGroup() == null) {
            throw new RuntimeException("First name and credit group is required.");
        }
    }
}
