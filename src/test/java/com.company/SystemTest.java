package com.company;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.common.IProduction;
import com.company.domain.CreditManagement;
import com.company.domain.ProductionManagement;
import com.company.domain.dto.CreditGroup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This SystemTest replaces the role of a GUI, and imitates the requests of a user to the domain.
 */
public class SystemTest {

    @Before
    public void setup() {

    }

    @Test
    public void navigateProductions() {
        //Request a list of productions
        IProduction[] productions = new ProductionManagement().list();
        assertThat("There are 20 productions in the database, and it should list all of them",
                productions.length, is(10));

        //Click on the first production
        int firstProductionId = productions[0].getID();
        IProduction firstProduction = new ProductionManagement().getByID(firstProductionId);
        assertThat("The first productions name is \"Druk\"",
                firstProduction.getName(), is("Druk"));
        assertThat("It was released the 24'th", firstProduction.getReleaseDay(), is(24));
        assertThat("It was released in September", firstProduction.getReleaseMonth(), is(9));
        assertThat("It was released in the year 2020", firstProduction.getReleaseYear(), is(2020));

        ICredit[] credits = firstProduction.getCredits();
        assertThat("The production is crediting 73 different people.", credits.length, is(73));

        //click on the first credit
        ICredit credit = credits[0];
        assertThat("Credit's name is \"Mads Mikkelsen\"",
                credit.getFullName(), is("Mads Mikkelsen"));
        assertThat("Credit's Email is \"mail_madsmikkelsen@system.tld\"",
                credit.getEmail(), is("mail_madsmikkelsen@system.tld"));
        CreditGroup creditGroup = new CreditGroup();
        creditGroup.setID(1);
        creditGroup.setName("Medvirkende");
        assertThat("Credit's CreditGroup is \"Medvirkende\"",credit.getCreditGroups()[0], is(creditGroup));

        //Check that the credit is listed with all of its assosiated productions.
        IProduction[] creditedFor = new ProductionManagement().getProductionByCredit(credit);
        assertThat(creditedFor.length == 0, is(false));
        assertThat(creditedFor[0].getName(), is("Druk"));
        assertThat(creditedFor[1].getName(), is("Flammen og Citronen"));
    }

    @After
    public void cleanup() {

    }
}
