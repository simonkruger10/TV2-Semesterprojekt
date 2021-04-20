package com.company.domain;

import com.company.crossInterfaces.ICredit;
import com.company.crossInterfaces.ICreditGroup;
import com.company.crossInterfaces.IProduction;

public interface Management {
    default CreditManagement addProduction(IProduction productionInfo) {
        return null;
    }

    default IProduction getProduction(int id) {
        return null;
    }

    default CreditManagement addCredit(ICredit creditinfo, IProduction production) {
        return null;
    }

    default ICredit getCredit(int id) {
        return null;
    }

    default CreditManagement addCreditGroup(ICreditGroup creditinfo) {
        return null;
    }

    default ICreditGroup getCreditGroup(int id) {
        return null;
    }
}
