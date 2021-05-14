package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.IAccount;
import com.company.data.Database;
import com.company.domain.descriptions.Account;
import javafx.util.Pair;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AccessControlException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.company.common.Tools.*;

public class AccountManagement implements IAccountManagement {
    private static Account currentUser = new Account();
    @SuppressWarnings({"FieldCanBeLocal", "SpellCheckingInspection"})
    private final String saltValue = "qOfzSKTYGNhmf4bT73ZMxmHe5C3FR756HANUIOmejTLs5PZb6mqAlVJPyOXeEwJ23NyySubPx51YILZWqdDG6BvB3XNYCJpw8HJXJ4Wh5lM8DcWiDqjnQRqeyf8nUshPmiDt38RDlQGQ";

    @Override
    public IAccount[] list() {
        return list(0, 20);
    }

    @Override
    public IAccount[] list(int start) {
        return list(start, 20);
    }

    @Override
    public IAccount[] list(int start, int max) {
        IAccount[] accounts = Database.getInstance().getAccounts();

        final List<IAccount> list = new ArrayList<>();
        for (int i = start; i < accounts.length && list.size() < max; i++) {
            list.add(new Account(accounts[i]));
        }

        return list.toArray(new IAccount[0]);
    }


    @Override
    public IAccount[] search(String[] words) {
        return search(words, 20);
    }

    @Override
    public IAccount[] search(String[] words, int maxResults) {
        final List<Pair<Account, Integer>> result = new ArrayList<>();

        for (IAccount account : Database.getInstance().getAccounts()) {
            // TODO: Investigate whether linear search is the right one to use
            int matchCount = 0;

            for (String word : words) {
                // TODO: Investigate whether linear search is the right one to use
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
                result.add(new Pair<>(new Account(account), matchCount));

                //TODO This might result in getting a few bad results, and never finding the the top X ones
                if (result.size() >= maxResults) {
                    break;
                }
            }
        }

        return result.stream()                                      //Iterate
                .sorted((Comparator.comparing(Pair::getValue)))     //Sort after Value -> machCount
                .map(Pair::getKey)                                  //Convert Pair<Key, Value> to Key
                .toArray(IAccount[]::new);                          //Convert the List<Key> into Key[]
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

        final List<Account> result = new ArrayList<>();

        for (IAccount account : Database.getInstance().getAccounts()) {
            if ((firstName == null || trueEquals(account.getFirstName(), firstName))
                    && (middleName == null || trueEquals(account.getMiddleName(), middleName))
                    && (lastName == null || trueEquals(account.getLastName(), lastName))) {
                result.add(new Account(account));
            }
        }

        return result.toArray(new IAccount[0]);
    }


    @Override
    public IAccount getByEmail(String email) {
        assert email != null;

        for (IAccount account : Database.getInstance().getAccounts()) {
            if (account.getEmail().equalsIgnoreCase(email)) {
                return new Account(account);
            }
        }

        return null;
    }


    @Override
    public IAccount getByID(Integer id) {
        assert id != null;

        return new Account(Database.getInstance().getAccount(id));
    }


    @Override
    public void login(String email, String password) throws NoSuchAlgorithmException {
        IAccount account = getByEmail(email);
        if (account == null) {
            throw new RuntimeException("Could not find the user");
        }
        account = Database.getInstance().login(account, hashPassword(password));
        if (account == null) {
            throw new RuntimeException("Could not find the user");
        }

        currentUser = new Account(account);
    }

    @Override
    public void logout() {
        currentUser = new Account();
    }


    @Override
    public IAccount getCurrentUser() {
        return currentUser;
    }


    @Override
    public IAccount create(IAccount account, String password) throws NoSuchAlgorithmException {
        // Only administrators can create a account
        if (!isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        controlsRequirements(account);
        if (isNullOrEmpty(password)) {
            throw new RuntimeException("Password is required.");
        }

        if (getByEmail(account.getEmail()) != null) {
            throw new RuntimeException("The email is in use.");
        }

        account = Database.getInstance().addAccount(account, hashPassword(password));

        return new Account(account);
    }


    @Override
    public void update(IAccount account) throws NoSuchAlgorithmException {
        update(account, null);
    }

    @Override
    public void update(IAccount account, String password) throws NoSuchAlgorithmException {
        assert account != null;

        Account oldAccount = (Account) getByID(account.getID());
        if (oldAccount == null) {
            throw new RuntimeException("Could not find account with specified id.");
        }

        // Account owners can update own account and administrators can change all accounts
        if (!trueEquals(currentUser.getID(), oldAccount.getID()) && !isAdmin()) {
            throw new AccessControlException("Insufficient permission.");
        }

        controlsRequirements(account);

        if (!trueEquals(oldAccount.getEmail(), account.getEmail()) && getByEmail(account.getEmail()) != null) {
            throw new RuntimeException("The email is in use.");
        }

        if (isNullOrEmpty(password)) {
            Database.getInstance().updateAccount(account, null);
        } else {
            Database.getInstance().updateAccount(account, hashPassword(password));
        }
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAdmin() {
        return currentUser.getAccessLevel().equals(AccessLevel.ADMINISTRATOR);
    }

    private void controlsRequirements(IAccount account) {
        AccessLevel accessLevel = account.getAccessLevel();
        if (!isNullOrEmpty(account.getFirstName()) && !isNullOrEmpty(account.getEmail())
                && accessLevel != null && accessLevel.greater(AccessLevel.GUEST)) {
            throw new RuntimeException("First name, email and access level is required.");
        }
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
