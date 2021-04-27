package com.company.common;

import javafx.scene.image.Image;

public interface IProduction {
    String getUUID();

    String getName();

    String getDescription();

    Image getImage();

    ICredit[] getCredits();
}
