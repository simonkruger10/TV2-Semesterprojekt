package com.company.common;

import javafx.scene.Node;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Tools {
    // https://www.regular-expressions.info/email.html
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^(?=[a-zA-Z0-9][a-zA-Z0-9@._%+-]{5,253}+$)" + //Contains only valid characters
                    "[a-zA-Z0-9._%+-]{1,64}+@" +                   //There has to be a first part, and it must end in @
                    "(?:" +                                     //Start of non-capturing group
                        "(?=[a-zA-Z0-9-]{1,63}+\\.)[a-zA-Z0-9]++" +  //There must at least be some text ending with a '.'
                    "(?:-[a-zA-Z0-9]++)*+\\.){1,8}" +             //It must have text after the '.' and there can be 8 sub-domains
                    "+[a-zA-Z]{2,63}+$");                         //And there must be some text/country/domain-code to end the email.

    public static boolean trueContains(String phrase, String keyword) {
        return phrase != null && keyword != null && !keyword.trim().isEmpty()
                && Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(phrase).matches();
    }

    public static boolean trueEquals(String string1, String string2) {
        return string1 != null && string2 != null && !string1.trim().isEmpty() && !string2.trim().isEmpty()
                && string1.equalsIgnoreCase(string2);
    }

    public static boolean trueEquals(Integer number, Integer number1) {
        return number != null && number.equals(number1);
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static URL getResourceAsUrl(String fileName) {
        assert fileName != null;
        return Tools.class.getResource(fileName);
    }

    public static File getResourceAsFile(String fileName) {
        URL file = getResourceAsUrl(fileName);
        assert file != null;
        return new File(file.getFile());
    }

    public static Image getResourceAsImage(String fileName) {
        URL image = getResourceAsUrl(fileName);
        if (image != null) {
            return new Image(image.toString());
        }
        return null;
    }

    public static String readFileAsString(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder text = new StringBuilder();
        while (scanner.hasNext()) {
            text.append(scanner.nextLine());
            text.append("\n");
        }
        return text.toString();
    }

    public static String createFullName(String firstName, String lastName) {
        String fullName = "";

        if (firstName != null) {
            fullName += firstName;
        }
        if (lastName != null) {
            fullName += " " + lastName;
        }

        return fullName.trim();
    }

    public static Boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static void trueVisible(Node node, boolean state) {
        // This method may need to be in the gui package
        node.setVisible(state);
        node.setManaged(state);
    }
}
