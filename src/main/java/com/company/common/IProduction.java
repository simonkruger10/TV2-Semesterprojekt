package com.company.common;

import java.io.File;

public interface IProduction {
    String getUUID();

    String getName();

    String getDescription();

    File getImage();

    void setName(String name);

    void setDescription(String description);

    void setImage(File image);
}
