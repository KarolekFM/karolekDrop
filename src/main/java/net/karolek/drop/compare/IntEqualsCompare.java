package net.karolek.drop.compare;

public class IntEqualsCompare extends AbstractIntCompare {

    public IntEqualsCompare(int value) {
        super(value);
    }

    @Override
    public boolean isInRange(Integer i) {
        return i == value;
    }

    @Override
    public CompareType getCompareType() {
        return CompareType.EQUALS;
    }

    @Override
    public String getParse() {
        return "=" + value;
    }
}
