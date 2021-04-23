package com.company.domain;

import com.company.common.ICredit;
import com.company.common.IProduction;
import javafx.util.Pair;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.company.tools.*;

public class ProductionManagement implements IProductionManagement {
    private final AccountManagement aMgt = new AccountManagement();
    private final CreditManagement cMgt = new CreditManagement();
    private final List<ProductionDTO> productions = new ArrayList<>();

    @Override
    public IProduction[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public IProduction[] search(String[] words, int maxResults) {
        final List<Pair<ProductionDTO, Integer>> result = new ArrayList<>();

        for (ProductionDTO production : productions) {
            int matchCount = 0;

            for (String word : words) {
                if (trueContains(production.getName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(production.getDescription(), word)) {
                    matchCount += 1;
                }
            }

            if (matchCount > 0) {
                result.add(new Pair<>(production, matchCount));

                if (result.size() >= maxResults) {
                    break;
                }
            }
        }

        result.sort(new Comparator<Pair<ProductionDTO, Integer>>() {
            @Override
            public int compare(Pair<ProductionDTO, Integer> pair1, Pair<ProductionDTO, Integer> pair2) {
                // -1 - less than, 1 - greater than, 0 - equal
                return pair1.getValue().compareTo(pair2.getValue());
            }
        });

        IProduction[] list = new IProduction[result.size()];
        for (int i = 0; i < result.size(); i++) {
            list[i] = result.get(i).getKey();
        }
        return list;
    }


    @Override
    public IProduction[] getByName(String name) {
        assert name != null;

        final List<IProduction> result = new ArrayList<>();

        for (IProduction production : productions) {
            if (trueEquals(production.getName(), name)) {
                result.add(production);
            }
        }

        return result.toArray(new IProduction[0]);
    }


    @Override
    public IProduction getByUUID(String uuid) {
        assert uuid != null;

        for (IProduction production : productions) {
            if (production.getUUID().equals(uuid)) {
                return production;
            }
        }

        return null;
    }


    @Override
    public IProduction create(IProduction production) {
        controlsAccess();

        controlsRequirements(production);

        ProductionDTO newProduction = new ProductionDTO();
        newProduction.copyProduction(production);

        productions.add(newProduction);

        return newProduction;
    }


    @Override
    public void update(IProduction production) {
        assert production != null;

        update(production.getUUID(), production);
    }

    @Override
    public void update(String uuid, IProduction production) {
        controlsAccess();

        controlsRequirements(production);

        ProductionDTO productionDTO = (ProductionDTO) getByUUID(uuid);
        if (productionDTO == null) {
            throw new RuntimeException("Could not the credit by the specified uuid.");
        }
        productionDTO.copyProduction(production);
    }


    private void controlsAccess() {
        // TODO: Hvem har adgang???
        if (aMgt.getCurrentUser().getAccessLevel() != AccessLevel.PRODUCER && !aMgt.isAdmin()) {
            throw new AccessControlException("The user is not allowed to create production.");
        }
    }

    private void controlsRequirements(IProduction production) {
        if (production == null || isNullOrEmpty(production.getName()) || production.getCredits() == null) {
            throw new RuntimeException("Production name and one credit are required.");
        }

        if (!(production.getCredits() instanceof CreditDTO[])) {
            throw new RuntimeException("The credit list must be an instance of the CreditDTO class");
        }
    }
}
