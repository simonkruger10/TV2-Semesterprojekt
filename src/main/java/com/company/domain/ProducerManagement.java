package com.company.domain;

import com.company.common.IProducer;
import com.company.data.Database;
import com.company.domain.dto.Producer;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import static com.company.common.Tools.*;

public class ProducerManagement implements IProducerManagement {
    private final AccountManagement aMgt = new AccountManagement();

    @Override
    public IProducer[] list() {
        return list(0, 20);
    }

    @Override
    public IProducer[] list(int start) {
        return list(start, 20);
    }

    @Override
    public IProducer[] list(int start, int max) {
        IProducer[] producers = Database.getInstance().getProducers();

        final List<IProducer> list = new ArrayList<>();
        for (int i = start; i < producers.length && list.size() < max; i++) {
            list.add(new Producer(producers[i]));
        }

        return list.toArray(new IProducer[0]);
    }


    @Override
    public IProducer[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public IProducer[] search(String[] words, int maxResults) {
        final List<Producer> result = new ArrayList<>();

        for (IProducer producer : Database.getInstance().getProducers()) {
            // TODO: Investigate whether linear search is the right one to use
            for (String word : words) {
                // TODO: Investigate whether linear search is the right one to use
                if (trueContains(producer.getName(), word)) {
                    result.add(new Producer(producer));
                }
            }

            //TODO This might result in getting a few bad results, and never finding the the top X ones
            if (result.size() >= maxResults) {
                break;
            }
        }

        return result.toArray(IProducer[]::new);
    }


    @Override
    public IProducer getByName(String name) {
        assert name != null;

        for (IProducer producer : Database.getInstance().getProducers()) {
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


    private void controlsRequirements(IProducer producer) {
        if (producer == null || isNullOrEmpty(producer.getName())) {
            throw new RuntimeException("Name is required.");
        }
    }
}
