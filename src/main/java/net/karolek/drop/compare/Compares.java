package net.karolek.drop.compare;

import net.karolek.drop.utils.NumberUtil;
import net.karolek.drop.utils.RandomUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compares {

    private static final Pattern EQUALS_PATTERN = Pattern.compile("=([0-9]+)");
    private static final Pattern LESS_THAN_PATTERN = Pattern.compile("<([0-9]+)");
    private static final Pattern GREATER_THAN_PATTERN = Pattern.compile(">([0-9]+)");
    private static final Pattern BETWEEN_PATTERN = Pattern.compile("([0-9]+)\\-([0-9]+)");

    public static Compare<Integer> parseString(String string) {
        if (string == null || string.length() < 1) return null;

        {
            Matcher equalsM = EQUALS_PATTERN.matcher(string);
            if (equalsM.find()) {
                int value = Integer.parseInt(equalsM.group(1));
                return new IntEqualsCompare(value);
            }
        }

        {
            Matcher lessThanM = LESS_THAN_PATTERN.matcher(string);
            if (lessThanM.find()) {
                int value = Integer.parseInt(lessThanM.group(1));
                return new IntLessThanCompare(value);
            }
        }

        {
            Matcher greaterThanM = GREATER_THAN_PATTERN.matcher(string);
            if (greaterThanM.find()) {
                int value = Integer.parseInt(greaterThanM.group(1));
                return new IntGreaterThanCompare(value);
            }
        }

        {
            Matcher betweenM = BETWEEN_PATTERN.matcher(string);
            if (betweenM.find()) {
                int value1 = Integer.parseInt(betweenM.group(1));
                int value2 = Integer.parseInt(betweenM.group(2));
                return new IntBetweenCompare(value1, value2);
            }
        }
        return null;
    }

    public static int getRandomValue(Compare<Integer> c) {

        switch (c.getCompareType()) {
            case BETWEEN: {
                IntBetweenCompare compare = (IntBetweenCompare) c;
                return RandomUtil.getRandInt(compare.min, compare.max);
            }
            case LESS_THAN: {
                AbstractIntCompare compare = (AbstractIntCompare) c;
                return RandomUtil.getRandInt(0, compare.value);
            }
            case GREATER_THAN: {
                AbstractIntCompare compare = (AbstractIntCompare) c;
                return RandomUtil.getRandInt(compare.value, Integer.MAX_VALUE);
            }
            case EQUALS: {
                AbstractIntCompare compare = (AbstractIntCompare) c;
                return compare.value;
            }
            default:
                return 1;
        }
    }

    /*public static void main(String[] args){
        // dla przetestowania ;) mozna rzucic w testy junit
        String[] strings = {"=15", "<18", ">8", "12-22"};

        for(String s : strings) {

            System.out.println("S: " + s);
            Compare<Integer> c = parseString(s);

            System.out.println("  " + c.getClass().getSimpleName());
            System.out.println("  3:  " + c.isInRange(3));
            System.out.println("  8:  " + c.isInRange(8));
            System.out.println("  9:  " + c.isInRange(9));
            System.out.println("  15: " + c.isInRange(15));
            System.out.println("  19: " + c.isInRange(19));
            System.out.println("  23: " + c.isInRange(23));
        }
    }*/
}
