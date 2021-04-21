package com.company.domain;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreditManagement implements ICreditManagement {
    private final List<CreditDTO> credits = new ArrayList<>();

    @Override
    public ICredit create(String firstName, ICreditGroup creditGroup) {
        CreditDTO account = new CreditDTO(firstName, creditGroup);
        credits.add(account);
        return account;
    }

    @Override
    public ICredit create(String firstName, String lastName, ICreditGroup creditGroup) {
        CreditDTO account = new CreditDTO(firstName, lastName, creditGroup);
        credits.add(account);
        return account;
    }

    @Override
    public ICredit create(String firstName, String middleName, String lastName, ICreditGroup creditGroup) {
        CreditDTO account = new CreditDTO(firstName, middleName, lastName, creditGroup);
        credits.add(account);
        return account;
    }


    @Override
    public ICredit[] search(String[] words) {
        final List<CreditDTO> result = new ArrayList<>();

        for (CreditDTO credit: credits) {
            for (String word: words) {
                if (credit.getFullName().contains(word) || credit.getCreditGroup().getName().contains(word)) {
                    result.add(credit);
                    break;
                }
            }
        }

        return result.toArray(new ICredit[0]);
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
        final List<CreditDTO> result = new ArrayList<>();

        for (CreditDTO credit: credits) {
            if (Objects.equals(credit.getFirstName(), firstName)
                    && (middleName == null || Objects.equals(credit.getMiddleName(), middleName))
                    && (lastName == null || Objects.equals(credit.getMiddleName(), lastName))) {
                result.add(credit);
            }
        }

        return result.toArray(new ICredit[0]);
    }


    @Override
    public ICredit[] getByGroup(String groupName) {
        final List<CreditDTO> result = new ArrayList<>();

        for (CreditDTO credit: credits) {
            if (Objects.equals(credit.getCreditGroup().getName(), groupName)) {
                result.add(credit);
            }
        }

        return result.toArray(new ICredit[0]);    }
}
