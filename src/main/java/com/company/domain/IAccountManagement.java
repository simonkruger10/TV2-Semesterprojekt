package com.company.domain;

import com.company.common.IAccount;

import java.security.NoSuchAlgorithmException;

public interface IAccountManagement {
    IAccount[] search(String[] words);
    IAccount[] search(String[] words, int maxResults);

    IAccount[] getByName(String firstName);
    IAccount[] getByName(String firstName, String lastName);
    IAccount[] getByName(String firstName, String middleName, String lastName);

    IAccount getByEmail(String email);

    void login(String email, String password) throws NoSuchAlgorithmException;
    void logout();

    IAccount getCurrentUser();

    IAccount create(IAccount account, String password) throws NoSuchAlgorithmException;

    void update(IAccount account) throws NoSuchAlgorithmException;
    void update(IAccount account, String password) throws NoSuchAlgorithmException;
    void update(String email, String password) throws NoSuchAlgorithmException;
    void update(String email, IAccount account) throws NoSuchAlgorithmException;
    void update(String email, IAccount account, String password) throws NoSuchAlgorithmException;
}
