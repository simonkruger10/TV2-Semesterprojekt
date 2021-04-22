package com.company.data;

import com.company.common.ICreditGroup;

public class CreditEntity {
    private final int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private ICreditGroup creditGroup;

    public CreditEntity(String firstName, String middleName, String lastName, ICreditGroup creditGroup) {
        this(-1, firstName, middleName, lastName, creditGroup);
    }

    public CreditEntity(int id, String firstName, String lastName, String middleName, ICreditGroup creditGroup) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.creditGroup = creditGroup;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        String fullName = "";

        if (firstName != null) {
            fullName = firstName + " ";
        }
        if (middleName != null && !middleName.trim().isEmpty()) {
            fullName += middleName + " ";
        }
        if (lastName != null) {
            fullName = lastName;
        }

        return fullName.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ICreditGroup getCreditGroup() {
        return creditGroup;
    }

    public void setCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = creditGroup;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditGroup=" + creditGroup +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", personID=" + id +
                '}';
    }

    public String toJSONString() {
        return "{" +
                "\"creditGroup\": " + "\"" + creditGroup + "\", " +
                "\"firstName\": " + "\"" + firstName + "\", " +
                "\"lastName\": " + "\"" + lastName + "\", " +
                "\"middleName\": " + "\"" + middleName + "\", " +
                "\"personID\": " + "\"" + id + "\"" +
                "}";
    }
}
