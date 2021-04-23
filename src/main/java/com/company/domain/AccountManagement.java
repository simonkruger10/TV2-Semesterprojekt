package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.IAccount;
import javafx.util.Pair;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AccessControlException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static com.company.common.Tools.*;

public class AccountManagement implements IAccountManagement {
    private final List<AccountDTO> accounts = new ArrayList<>();
    private static AccountDTO currentUser = new AccountDTO();
    private final String saltValue = "qOfzSKTYGNhmf4bT73ZMxmHe5C3FR756HANUIOmejTLs5PZb6mqAlVJPyOXeEwJ23NyySubPx51YILZWqdDG6BvB3XNYCJpw8HJXJ4Wh5lM8DcWiDqjnQRqeyf8nUshPmiDt38RDlQGQ";

    @Override
    public IAccount[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public IAccount[] search(String[] words, int maxResults) {
        final List<Pair<AccountDTO, Integer>> result = new ArrayList<>();

        for (AccountDTO account : accounts) {
            int matchCount = 0;

            for (String word : words) {
                if (trueContains(account.getFirstName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(account.getMiddleName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(account.getLastName(), word)) {
                    matchCount += 1;
                }
                if (trueContains(account.getEmail(), word)) {
                    matchCount += 1;
                }
            }

            if (matchCount > 0) {
                result.add(new Pair<>(account, matchCount));
                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break;
                }
            }
        }

        return result.stream()                                      //Iterate
                .sorted((Comparator.comparing(Pair::getValue)))     //Sort after Value -> machCount
                .map(Pair::getKey)                                  //Convert Pair<Key, Value> to Key
                .toArray(value -> new IAccount[0]);                 //Convert the List<Key> into Key[]
    }


    @Override
    public IAccount[] getByName(String firstName) {
        return getByName(firstName, null, null);
    }

    @Override
    public IAccount[] getByName(String firstName, String lastName) {
        return getByName(firstName, null, lastName);
    }

    @Override
    public IAccount[] getByName(String firstName, String middleName, String lastName) {
        assert firstName != null || middleName != null || lastName != null;

        final List<AccountDTO> result = new ArrayList<>();

        for (AccountDTO account : accounts) {
            if ((firstName == null || trueEquals(account.getFirstName(), firstName))
                    && (middleName == null || trueEquals(account.getMiddleName(), middleName))
                    && (lastName == null || trueEquals(account.getLastName(), lastName))) {
                result.add(account);
            }
        }

        return result.toArray(new IAccount[0]);
    }


    @Override
    public IAccount getByEmail(String email) {
        assert email != null;

        for (AccountDTO account : accounts) {
            if (account.getEmail().equalsIgnoreCase(email)) {
                return account;
            }
        }

        return null;
    }


    @Override
    public void login(String email, String password) throws NoSuchAlgorithmException {
        assert email != null;

        AccountDTO accountDTO = (AccountDTO) getByEmail(email);
        if (accountDTO == null || !accountDTO.getPassword().equalsIgnoreCase(hashPassword(password))) {
            throw new RuntimeException("Could not find the user.");
        }

        currentUser = accountDTO;
    }

    @Override
    public void logout() {
        currentUser = new AccountDTO();
    }


    @Override
    public IAccount getCurrentUser() {
        return currentUser;
    }


    @Override
    public IAccount create(IAccount account, String password) throws NoSuchAlgorithmException {
        if (!isAdmin()) {
            throw new AccessControlException("The user is not allowed to create accounts.");
        }

        if (hasRequirements(account) || isNullOrEmpty(password)) {
            throw new RuntimeException("First name, email, access level and password are required.");
        }

        if (getByEmail(account.getEmail()) != null) {
            throw new RuntimeException("The user already exists.");
        }

        AccountDTO newAccount = new AccountDTO();
        newAccount.setCopyOf(account);
        newAccount.setPassword(hashPassword(password));

        accounts.add(newAccount);

        return newAccount;
    }


    @Override
    public void update(IAccount account) throws NoSuchAlgorithmException {
        assert account != null;
        update(account.getEmail(), account, null);
    }

    @Override
    public void update(IAccount account, String password) throws NoSuchAlgorithmException {
        assert account != null;
        update(account.getEmail(), account, password);
    }

    @Override
    public void update(String email, String password) throws NoSuchAlgorithmException {
        assert email != null;
        update(email, null, password);
    }

    @Override
    public void update(String email, IAccount account) throws NoSuchAlgorithmException {
        update(email, account, null);
    }

    @Override
    public void update(String email, IAccount account, String password) throws NoSuchAlgorithmException {
        assert email != null;

        if (currentUser != getByEmail(email) && !isAdmin()) {
            throw new AccessControlException("The user is not allowed to update the account.");
        }

        AccountDTO accountDTO = (AccountDTO) getByEmail(email);
        if (accountDTO == null) {
            throw new RuntimeException("Could not find the account by the specified email address.");
        }

        if (account != null) {
            if (hasRequirements(account)) {
                throw new RuntimeException("First name, email and access level are required.");
            }

            String newEmail = account.getEmail();
            if (!trueEquals(newEmail, email) && getByEmail(newEmail) != null) {
                throw new RuntimeException("The email address is already in use.");
            }

            accountDTO.setCopyOf(account);
        }

        if (!isNullOrEmpty(password)) {
            accountDTO.setPassword(hashPassword(password));
        }
    }


    public boolean isAdmin() {
        return currentUser.getAccessLevel().equals(AccessLevel.ADMINISTRATOR);
    }

    private boolean hasRequirements(IAccount account) {
        assert account != null;

        AccessLevel accessLevel = account.getAccessLevel();
        return !isNullOrEmpty(account.getFirstName()) && !isNullOrEmpty(account.getEmail())
                && accessLevel != null && accessLevel.greater(AccessLevel.GUEST);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        assert password != null;

        password += saltValue;

        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(password.getBytes(StandardCharsets.UTF_8));

        return String.format("%0128x", new BigInteger(1, digest.digest()));
    }
}
