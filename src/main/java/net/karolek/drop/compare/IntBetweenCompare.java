package net.karolek.drop.compare;

public class IntBetweenCompare implements Compare<Integer> {

    protected int min, max;

    public IntBetweenCompare(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isInRange(Integer in) {
        int i = in; // bo tak ;)
        return min < i && i < max;
    }

    @Override
    public CompareType getCompareType() {
        return CompareType.BETWEEN;
    }

    @Override
    public String getParse() {
        return min + "-" + max;
    }
}
