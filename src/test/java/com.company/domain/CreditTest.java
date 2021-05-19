package com.company.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class CreditTest {

    private CreditManagement creditManagement;

    //Create simple credits: nonDupeCredit, dupeCredit.
    //Create productions: actualProduction, newProduction


    @Before
    void setUp() {
        creditManagement = new CreditManagement();
        //Init credits and productions

    }

    @Test
    void addNewCreditToExistingProduction() {
        //Assert that the credit IS NOT in the database

        //Add the credit to the database

        //Assert that the credit IS in the database
    }

    @After
    void after() {
        //Make sure that the database is reset and not altered by this test class.
    }


}