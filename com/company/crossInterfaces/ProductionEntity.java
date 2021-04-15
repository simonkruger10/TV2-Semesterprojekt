package com.company.crossInterfaces;

import com.company.data.Credit;

import java.io.File;

public interface ProductionEntity {
    int gedId();
    String getName();
    String getDescription();
    File getImage();
    String setName();
    String setDescription();
    File setImage();
}
