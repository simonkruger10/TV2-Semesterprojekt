package com.company.common;

import java.io.File;

public interface IProduction {
    String getUUID();

    String getName();

    String getDescription();

    File getImage();

    ICredit[] getCredits();
}
