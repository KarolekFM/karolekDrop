package net.karolek.drop.compare;

public class IntBetweenCompare implements Compare<Integer> {

    protected int min, max;

    public IntBetweenCompare(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isInRange(Integer type) {
        int t = type; // bo tak ;)
        return min < t && t < max;
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
