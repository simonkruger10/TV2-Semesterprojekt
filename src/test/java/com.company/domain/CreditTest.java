package com.company.domain;

import com.company.common.ICredit;
import com.company.common.IProduction;
import com.company.data.Database;
import com.company.data.PostgresCredit;
import com.company.domain.dto.Account;
import com.company.domain.dto.Credit;
import com.company.domain.dto.Producer;
import com.company.domain.dto.Production;
import org.checkerframework.checker.units.qual.C;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class CreditTest {

    private CreditManagement creditManagement;
    private Credit newCredit, existingCredit;
    private Production newProduction, existingProduction;
    private Producer existingProducer;

    @Before
    public void setUp() {
        creditManagement = new CreditManagement();
        newCredit = new Credit();
        newCredit.setFirstName("Bob");
        newCredit.setLastName("Marley");
        newCredit.setEmail("bob@marley.tld");

        existingCredit = new Credit();
        existingCredit.setFirstName("Mads");
        existingCredit.setLastName("Mikkelsen");
        existingCredit.setImage("oQBzMxGTMOIVrFGUqHnWK5lB61G.jpg");
        existingCredit.setEmail("mail_madsmikkelsen@system.tld");
        existingCredit.setID(1);

        existingProducer = new Producer();
        existingProducer.setID(1);
        existingProducer.setName("Zentropa Entertainments");
        existingProducer.setLogo(null);
        existingProducer.setAccount(new Account());

        newProduction = new Production();
        newProduction.setName("Production created from UnitTest");
        newProduction.setProducer(existingProducer);
        newProduction.setReleaseDay(1);
        newProduction.setReleaseMonth(1);
        newProduction.setReleaseYear(1);
        newProduction.setDescription("Production created from UnitTest");

        existingProduction = new Production();
        existingProduction.setID(1);
        existingProduction.setName("Druk");
        existingProduction.setReleaseDay(24);
        existingProduction.setReleaseMonth(9);
        existingProduction.setReleaseYear(2020);
        existingProduction.setImage("2GIHLUjKfGgrqoP5F1WxBwG62HO.jpg");
        existingProduction.setDescription(
                "Fire gymnasielærere og venner beslutter sig for at teste en teori om, " +
                        "at mennesket er født med en halv promille for lidt. Teorien lyder, " +
                        "at alkohol i blodet åbner sindet for omverdenen og får kreativiteten til at stige. " +
                        "Resultatet er opsigtsvækkende. Både undervisningen og resultaterne løfter sig, " +
                        "og vennerne begynder for alvor at mærke livet igen. " +
                        "I takt med at genstandene ryger indenbords, skrider eksperimentet fremad for nogle og " +
                        "af sporet for andre. Det bliver tydeligt, at alkohol kan skabe store resultater, " +
                        "men også at den slags vovemod kan have konsekvenser. Filmen er et drama om venskab, " +
                        "frihed, kærlighed – og alkohol.");

    }

    //An example of what a UNIT test case could look like.
    //However, since dto.Credit uses super.equals to check names and email,
    //it doesn't only test a single method from a single class.
    //This test is however essential to validate a negative assertion in "addNewCreditToExistingProduction"
    @Test
    public void creditEqualsWorks() {
        Credit creditDupe = new Credit();
        creditDupe.setFirstName("Bob");
        creditDupe.setLastName("Marley");
        creditDupe.setEmail("bob@marley.tld");

        assertEquals(newCredit, creditDupe);

        creditDupe.setFirstName("");
        assertNotEquals(newCredit, creditDupe);
        creditDupe.setFirstName("Bob");
        assertEquals(newCredit, creditDupe);

        creditDupe.setLastName("");
        assertNotEquals(newCredit, creditDupe);
        creditDupe.setLastName("Marley");
        assertEquals(newCredit, creditDupe);

        creditDupe.setMiddleName("HMS");
        assertNotEquals(newCredit, creditDupe);
        creditDupe.setMiddleName(null);
        assertEquals(newCredit, creditDupe);

        creditDupe.setEmail("lolz");
        assertNotEquals(newCredit, creditDupe);
        creditDupe.setEmail("bob@marley.tld");
        assertEquals(newCredit, creditDupe);

        //Could also test for Image, ID, CreditGroups
    }

    //After not knowing why "addNewCreditToExistingProduction" failed, I figured out
    //That the constructor taking an ICredit did not copy the Email field.
    // So I added a test to make sure we never forgot to include fields ad we add them.
    @Test
    public void constructCopy() {
        Credit dupe = new Credit(existingCredit);
        assertEquals(dupe, existingCredit);
    }

    /**
     * Assumes that Postgres.getProduction(Interger id) works correctly.
     */
    @Test
    public void addNewCreditToExistingProduction() {
        //Assert that the credit IS NOT in the database
        ICredit[] allCredits = Database.getInstance().getCredits();
        for (ICredit c : allCredits) {
            //This assumes that Credit.equals works as intended.
            assertNotEquals(c, newCredit);
        }
        //And that the credit is not attached to the production.
        IProduction productionFromDatabase = Database.getInstance().getProduction(1);
        for (ICredit c : productionFromDatabase.getCredits()) {
            assertNotEquals(c, newCredit);
        }

        //Add the credit to the database
        Credit returnedCredit = new Credit(Database.getInstance().addCredit(newCredit));
        newCredit.setID(returnedCredit.getID()); //the equals method also compares id

        assertEquals(returnedCredit, newCredit);

        //Assert that the credit IS in the database
        allCredits = Database.getInstance().getCredits();
        boolean creditFound = false;
        for (ICredit c : allCredits) {
            //This assumes that Credit.equals works as intended.
            if (newCredit.equals(new Credit(c))) {
                creditFound = true;
            }
        }
        assertTrue(creditFound);

    }

    @After
    public void after() {
        //PostgresCredit postgresCredit = new PostgresCredit();
        //postgresCredit.deleteCredit(newCredit.getID());
    }


}