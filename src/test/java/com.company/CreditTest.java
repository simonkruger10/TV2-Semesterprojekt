package com.company;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.data.Database;
import com.company.data.DatabaseFacade;
import com.company.domain.AccountManagement;
import com.company.domain.CreditManagement;
import com.company.domain.dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.AccessControlException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class CreditTest {

    private CreditManagement creditManagement;
    private Credit newCredit, existingCredit;
    private Production newProduction, existingProduction;
    private Producer existingProducer;
    boolean newCreditHasBeenAddedToTheDatabase;
    private DatabaseFacade dbInstance;

    @Before
    public void setUp() {
        creditManagement = new CreditManagement();
        newCreditHasBeenAddedToTheDatabase = false;
        dbInstance = Database.getInstance();

        newCredit = new Credit();
        newCredit.setFirstName("Bob");
        newCredit.setLastName("Marley");
        newCredit.setEmail("bob@marley.tld");
        newCredit.setImage("defaultCreditPerson.jpg");

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
        creditDupe.setImage("defaultCreditPerson.jpg");

        assertEquals(newCredit, creditDupe);

        creditDupe.setFirstName("");
        assertNotEquals(newCredit, creditDupe);
        creditDupe.setFirstName("Bob");
        assertEquals(newCredit, creditDupe);

        creditDupe.setLastName("");
        assertNotEquals(newCredit, creditDupe);
        creditDupe.setLastName("Marley");
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
     * Unit test af databasen's addCredit metode.
     */
    @Test
    public void addNewCredit() {
        //Assert that the credit IS NOT in the database
        ICredit[] allCredits = dbInstance.getCredits(500, 0);
        for (ICredit c : allCredits) {
            //This assumes that Credit.equals works as intended.
            assertNotEquals(c, newCredit);
        }

        //Add the credit to the database
        Credit returnedCredit = new Credit(dbInstance.addCredit(newCredit));
        newCreditHasBeenAddedToTheDatabase = true; //Used in @After to revert changed to database
        newCredit.setID(returnedCredit.getID()); //the equals method also compares id

        assertEquals(returnedCredit, newCredit);

        //Assert that the credit IS in the database
        allCredits = dbInstance.getCredits(500, 0);
        boolean creditFound = false;
        for (ICredit c : allCredits) {
            //This assumes that Credit.equals works as intended.
            if (newCredit.equals(new Credit(c))) {
                creditFound = true;
            }
        }
        assertTrue(creditFound);
    }

    /**
     * Unit test af databasen's updateCredit metode
     */
    @Test
    public void updateCreditValues() {
        //get the existing credit
        Credit dbCredit = new Credit(dbInstance.getCredit(1, CreditType.PERSON));
        assertEquals(dbCredit, existingCredit);

        //Update values
        existingCredit.setImage("test.png");
        existingCredit.setFirstName("Test");
        existingCredit.setLastName("Test");
        existingCredit.setEmail("test@system.tld");
        dbInstance.updateCredit(existingCredit);

        //Get the updated credit from the database, and assert that the values have changed.
        dbCredit = new Credit(dbInstance.getCredit(1, CreditType.PERSON));
        assertEquals(dbCredit, existingCredit);
        assertEquals(dbCredit.getImage(), "test.png");
        assertEquals(dbCredit.getFirstName(), "Test");
        assertEquals(dbCredit.getLastName(), "Test");
        assertEquals(dbCredit.getEmail(), "test@system.tld");

        //Clean up - change the values back to the backup.
        existingCredit.setFirstName("Mads");
        existingCredit.setLastName("Mikkelsen");
        existingCredit.setImage("oQBzMxGTMOIVrFGUqHnWK5lB61G.jpg");
        existingCredit.setEmail("mail_madsmikkelsen@system.tld");
        existingCredit.setID(1);
        dbInstance.updateCredit(existingCredit);

        //Assert that we haven't altered any data
        dbCredit = new Credit(dbInstance.getCredit(1, CreditType.PERSON));
        assertEquals(dbCredit, existingCredit);
    }

    @Test
    public void addNewCreditViaDomain() {
        //Assert Insufficient permission
        boolean hasRunException = false;
        try {
            creditManagement.create(newCredit);
        } catch (AccessControlException ace) {
            hasRunException = true;
            assertEquals("Insufficient permission.", ace.getMessage());
        }
        assertTrue(hasRunException); //Check that the exception was caught.
        hasRunException = false;

        //Assign admin permissions and check validity of credit argument.
        try {
            new AccountManagement().login("admin@system.tld", "admin");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            fail();
        }
        //Argument cannot be null
        try {
            creditManagement.create(null);
        } catch (AccessControlException ace){
            fail(); //We should pass the access check at this point.
        } catch (RuntimeException re) {
            assertEquals(re.getMessage(), "Credit cannot be null.");
            hasRunException = true;
        }
        assertTrue(hasRunException); //Check that the exception was caught.
        hasRunException = false;
        //Must have a name
        try {
            newCredit.setFirstName(null);
            creditManagement.create(newCredit);
        } catch (RuntimeException re) {
            assertEquals(re.getMessage(), "First name cannot be null or empty.");
            newCredit.setFirstName("Bob");
            hasRunException = true;
        }
        assertTrue(hasRunException); //Check that the exception was caught.
        hasRunException = false;
        //Must be assigned a CreditGroup
        try {
            //newCredit is not assigned a creditGroup in @Before
            creditManagement.create(newCredit);
        } catch (RuntimeException re) {
            assertEquals(re.getMessage(), "The person must be assigned a Credit Group.");
            hasRunException = true;
        }
        assertTrue(hasRunException); //Check that the exception was caught.
        hasRunException = false;

        CreditGroup testGroup = new CreditGroup();
        testGroup.setName("Medvirkende");
        testGroup.setID(1);
        newCredit.addCreditGroup(testGroup);

        try {
            ICredit dbCredit = creditManagement.create(newCredit);
            newCreditHasBeenAddedToTheDatabase = true;
            newCredit.setID(dbCredit.getID()); //Needed to call delete in @After
        } catch (Exception e) {
            e.printStackTrace();
            hasRunException = true;
        }
        assertFalse(hasRunException); //We should NOT have run into any errors this time.

        //Assert that the credit IS in the database
        ICredit[] allCredits = dbInstance.getCredits(500, 0);

        boolean creditFound = false;
        for (ICredit c : allCredits) {
            //This assumes that Credit.equals works as intended.
            if (newCredit.getID().equals(c.getID()) &&
                    newCredit.getFullName().equals(c.getFullName()) &&
                    newCredit.getEmail().equals(c.getEmail())) {
                creditFound = true;
            }
        }
        assertTrue(creditFound);
    }

    @After
    public void after() {
        if (newCreditHasBeenAddedToTheDatabase) {
            assertTrue(dbInstance.deleteCredit(newCredit.getID()));
            newCreditHasBeenAddedToTheDatabase = false;
        }
    }
}