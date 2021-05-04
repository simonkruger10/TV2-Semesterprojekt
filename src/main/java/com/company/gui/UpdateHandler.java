package com.company.gui;

import com.company.common.AccessLevel;

public interface UpdateHandler {
    boolean hasAccess(AccessLevel accessLevel);
    void update();
}
