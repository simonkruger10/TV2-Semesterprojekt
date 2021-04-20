package com.company.domain;

public interface ICreditManagement {
    CreditDTO createCredit(String firstName);
    CreditDTO createCredit(String firstName, String lastName);
    CreditDTO createCredit(String firstName, String middleName, String lastName);

    CreditDTO[] getCreditByName(String name);
    CreditDTO[] getCreditByName(String firstName, String lastName);
    CreditDTO[] getCreditByName(String firstName, String middleName, String lastName);
}
