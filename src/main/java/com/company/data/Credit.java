package com.company.data;

import com.company.crossInterfaces.CreditEntity;
import com.company.crossInterfaces.CreditGroupEntity;

public class Credit implements CreditEntity {
    private final int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private CreditGroupEntity creditGroup;

    public Credit(String firstName, String middleName, String lastName, CreditGroupEntity creditGroup) {
        this(-1, firstName, middleName, lastName, creditGroup);
    }

    public Credit(int id, String firstName, String lastName, String middleName, CreditGroupEntity creditGroup) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.creditGroup = creditGroup;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
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

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public CreditGroupEntity getCreditGroup() {
        return creditGroup;
    }

    @Override
    public void setCreditGroup(CreditGroupEntity creditGroup) {
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
