package com.company;

import java.util.regex.Pattern;

public class tools {
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

}
