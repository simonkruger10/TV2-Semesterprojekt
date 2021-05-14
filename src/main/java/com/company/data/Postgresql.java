package com.company.data;

import com.company.common.*;
import com.company.data.DatabaseFacade;

public class Postgresql implements DatabaseFacade {
    @Override
    public boolean checkAccess() {
        return false;
    }


    @Override
    public IProducer[] getProducers() {
        return new IProducer[0];
    }

    @Override
    public IProducer getProducer(Integer id) {
        return null;
    }

    @Override
    public IProducer addProducer(IProducer producer) {
        return null;
    }

    @Override
    public void updateProducer(IProducer producer) {

    }


    @Override
    public IProduction[] getProductions() {
        return new IProduction[0];
    }

    @Override
    public IProduction getProduction(Integer id) {
        return null;
    }

    @Override
    public IProduction addProduction(IProduction production) {
        return null;
    }

    @Override
    public void updateProduction(IProduction production) {

    }


    @Override
    public ICredit[] getCredits() {
        return new ICredit[0];
    }

    @Override
    public ICredit getCredit(Integer uuid) {
        return null;
    }

    @Override
    public ICredit addCredit(ICredit credit) {
        return null;
    }

    @Override
    public void updateCredit(ICredit credit) {

    }


    @Override
    public ICreditGroup[] getCreditGroups() {
        return new ICreditGroup[0];
    }

    @Override
    public ICreditGroup getCreditGroup(Integer id) {
        return null;
    }

    @Override
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        return null;
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroup) {

    }


    @Override
    public IAccount[] getAccounts() {
        return new IAccount[0];
    }

    @Override
    public IAccount getAccount(Integer id) {
        return null;
    }

    @Override
    public IAccount addAccount(IAccount account, String hashedPassword) {
        return null;
    }

    @Override
    public void updateAccount(IAccount account) {

    }

    @Override
    public void updateAccount(IAccount account, String hashedPassword) {

    }


    @Override
    public IAccount login(IAccount account, String password) {
        return null;
    }
}
