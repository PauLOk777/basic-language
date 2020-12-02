package com.paulok777.printable;

import java.util.List;

public abstract class Expression extends Printable {
    public abstract int getNumberOfSigns();

    public abstract void setNumberOfSigns(int numberOfSigns);

    public abstract List<Number> getNumbers();

    public abstract String getSign();

    public abstract Number getSum();
}
