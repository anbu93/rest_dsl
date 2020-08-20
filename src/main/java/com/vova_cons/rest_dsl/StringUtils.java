package com.vova_cons.rest_dsl;

import java.util.Date;

/**
 * Created by anbu on 20.08.20.
 **/
public class StringUtils {
    public static String toCamelCase(String s){
        String[] parts = s.split("_");
        String camelCaseString = "";
        for (String part : parts){
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString;
    }

    public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }

    public static String toFirstLetterLower(String s) {
        return s.substring(0, 1).toLowerCase() +
                s.substring(1);
    }

    public static String getDate() {
        Date date = new Date(System.currentTimeMillis());
        return prefixZero(date.getDate(), 2) + "." + prefixZero(date.getMonth() + 1, 2) + "." + prefixZero(date.getYear() % 100, 2);
    }

    public static String prefixZero(int value, int numbers) {
        String result = Integer.toString(value);
        for(int i = result.length(); i < numbers; i++) {
            result = "0" + result;
        }
        return result;
    }
}
