package com.jdy.angel.utils;

/**
 * @author Aglet
 * @create 2022/7/6 17:20
 */
public class StringUtil {

    private StringUtil() {
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.isBlank()) {
            return str;
        }

        char baseChar = str.charAt(0);
        char updatedChar;
        if (capitalize) {
            updatedChar = Character.toUpperCase(baseChar);
        } else {
            updatedChar = Character.toLowerCase(baseChar);
        }

        if (baseChar == updatedChar) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[0] = updatedChar;
        return new String(chars);
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }
}
