package com.company.data;

import com.company.common.*;

public interface DatabaseFacade {
    @SuppressWarnings("SameReturnValue")
    static DatabaseFacade getInstance() {
        return null;
    }

    boolean checkAccess();


    IProducer[] getProducers();

    IProducer getProducer(Integer id);

    IProducer addProducer(IProducer producer);

    void updateProducer(IProducer producer);


    IProduction[] getProductions();

    IProduction getProduction(Integer id);

    IProduction addProduction(IProduction production);

    void updateProduction(IProduction production);


    ICredit[] getCredits();

    ICredit getCredit(Integer uuid);

    ICredit addCredit(ICredit credit);

    void updateCredit(ICredit credit);


    ICreditGroup[] getCreditGroups();

    ICreditGroup getCreditGroup(Integer id);

    ICreditGroup addCreditGroup(ICreditGroup creditGroup);

    void updateCreditGroup(ICreditGroup creditGroup);


    IAccount[] getAccounts();

    IAccount getAccount(Integer id);

    IAccount addAccount(IAccount account, String hashedPassword);

    void updateAccount(IAccount account);

    void updateAccount(IAccount account, String hashedPassword);


    IAccount login(IAccount account, String password);
}
