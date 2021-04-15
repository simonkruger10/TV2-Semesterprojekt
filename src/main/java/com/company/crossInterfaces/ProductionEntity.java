package com.company.crossInterfaces;

import java.io.File;

public interface ProductionEntity {
    int getId();

    String getName();

    String getDescription();

    File getImage();

    void setName(String name);

    void setDescription(String description);

    void setImage(File image);
}
