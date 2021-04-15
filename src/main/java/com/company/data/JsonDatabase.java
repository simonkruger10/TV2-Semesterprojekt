package com.company.data;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.company.crossInterfaces.AccountEntity;
import com.company.crossInterfaces.CreditEntity;
import com.company.crossInterfaces.CreditGroupEntity;
import com.company.crossInterfaces.ProductionEntity;

public class JsonDatabase implements DatabaseFacade {
    JSONObject jsonProductionTable;
    ArrayList<Production> productions;

    public JsonDatabase() {
        try {
            Scanner fileReader = new Scanner(loadFile("credits.json"));
            StringBuilder text = new StringBuilder();
            while (fileReader.hasNext()) {
                text.append(fileReader.nextLine());
                text.append("\n");
            }
            System.out.println(text);
            jsonProductionTable = new JSONObject(text.toString());
            System.out.println(jsonProductionTable.toString());

            productions = new ArrayList<>();
            JSONArray productions = jsonProductionTable.getJSONArray("productions");
            for (int i = 0; i < productions.length(); i++) {
                JSONObject prod = productions.getJSONObject(i);

                String id = prod.getString("id");
                String productionName = prod.getString("productionName");
                File image = new File(prod.getString("Image"));
                Production p = new Production(id, productionName, image);
                System.out.println(p.getName());

                JSONArray creditsJsonArray = prod.getJSONArray("credits");
                for (int c = 0; c < creditsJsonArray.length(); c++) {
                    JSONObject jsonCredit = creditsJsonArray.getJSONObject(c);
                    CreditGroup creditgroup = new CreditGroup(jsonCredit.getString("creditgroup"));
                    String firstName = jsonCredit.getString("firstName");
                    String middleName = null;
                    try {
                        middleName = jsonCredit.getString("middleName");
                    } catch (JSONException jsonException) {
                        if (jsonException.getMessage().equals("JSONObject[\"middleName\"] is not a string.")) {
                            //Apparently the package cant accept the null value as a string value.
                        } else {
                            jsonException.printStackTrace();
                        }
                    }
                    String lastName = jsonCredit.getString("lastName");
                    int personID = jsonCredit.getInt("personID");
                    Credit credit = new Credit(personID, firstName, middleName, lastName, creditgroup);
                    p.addCredit(credit);
                    System.out.println(credit.toJSONString());
                }
            }
            //System.out.println(productions.getJSONObject(1));

        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("File not found: \"credits.json\"");
            System.out.println(fileNotFoundException.toString());
        }
    }

    @Override
    public boolean checkAccess() {
        return false;
    }

    @Override
    public ProductionEntity[] getProductions() {
        return new ProductionEntity[0];
    }

    @Override
    public ProductionEntity getProduction(int id) {
        return null;
    }

    @Override
    public void addProduction(ProductionEntity productionInfo) {

    }

    @Override
    public void updateProduction(ProductionEntity productionInfo) {

    }

    @Override
    public CreditEntity[] getCredits() {
        return new CreditEntity[0];
    }

    @Override
    public CreditEntity getCredit(int id) {
        return null;
    }

    @Override
    public void addCredit(CreditEntity creditInfo) {

    }

    @Override
    public void updateCredit(CreditEntity creditInfo) {

    }

    @Override
    public CreditGroupEntity[] getCreditGroups() {
        return new CreditGroupEntity[0];
    }

    @Override
    public CreditGroupEntity getCreditGroup(int id) {
        return null;
    }

    @Override
    public void addCreditGroup(CreditGroupEntity creditGroupInfo) {

    }

    @Override
    public void updateCreditGroup(CreditGroupEntity creditGroupInfo) {

    }

    @Override
    public AccountEntity[] getAccounts() {
        return new AccountEntity[0];
    }

    @Override
    public AccountEntity getAccount(int id) {
        return null;
    }

    @Override
    public void addAccount(AccountEntity accountInfo) {

    }

    @Override
    public void updateAccount(AccountEntity accountInfo) {

    }

    @Override
    public boolean login(String email, String password) {
        return false;
    }

    public static File loadFile(String fileName) {
        URL jsonFile = JsonDatabase.class.getResource(fileName);
        return new File(jsonFile.getFile());
    }
}
