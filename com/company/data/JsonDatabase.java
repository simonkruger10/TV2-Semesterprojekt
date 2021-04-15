package com.company.data;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonDatabase implements Database {
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
                Production p = new Production(prod.getString("productionName"));
                System.out.println(p.getProductionName());
                JSONArray creditsJsonArray = prod.getJSONArray("credits");
                for (int c = 0; c < creditsJsonArray.length(); c++) {
                    JSONObject jsonCredit = creditsJsonArray.getJSONObject(c);
                    Creditgroup creditgroup = new Creditgroup(jsonCredit.getString("creditgroup"));
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
                    Credit credit = new Credit(creditgroup, firstName, middleName, lastName, personID);
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
    public void getProductions() {

    }

    @Override
    public void addProduction(Production production) {

    }

    @Override
    public void getCredits(Production production) {

    }

    @Override
    public void addCredit(Production production) {

    }

    @Override
    public boolean validLogin(String username, String password) {
        return false;
    }

    public static File loadFile(String fileName) {
        String path = System.getProperty("user.dir");
        if (path.endsWith("TV2-Semesterprojekt")) {
            return new File(path + "\\com\\company\\data\\json\\" + fileName);
        }
        return new File(path + "\\TV2-Semesterprojekt\\com\\company\\data\\json\\" + fileName);
    }
}
