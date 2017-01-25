package com.carcaret;

public class StringUtil {

    public static String capitalFirst(String s) {
        String first = s.substring(0, 1);
        return first.toUpperCase() + s.substring(1);
    }
}
