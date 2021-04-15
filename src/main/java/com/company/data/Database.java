package com.company.data;

public interface Database {

    void getProductions();

    void addProduction(Production production);

    void getCredits(Production production);

    void addCredit(Production production);

    /**
     * Searches through the database for a matching username password pair.
     * @param username is saved as a string in the database
     * @param password is saved as a sha256, plaintext password should never be used as an argument here.
     * @return true if the username password pair exists.
     */ //Can maybe later return a security token instead of true
    boolean validLogin(String username, String password);


}
