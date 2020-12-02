package com.paulok777.printable.expressions;

import com.paulok777.printable.Expression;

import java.util.ArrayList;
import java.util.List;

public class Summary extends Expression {
    public static final String SIGN = "+";
    private int numberOfSigns = 0;
    private final List<Number> numbers = new ArrayList<>();

    public int getNumberOfSigns() {
        return numberOfSigns;
    }

    public void setNumberOfSigns(int numberOfSigns) {
        this.numberOfSigns = numberOfSigns;
    }

    public List<Number> getNumbers() {
        return numbers;
    }

    public String getSign() {
        return SIGN;
    }

    @Override
    public Number getSum() {
        return numbers.stream().map(Number::doubleValue).reduce(0.0, Double::sum);
    }

    @Override
    public String getPrintableString() {
        StringBuilder result = new StringBuilder();
        for (Number number: numbers) {
            result.append(number).append(SIGN);
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public String toString() {
        return "Summary {" +
                "numberOfSigns=" + numberOfSigns +
                ", numbers=" + numbers +
                '}';
    }
}
