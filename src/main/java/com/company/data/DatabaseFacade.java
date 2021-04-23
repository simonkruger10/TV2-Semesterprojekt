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

    IProduction getProduction(String uuid);

    IProduction addProduction(IProduction production);

    void updateProduction(IProduction production);


    ICredit[] getCredits();

    ICredit getCredit(String uuid);

    ICredit addCredit(ICredit credit);

    void updateCredit(ICredit credit);


    ICreditGroup[] getCreditGroups();

    ICreditGroup getCreditGroup(String uuid);

    ICreditGroup addCreditGroup(ICreditGroup creditGroup);

    void updateCreditGroup(ICreditGroup creditGroup);


    IAccount[] getAccounts();

    IAccount getAccount(String uuid);

    IAccount addAccount(IAccount account, String hashedPassword);

    void updateAccount(IAccount account);

    void updateAccount(IAccount account, String hashedPassword);


    IAccount login(IAccount account, String password);
}
