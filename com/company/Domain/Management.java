package com.company.Domain;

import com.company.crossInterfaces.CreditEntity;
import com.company.crossInterfaces.CreditGroupEntity;
import com.company.crossInterfaces.ProductionEntity;

public interface Management {
    default CreditManagement addProduction(ProductionEntity productionInfo) {
        return null;
    }

    default ProductionEntity getProduction(int id) {
        return null;
    }

    default CreditManagement addCredit(CreditEntity creditinfo, ProductionEntity production) {
        return null;
    }

    default CreditEntity getCredit(int id) {
        return null;
    }

    default CreditManagement addCreditGroup(CreditGroupEntity groupinfo) {
        return null;
    }

    default CreditGroupEntity getCreditGroup(int id) {
        return null;
    }
}
