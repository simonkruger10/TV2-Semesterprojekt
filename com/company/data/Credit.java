package com.company.data;

public class Credit {
    private Creditgroup creditgroup;
    private String firstName;
    private String lastName;
    private String middleName;

    public Creditgroup getCreditgroup() {
        return creditgroup;
    }

    public void setCreditgroup(Creditgroup creditgroup) {
        this.creditgroup = creditgroup;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
