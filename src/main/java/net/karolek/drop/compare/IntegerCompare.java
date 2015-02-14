package net.karolek.drop.compare;

public class IntegerCompare extends Compare<Integer> {

    public IntegerCompare(String parse, CompareType type, Integer... t) {
        super(parse, type, t);
    }

    @Override
    public boolean isInRange(Integer type) {
        switch (compareType) {
            case EQUALS:
                return compare[0] == type;
            case GREATER_THAN:
                return compare[0] < type;
            case LESS_THAN:
                return compare[0] > type;
            case BETWEEN:
                return type > compare[0] && type < compare[1];
            default:
                return false;
        }
    }
}
