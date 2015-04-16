package net.karolek.drop.compare;

public abstract class AbstractIntCompare implements Compare<Integer> {

    protected int value;

    public AbstractIntCompare(int value){
        this.value = value;
    }

}
