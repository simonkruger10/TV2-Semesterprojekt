package com.company.data;

import com.company.common.*;

public interface DatabaseFacade {
    @SuppressWarnings("SameReturnValue")
    static DatabaseFacade getInstance() {
        return null;
    }


    IProducer[] searchProducers(String word);

    IProducer[] getProducers(Integer limit, Integer offset);

    IProducer getProducer(Integer id);

    IProducer addProducer(IProducer producer);

    void updateProducer(IProducer producer);


    Integer countProducers();

    IProduction[] searchProductions(String word);

    IProduction[] getProductions(Integer limit, Integer offset);

    IProduction[] getProductionByCredit(ICredit credit);

    IProduction[] getProductions(IProducer producer);

    IProduction getProduction(Integer id);

    IProduction addProduction(IProduction production);

    void updateProduction(IProduction production);


    Integer countProductions();

    ICredit[] searchCredits(String word);

    ICredit[] getCredits(Integer limit, Integer offset);

    ICredit[] getCredits(ICreditGroup creditGroup);

    ICredit getCredit(Integer id, CreditType type);

    ICredit addCredit(ICredit credit);

    void updateCredit(ICredit credit);

    boolean deleteCredit(Integer id);


    Integer countCredits();

    ICreditGroup[] searchCreditGroups(String word);

    ICreditGroup[] getCreditGroups(Integer limit, Integer offset);

    ICreditGroup getCreditGroup(Integer id);

    ICreditGroup addCreditGroup(ICreditGroup creditGroup);

    void updateCreditGroup(ICreditGroup creditGroup);


    Integer countCreditGroups();

    IAccount[] searchAccounts(String word);

    IAccount[] getAccounts(Integer limit, Integer offset);

    IAccount getAccount(Integer id);

    IAccount getAccount(String email);

    IAccount getAccount(String email, String hashedPassword);

    IAccount addAccount(IAccount account, String hashedPassword);

    void updateAccount(IAccount account);

    void updateAccount(IAccount account, String hashedPassword);

    Integer countAccounts();
}
