package com.company.data;

import com.company.common.IAccount;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.common.IProduction;

public interface DatabaseFacade {
    @SuppressWarnings("SameReturnValue")
    static DatabaseFacade getInstance() {
        return null;
    }

    boolean checkAccess();

    IProduction[] getProductions();

    IProduction getProduction(int id);

    void addProduction(IProduction productionInfo);

    void updateProduction(IProduction productionInfo);

    ICredit[] getCredits();

    ICredit getCredit(int id);

    void addCredit(ICredit creditInfo);

    void updateCredit(ICredit creditInfo);

    ICreditGroup[] getCreditGroups();

    ICreditGroup getCreditGroup(int id);

    void addCreditGroup(ICreditGroup creditGroupInfo);

    void updateCreditGroup(ICreditGroup creditGroupInfo);

    IAccount[] getAccounts();

    IAccount getAccount(int id);

    void addAccount(IAccount accountInfo);

    void updateAccount(IAccount accountInfo);

    /**
     * Searches through the database for a matching username password pair.
     *
     * @param email    is saved as a string in the database
     * @param password is saved as a sha256, plaintext password should never be used as an argument here.
     * @return true if the username password pair exists.
     */ //Can maybe later return a security token instead of true
    boolean login(String email, String password);
}
