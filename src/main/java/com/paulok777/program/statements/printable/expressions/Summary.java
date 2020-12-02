package com.paulok777.program.statements.printable.expressions;

import com.paulok777.program.statements.printable.Expression;

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
    public String getPrintableString(boolean numberLine) {
        if (numbers.size() == 0) return "\n";
        if (numberLine) {
            return String.valueOf(numbers.stream().map(Number::doubleValue).reduce(0.0, Double::sum).intValue());
        }

        if (numbers.stream().anyMatch(n -> n instanceof Double)) {
            return String.valueOf(numbers.stream().map(Number::doubleValue).reduce(0.0, Double::sum));
        } else {
            return String.valueOf(numbers.stream().map(Number::intValue).reduce(0, Integer::sum));
        }
    }

    @Override
    public String toString() {
        return "Summary {" +
                "numberOfSigns=" + numberOfSigns +
                ", numbers=" + numbers +
                '}';
    }
}
