package com.company.crossInterfaces;

import com.company.data.Credit;
import java.io.File;

public interface ProductionEntity {
    String getName();
    String getDescription();
    File getImage();
    Credit[] getCredits();
    int gedId();
}
