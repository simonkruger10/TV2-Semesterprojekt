package com.company.data;

import com.company.crossInterfaces.AccountEntity;
import com.company.crossInterfaces.CreditEntity;
import com.company.crossInterfaces.CreditGroupEntity;
import com.company.crossInterfaces.ProductionEntity;

public interface DatabaseFacade {
    @SuppressWarnings("SameReturnValue")
    static DatabaseFacade getInstance() {
        return null;
    }

    boolean checkAccess();

    ProductionEntity[] getProductions();

    ProductionEntity getProduction(int id);

    void addProduction(ProductionEntity productionInfo);

    void updateProduction(ProductionEntity productionInfo);

    CreditEntity[] getCredits();

    CreditEntity getCredit(int id);

    void addCredit(CreditEntity creditInfo);

    void updateCredit(CreditEntity creditInfo);

    CreditGroupEntity[] getCreditGroups();

    CreditGroupEntity getCreditGroup(int id);

    void addCreditGroup(CreditGroupEntity creditGroupInfo);

    void updateCreditGroup(CreditGroupEntity creditGroupInfo);

    AccountEntity[] getAccounts();

    AccountEntity getAccount(int id);

    void addAccount(AccountEntity accountInfo);

    void updateAccount(AccountEntity accountInfo);

    /**
     * Searches through the database for a matching username password pair.
     *
     * @param email    is saved as a string in the database
     * @param password is saved as a sha256, plaintext password should never be used as an argument here.
     * @return true if the username password pair exists.
     */ //Can maybe later return a security token instead of true
    boolean login(String email, String password);
}
