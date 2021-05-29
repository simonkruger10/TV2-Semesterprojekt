package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.data.Database;
import com.company.domain.dto.Credit;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.company.common.Tools.*;

public class CreditManagement implements ICreditManagement {
    private final AccountManagement aMgt = new AccountManagement();

    @Override
    public ICredit[] list() {
        return list(0, 10);
    }

    @Override
    public ICredit[] list(int start) {
        return list(start, 10);
    }

    @Override
    public ICredit[] list(int start, int max) {
        final List<ICredit> list = new ArrayList<>();

        for (ICredit credit: Database.getInstance().getCredits(max, start)) {
            list.add(new Credit(credit));
        }

        return list.toArray(new ICredit[0]);
    }


    @Override
    public ICredit[] search(String[] words) {
        return search(words, 10);
    }

    @Override
    public ICredit[] search(String[] words, int maxResults) {
        final List<Pair<Credit, Integer>> result = new ArrayList<>();

        onMaxResults:
        for (String word : words) {
            // TODO: Investigate whether linear search is the right one to use
            int matchCount = 0;

            for (ICredit credit : Database.getInstance().searchCredits(word)) {
                // TODO: Investigate whether linear search is the right one to use
                if (credit.getType().equals(CreditType.PERSON)) {
                    if (trueContains(credit.getFirstName(), word)) {
                        matchCount += 1;
                    }
                    if (trueContains(credit.getLastName(), word)) {
                        matchCount += 1;
                    }
                }

                result.add(new Pair<>(new Credit(credit), matchCount));

                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break onMaxResults;
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
        return getByName(firstName, null);
    }

    @Override
    public ICredit[] getByName(String firstName, String lastName) {
        assert firstName != null || lastName != null;

        final List<Credit> result = new ArrayList<>();

        String searchWord = firstName;
        if (searchWord == null) {
            searchWord = lastName;
        }

        for (ICredit credit : Database.getInstance().searchCredits(searchWord)) {
            // TODO: Investigate whether linear search is the right one to use
            if ((firstName == null || trueEquals(credit.getFirstName(), firstName))
                    && (lastName == null || trueEquals(credit.getLastName(), lastName))) {
                result.add(new Credit(credit));
            }
        }

        return result.toArray(new ICredit[0]);
    }

    @Override
    public ICredit getByID(Integer id, CreditType type) {
        assert id != null || type != null;

        return new Credit(Database.getInstance().getCredit(id, type));
    }

    @Override
    public ICredit[] getByCreditGroup(ICreditGroup creditGroup) {
        final List<ICredit> credits = new ArrayList<>();
        for (ICredit credit: Database.getInstance().getCredits(creditGroup)) {
            credits.add(new Credit(credit));
        }
        return credits.toArray(new ICredit[0]);
    }


    @Override
    public ICredit create(ICredit credit) {
        // TODO: These access requirements are incorrect. A producer can only create a credit when the producer
        //       create or update own production. Solution: implement an overloading method of create method
        //       with augment production: IProduction
        controlsAccess();

        controlsRequirements(credit);

        // TODO: Check for duplicates

        credit = Database.getInstance().addCredit(credit);

        return new Credit(credit);
    }


    @Override
    public void update(ICredit credit) {
        // TODO: These access requirements are incorrect. A producer can only update a credit when the producer
        //       create or update own production. Solution: implement an overloading method of update method
        //       with augment production: IProduction
        controlsAccess();

        if (credit.getID() == null || getByID(credit.getID(), credit.getType()) == null) {
            throw new RuntimeException("Could not find credit with specified id.");
        }

        controlsRequirements(credit);

        // TODO: Check for duplicates

        Database.getInstance().updateCredit(new Credit(credit));
    }

    @Override
    public Integer count() {
        return Database.getInstance().countCredits();
    }


    private void controlsAccess() {
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }
    }

    private void controlsRequirements(ICredit credit) {
        if (credit == null) {
            throw new RuntimeException("Credit cannot be null.");
        }
        if (credit.getType() == null) {
            throw new RuntimeException("Credit type cannot be null.");
        }
        if (credit.getType() == CreditType.UNIT) {
            if (isNullOrEmpty(credit.getName())) {
                throw new RuntimeException("Name cannot be null or empty.");
            }
        } else {
            if (isNullOrEmpty(credit.getFirstName())) {
                throw new RuntimeException("First name cannot be null or empty.");
            }
            if (isNullOrEmpty(credit.getLastName())) {
                throw new RuntimeException("Last name cannot be null or empty.");
            }
            if (isNullOrEmpty(credit.getImage())) {
                throw new RuntimeException("Image cannot be null or empty.");
            }
        }
        if (credit.getCreditGroups().length == 0) {
            throw new RuntimeException("The person must be assigned a Credit Group.");
        }
    }
}
