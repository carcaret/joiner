package com.carcaret;

public class StringUtil {

    public static boolean isNotNullNorEmpty(String s) {
        return s !=null && !s.isEmpty();
    }

    public static boolean isNullOrEmpty(String s) {
        return !isNotNullNorEmpty(s);
    }

    public static String capitalFirst(String s) {
        String first = s.substring(0, 1);
        return first.toUpperCase() + s.substring(1);
    }
}
