package com.company.common;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Tools {
    // https://www.regular-expressions.info/email.html
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^(?=[A-Z0-9][A-Z0-9@._%+-]{5,253}+$)[A-Z0-9._%+-]{1,64}+@(?:(?=[A-Z0-9-]{1,63}+\\.)[A-Z0-9]++(?:-[A-Z0-9]++)*+\\.){1,8}+[A-Z]{2,63}+$");

    // TODO: Find out where Tools should be located
    public static boolean trueContains(String phrase, String keyword) {
        return phrase != null && keyword != null && !keyword.trim().isEmpty()
                && Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(phrase).find();
    }

    public static boolean trueEquals(String string1, String string2) {
        return string1 != null && string2 != null && !string1.trim().isEmpty() && !string2.trim().isEmpty()
                && string1.equalsIgnoreCase(string2);
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).find();
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
        assert image != null;
        return new Image(image.toString());
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

    public static String createFullName(String firstName, String middleName, String lastName) {
        String fullName = "";

        if (firstName != null) {
            fullName += firstName;
        }
        if (middleName != null && middleName.trim().isEmpty()) {
            fullName += " " + middleName;
        }
        if (lastName != null) {
            fullName += " " + lastName;
        }

        return fullName.trim();
    }

    public static Boolean isEven(int number) {
        return number % 2 == 0;
    }
}
