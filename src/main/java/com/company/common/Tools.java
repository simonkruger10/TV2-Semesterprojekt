package com.company.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Tools {
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

    public static File getResourceAsFile(String fileName) {
        assert fileName != null;
        URL file = Tools.class.getResource(fileName);

        assert file != null;
        return new File(file.getFile());
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
}
