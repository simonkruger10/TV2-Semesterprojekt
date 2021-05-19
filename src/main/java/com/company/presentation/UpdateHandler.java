package com.company.presentation;

import com.company.common.AccessLevel;

public interface UpdateHandler {
    boolean hasAccess(AccessLevel accessLevel);
    void update();
}
