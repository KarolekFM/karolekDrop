package net.karolek.drop.compare;

public class IntLessThanCompare extends AbstractIntCompare {

    public IntLessThanCompare(int value) {
        super(value);
    }

    @Override
    public boolean isInRange(Integer type) {
        return type < value;
    }

    @Override
    public CompareType getCompareType() {
        return CompareType.LESS_THAN;
    }

    @Override
    public String getParse() {
        return "<" + value;
    }
}
