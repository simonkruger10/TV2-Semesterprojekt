package com.company.data;

public class Credit {


    private Creditgroup creditgroup;
    private String firstName;
    private String lastName;
    private String middleName;
    private int personID;

    public Credit(Creditgroup creditgroup, String firstName, String middleName, String lastName, int personID) {
        this.creditgroup = creditgroup;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.personID = personID;
    }

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

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditgroup=" + creditgroup +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", personID=" + personID +
                '}';
    }

    public String toJSONString() {
        return "{" +
                "\"creditgroup\": " + "\"" + creditgroup + "\", "+
                "\"firstName\": " + "\"" + firstName + "\", " +
                "\"lastName\": " + "\"" + lastName + "\", " +
                "\"middleName\": " + "\"" + middleName + "\", " +
                "\"personID\": " + "\"" + personID + "\"" +
                "}";
    }
}
