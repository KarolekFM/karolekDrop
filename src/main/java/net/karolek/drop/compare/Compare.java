package net.karolek.drop.compare;

import lombok.Getter;
import lombok.Setter;
import net.karolek.drop.utils.NumberUtil;
import net.karolek.drop.utils.RandomUtil;

@Getter
@Setter
public abstract class Compare<T> {

    protected final String parse;
    protected final T[] compare;
    protected final CompareType compareType;

    public Compare(String parse, CompareType type, T... t) {
        this.parse = parse;
        this.compare = t;
        this.compareType = type;
    }

    @Override
    public String toString() {
        return this.parse;
    }

    public static IntegerCompare parseString(String string) {
        if (string == null || string.length() < 1) return null;
        if (string.startsWith("<")) {
            Integer one = NumberUtil.parseInt(string.replace("<", ""));
            if (one == null) return null;
            return new IntegerCompare(string, CompareType.LESS_THAN, one);
        } else if (string.startsWith(">")) {
            Integer one = NumberUtil.parseInt(string.replace(">", ""));
            if (one == null) return null;
            return new IntegerCompare(string, CompareType.GREATER_THAN, one);
        } else if (string.startsWith("=")) {
            Integer one = NumberUtil.parseInt(string.replace("=", ""));
            if (one == null) return null;
            return new IntegerCompare(string, CompareType.EQUALS, one);
        } else {
            String[] splits = string.split("-");
            if (splits.length != 2) return null;
            Integer one = NumberUtil.parseInt(splits[0]);
            Integer two = NumberUtil.parseInt(splits[1]);
            if (one == null || two == null) return null;
            return new IntegerCompare(string, CompareType.BETWEEN, one, two);
        }
    }

    public static int getRandomValue(IntegerCompare compare) {
        switch (compare.getCompareType()) {
            case BETWEEN:
                return RandomUtil.getRandInt(compare.getCompare()[0], compare.getCompare()[1]);
            case LESS_THAN:
                return RandomUtil.getRandInt(0, compare.getCompare()[0]);
            case GREATER_THAN:
                return RandomUtil.getRandInt(compare.getCompare()[0], Integer.MAX_VALUE);
            case EQUALS:
                return compare.getCompare()[0];
            default:
                return 1;
        }
    }

    public abstract boolean isInRange(T type);


}
