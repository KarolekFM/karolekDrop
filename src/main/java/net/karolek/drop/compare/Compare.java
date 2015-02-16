package net.karolek.drop.compare;

public interface Compare<T> {

    public abstract boolean isInRange(T type);

    public abstract CompareType getCompareType();

    public abstract String getParse();
}
