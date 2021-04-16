package com.company.crossInterfaces;

public interface AccountEntity {
    int getId();

    default String getFullName() {
        String fullName = "";

        if (getFirstName() != null) {
            fullName = getFirstName();
        }
        if (getMiddleName() != null && !getMiddleName().trim().isEmpty()) {
            fullName += " " + getMiddleName();
        }
        if (getLastName() != null) {
            fullName = " " + getLastName();
        }

        return fullName.trim();
    }

    String getEmail();

    int getAccessLevel();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setMiddleName(String middleName);

    void setEmail(String email);

    void setAccessLevel(int accessLevel);
}
