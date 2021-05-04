package com.company.data;

import com.company.common.*;
import com.company.data.mapper.Account;
import com.company.data.mapper.Credit;
import com.company.data.mapper.CreditGroup;
import com.company.data.mapper.Production;
import org.json.*;

import java.io.FileNotFoundException;
import java.util.*;

import static com.company.common.Tools.*;

@SuppressWarnings("UnusedReturnValue")
public class JsonDatabase implements DatabaseFacade {
    private final Map<String, Production> productions = new HashMap<>();
    private final Map<String, Credit> credits = new HashMap<>();
    private final Map<String, CreditGroup> creditGroups = new HashMap<>();
    private final Map<String, Account> accounts = new HashMap<>();
    private boolean access;

    public JsonDatabase() {
        try {
            String jsonAsText = readFileAsString(getResourceAsFile("/credits.json"));
            JSONObject jsonObject = new JSONObject(jsonAsText);

            JSONArray jsonArray = jsonObject.getJSONArray("creditGroups");
            for (int i = 0; i < jsonArray.length(); i++) {
                parseCreditGroup(jsonArray.getJSONObject(i));
            }

            jsonArray = jsonObject.getJSONArray("credits");
            for (int i = 0; i < jsonArray.length(); i++) {
                parseCredit(jsonArray.getJSONObject(i));
            }

            jsonArray = jsonObject.getJSONArray("productions");
            for (int i = 0; i < jsonArray.length(); i++) {
                parseProduction(jsonArray.getJSONObject(i));
            }

            jsonArray = jsonObject.getJSONArray("accounts");
            for (int i = 0; i < jsonArray.length(); i++) {
                parseAccount(jsonArray.getJSONObject(i));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: \"credits.json\"");
            e.printStackTrace();

            access = false;
        }

        access = true;
    }

    @SuppressWarnings("UnusedReturnValue")
    private Production parseProduction(JSONObject productionJson) {
        String uuid = getJsonString(productionJson, "_uuid");

        Production production = productions.get(uuid);
        if (production == null) {
            production = new Production();
            production.setUUID(uuid);
            production.setName(getJsonString(productionJson, "name"));
            production.setDescription(getJsonString(productionJson, "description"));
            String image = getJsonString(productionJson, "image");
            if (!isNullOrEmpty(image)) {
                production.setImage(getResourceAsImage(image));
            }

            JSONArray creditsJson = productionJson.getJSONArray("credits");
            for (int c = 0; c < creditsJson.length(); c++) {
                production.addCredits(parseCredit(creditsJson.getJSONObject(c)));
            }

            productions.put(uuid, production);
        }

        return production;
    }

    private Credit parseCredit(JSONObject creditJson) {
        String uuid = getJsonString(creditJson, "_uuid");

        Credit credit = credits.get(uuid);
        if (credit == null) {
            credit = new Credit();
            credit.setUUID(uuid);
            credit.setFirstName(getJsonString(creditJson, "firstName"));
            credit.setMiddleName(getJsonString(creditJson, "middleName"));
            credit.setLastName(getJsonString(creditJson, "lastName"));
            credit.setCreditGroup(parseCreditGroup(creditJson.getJSONObject("creditGroup")));

            credits.put(uuid, credit);
        }

        return credit;
    }

    private CreditGroup parseCreditGroup(JSONObject creditGroupJson) {
        String uuid = creditGroupJson.getString("_uuid");

        CreditGroup creditGroup = creditGroups.get(uuid);
        if (creditGroup == null) {
            creditGroup = new CreditGroup();
            creditGroup.setUUID(uuid);
            creditGroup.setName(creditGroupJson.getString("name"));

            creditGroups.put(uuid, creditGroup);
        }

        return creditGroup;
    }

    private Account parseAccount(JSONObject accountJson) {
        Account account = new Account();

        account.setUUID(getJsonString(accountJson, "_uuid"));
        account.setFirstName(getJsonString(accountJson, "firstName"));
        account.setMiddleName(getJsonString(accountJson, "middleName"));
        account.setLastName(getJsonString(accountJson, "lastName"));
        account.setEmail(getJsonString(accountJson, "email"));
        account.setPassword(getJsonString(accountJson, "hashedPassword"));
        String accessLevelName = getJsonString(accountJson, "accessLevel");

        for (AccessLevel accessLevel : AccessLevel.values()) {
            if (accessLevel.toString().equals(accessLevelName)) {
                account.setAccessLevel(accessLevel);
            }
        }

        accounts.put(account.getUUID(), account);

        return account;
    }

    private String getJsonString(JSONObject jsonObject, String key) {
        String value = null;
        try {
            value = jsonObject.getString(key);
        } catch (JSONException ignored) {
        }
        return value;
    }


    @Override
    public boolean checkAccess() {
        return access;
    }


    @Override
    public IProduction[] getProductions() {
        return productions.values().toArray(new IProduction[0]);
    }

    @Override
    public IProduction getProduction(String uuid) {
        return productions.get(uuid);
    }

    @Override
    public IProduction addProduction(IProduction production) {
        Production productionEntity = new Production();
        productionEntity.setCopyOf(production);

        productions.put(productionEntity.getUUID(), productionEntity);

        return productionEntity;
    }

    @Override
    public void updateProduction(IProduction production) {
        productions.get(production.getUUID()).setCopyOf(production);
    }


    @Override
    public ICredit[] getCredits() {
        return credits.values().toArray(new ICredit[0]);
    }

    @Override
    public ICredit getCredit(String uuid) {
        return credits.get(uuid);
    }

    @Override
    public ICredit addCredit(ICredit credit) {
        Credit creditEntity = new Credit();
        creditEntity.setCopyOf(credit);

        credits.put(creditEntity.getUUID(), creditEntity);

        return creditEntity;
    }

    @Override
    public void updateCredit(ICredit credit) {
        credits.get(credit.getUUID()).setCopyOf(credit);
    }


    @Override
    public ICreditGroup[] getCreditGroups() {
        return creditGroups.values().toArray(new ICreditGroup[0]);
    }

    @Override
    public ICreditGroup getCreditGroup(String uuid) {
        return creditGroups.get(uuid);
    }

    @Override
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        CreditGroup creditGroupEntity = new CreditGroup();
        creditGroupEntity.setCopyOf(creditGroup);

        creditGroups.put(creditGroupEntity.getUUID(), creditGroupEntity);

        return creditGroupEntity;
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroup) {
        creditGroups.get(creditGroup.getUUID()).setCopyOf(creditGroup);
    }


    @Override
    public IAccount[] getAccounts() {
        return accounts.values().toArray(new IAccount[0]);
    }

    @Override
    public IAccount getAccount(String uuid) {
        return accounts.get(uuid);
    }

    @Override
    public IAccount addAccount(IAccount account, String hashedPassword) {
        Account accountEntity = new Account();
        accountEntity.setCopyOf(account);
        accountEntity.setPassword(hashedPassword);

        accounts.put(accountEntity.getUUID(), accountEntity);

        return accountEntity;

    }

    @Override
    public void updateAccount(IAccount account) {
        accounts.get(account.getUUID()).setCopyOf(account);
    }

    @Override
    public void updateAccount(IAccount account, String hashedPassword) {
        updateAccount(account);
        accounts.get(account.getUUID()).setPassword(hashedPassword);
    }


    @Override
    public IAccount login(IAccount account, String password) {
        Account accountEntity = accounts.get(account.getUUID());
        if (trueEquals(accountEntity.getPassword(), password)) {
            return accountEntity;
        }
        return null;
    }
}
