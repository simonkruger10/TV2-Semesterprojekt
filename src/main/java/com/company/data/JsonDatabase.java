package com.company.data;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.company.common.IAccount;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.common.IProduction;

public class JsonDatabase implements DatabaseFacade {
    JSONObject jsonProductionTable;
    ArrayList<ProductionEntity> productions;

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
                ProductionEntity p = new ProductionEntity(id, productionName, image);
                System.out.println(p.getName());

                JSONArray creditsJsonArray = prod.getJSONArray("credits");
                for (int c = 0; c < creditsJsonArray.length(); c++) {
                    JSONObject jsonCredit = creditsJsonArray.getJSONObject(c);
                    CreditGroupEntity creditgroup = new CreditGroupEntity(jsonCredit.getString("creditgroup"));
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
                    CreditEntity credit = new CreditEntity(personID, firstName, middleName, lastName, creditgroup);
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
    public IProduction[] getProductions() {
        return new IProduction[0];
    }

    @Override
    public IProduction getProduction(int id) {
        return null;
    }

    @Override
    public void addProduction(IProduction productionInfo) {

    }

    @Override
    public void updateProduction(IProduction productionInfo) {

    }

    @Override
    public ICredit[] getCredits() {
        return new ICredit[0];
    }

    @Override
    public ICredit getCredit(int id) {
        return null;
    }

    @Override
    public void addCredit(ICredit creditInfo) {

    }

    @Override
    public void updateCredit(ICredit creditInfo) {

    }

    @Override
    public ICreditGroup[] getCreditGroups() {
        return new ICreditGroup[0];
    }

    @Override
    public ICreditGroup getCreditGroup(int id) {
        return null;
    }

    @Override
    public void addCreditGroup(ICreditGroup creditGroupInfo) {

    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroupInfo) {

    }

    @Override
    public IAccount[] getAccounts() {
        return new IAccount[0];
    }

    @Override
    public IAccount getAccount(int id) {
        return null;
    }

    @Override
    public void addAccount(IAccount accountInfo) {

    }

    @Override
    public void updateAccount(IAccount accountInfo) {

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
