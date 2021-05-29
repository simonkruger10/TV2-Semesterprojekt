package com.company.domain;

import com.company.common.IProducer;
import com.company.data.Database;
import com.company.domain.dto.Producer;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import static com.company.common.Tools.isNullOrEmpty;
import static com.company.common.Tools.trueEquals;

public class ProducerManagement implements IProducerManagement {
    private final AccountManagement aMgt = new AccountManagement();

    @Override
    public IProducer[] list() {
        return list(0, 10);
    }

    @Override
    public IProducer[] list(int start) {
        return list(start, 10);
    }

    @Override
    public IProducer[] list(int start, int max) {
        final List<IProducer> list = new ArrayList<>();

        for (IProducer producer: Database.getInstance().getProducers(max, start)) {
            list.add(new Producer(producer));
        }

        return list.toArray(new IProducer[0]);
    }


    @Override
    public IProducer[] search(String[] words) {
        return search(words, 10);
    }

    @Override
    public IProducer[] search(String[] words, int maxResults) {
        final List<Producer> result = new ArrayList<>();

        onMaxResults:
        for (String word : words) {
            // TODO: Investigate whether linear search is the right one to use
            for (IProducer producer : Database.getInstance().searchProducers(word)) {
                // TODO: Investigate whether linear search is the right one to use
                result.add(new Producer(producer));

                if (result.size() >= maxResults) {
                    break onMaxResults;
                }
            }
        }

        return result.toArray(IProducer[]::new);
    }


    @Override
    public IProducer getByName(String name) {
        assert name != null;

        for (IProducer producer : Database.getInstance().searchProducers(name)) {
            // TODO: Investigate whether linear search is the right one to use
            if (trueEquals(producer.getName(), name)) {
                return new Producer(producer);
            }
        }
        return null;
    }


    @Override
    public IProducer getByID(Integer id) {
        assert id != null;

        return new Producer(Database.getInstance().getProducer(id));
    }


    @Override
    public IProducer create(IProducer producer) {
        // TODO: Is it only administrators that can create new a producer?
        if (!aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        controlsRequirements(producer);

        // TODO: Check for duplicates

        producer = Database.getInstance().addProducer(producer);

        return new Producer(producer);
    }


    @Override
    public void update(IProducer producer) {
        controlsRequirements(producer);

        if (getByID(producer.getID()) == null) {
            throw new RuntimeException("Could not find producer with specified id.");
        }

        // Producer can change own producer information and administrators can change all producers information
        if (!aMgt.getCurrentUser().getID().equals(producer.getAccount().getID()) && !aMgt.isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        // TODO: Check for duplicates

        Database.getInstance().updateProducer(new Producer(producer));
    }

    @Override
    public Integer count() {
        return Database.getInstance().countProducers();
    }


    private void controlsRequirements(IProducer producer) {
        if (producer == null || isNullOrEmpty(producer.getName()) || isNullOrEmpty(producer.getLogo())) {
            throw new RuntimeException("Name is required.");
        }
    }
}
