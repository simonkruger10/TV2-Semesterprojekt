package com.company.data;

import com.company.common.*;
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.company.common.Tools.*;

public class JsonDatabase implements DatabaseFacade {
    private final Map<String, ProductionEntity> productions = new HashMap<>();
    private final Map<String, CreditEntity> credits = new HashMap<>();
    private final Map<String, CreditGroupEntity> creditGroups = new HashMap<>();
    private final Map<String, AccountEntity> accounts = new HashMap<>();
    private boolean access;

    public JsonDatabase() {
        try {
            String jsonAsText = readFileAsString(getResourceAsFile("credits.json"));
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

    private ProductionEntity parseProduction(JSONObject productionJson) {
        String uuid = getJsonString(productionJson,"_uuid");

        ProductionEntity production = productions.get(uuid);
        if (production == null) {
            production = new ProductionEntity();
            production.setUUID(uuid);
            production.setName(getJsonString(productionJson,"name"));
            production.setDescription(getJsonString(productionJson,"description"));
            production.setImage(new File(getJsonString(productionJson,"image")));

            JSONArray creditsJson = productionJson.getJSONArray("credits");
            for (int c = 0; c < creditsJson.length(); c++) {
                production.addCredits(parseCredit(creditsJson.getJSONObject(c)));
            }

            productions.put(uuid, production);
        }

        return production;
    }

    private CreditEntity parseCredit(JSONObject creditJson) {
        String uuid = getJsonString(creditJson,"_uuid");

        CreditEntity credit = credits.get(uuid);
        if (credit == null) {
            credit = new CreditEntity();
            credit.setUUID(uuid);
            credit.setFirstName(getJsonString(creditJson,"firstName"));
            credit.setMiddleName(getJsonString(creditJson,"middleName"));
            credit.setLastName(getJsonString(creditJson,"lastName"));
            credit.setCreditGroup(parseCreditGroup(creditJson.getJSONObject("creditGroup")));

            credits.put(uuid, credit);
        }

        return credit;
    }

    private CreditGroupEntity parseCreditGroup(JSONObject creditGroupJson) {
        String uuid = creditGroupJson.getString("_uuid");

        CreditGroupEntity creditGroup = creditGroups.get(uuid);
        if (creditGroup == null) {
            creditGroup = new CreditGroupEntity();
            creditGroup.setUUID(uuid);
            creditGroup.setName(creditGroupJson.getString("name"));

            creditGroups.put(uuid, creditGroup);
        }

        return creditGroup;
    }

    private AccountEntity parseAccount(JSONObject accountJson) {
        AccountEntity account = new AccountEntity();

        account.setUUID(getJsonString(accountJson,"_uuid"));
        account.setFirstName(getJsonString(accountJson,"firstName"));
        account.setMiddleName(getJsonString(accountJson,"middleName"));
        account.setLastName(getJsonString(accountJson, "lastName"));
        account.setEmail(getJsonString(accountJson,"email"));
        account.setPassword(getJsonString(accountJson,"hashedPassword"));
        String accessLevelName = getJsonString(accountJson,"accessLevel");
        
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
        } catch (JSONException jsonException) {}
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
    public IProduction addProduction(IProduction production) {
        ProductionEntity productionEntity = new ProductionEntity();
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
    public ICredit addCredit(ICredit credit) {
        CreditEntity creditEntity = new CreditEntity();
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
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        CreditGroupEntity creditGroupEntity = new CreditGroupEntity();
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
    public IAccount addAccount(IAccount account, String hashedPassword) {
        AccountEntity accountEntity = new AccountEntity();
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
}
