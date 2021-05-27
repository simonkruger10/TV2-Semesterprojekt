package com.company.domain.dto;

import com.company.common.IAccount;
import com.company.common.IProducer;

public class Producer extends Identifier implements IProducer {
    private String name = null;
    private String logo = null;
    private IAccount account = null;

    public Producer() {
    }

    public Producer(IProducer producer) {
        this.setCopyOf(producer);
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    @Override
    public IAccount getAccount() {
        return account;
    }

    public void setAccount(IAccount account) {
        this.account = account;
    }


    public void setCopyOf(IProducer producer) {
        assert producer != null;

        this.setID(producer.getID());
        this.setName(producer.getName());
        this.setLogo(producer.getLogo());
        if (producer.getAccount() != null) {
            this.setAccount(new Account(producer.getAccount()));
        }
    }
}
