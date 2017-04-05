package com.example.parthobiswas.familytree.utils;

import java.util.Random;

/**
 * Created by parthobiswas on 3/23/17.
 */

public final class AppUtils {

    private AppUtils() {
    }

    public static final String MY_SSN = "MY_SSN_KEY";
    public static final String IS_ADDIND_RELATION = "IS_ADDIND_RELATION";
    public static final String SELECTED_PERSON = "SELECTED_PERSON";

    public static String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }

}
