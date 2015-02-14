package net.karolek.drop.utils;

public final class NumberUtil {

    private NumberUtil() {
    }

    public static Integer parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
