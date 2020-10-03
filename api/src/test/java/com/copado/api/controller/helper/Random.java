package com.copado.api.controller.helper;

public class Random {
    private static final String AlphaNumericString = new StringBuilder().append("ABCDEFGHIJKLMNOPQRSTUVWXYZ").append("0123456789").append("abcdefghijklmnopqrstuvxyz").toString();

    public static String string(int size) {
        StringBuilder result = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());

            result.append(AlphaNumericString.charAt(index));
        }

        return result.toString();
    }
}
