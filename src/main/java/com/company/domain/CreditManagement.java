package com.company.domain;

public class CreditManagement implements ICreditManagement {
    @Override
    public CreditDTO createCredit(String firstName) {
        return null;
    }

    @Override
    public CreditDTO createCredit(String firstName, String lastName) {
        return null;
    }

    @Override
    public CreditDTO createCredit(String firstName, String middleName, String lastName) {
        return null;
    }

    @Override
    public CreditDTO[] getCreditByName(String name) {
        return new CreditDTO[0];
    }

    @Override
    public CreditDTO[] getCreditByName(String firstName, String lastName) {
        return new CreditDTO[0];
    }

    @Override
    public CreditDTO[] getCreditByName(String firstName, String middleName, String lastName) {
        return new CreditDTO[0];
    }
}
