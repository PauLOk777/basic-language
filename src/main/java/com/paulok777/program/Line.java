package com.paulok777.program;

import com.paulok777.program.statements.Rem;
import com.paulok777.program.statements.Statement;

import java.util.List;

public class Line implements Comparable<Line> {

    public static final String LINE_OFFSET = "   ";
    private int number;
    private List<Statement> statements;
    private Rem rem;

    public Line(int number, List<Statement> statements, Rem rem) {
        this.number = number;
        this.statements = statements;
        this.rem = rem;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public int compareTo(Line o) {
        return Integer.compare(number, o.number);
    }

    @Override
    public String toString() {
        return "Line {" +
                "\n" + LINE_OFFSET + "number=" + number +
                ",\n" + LINE_OFFSET + "statements=" + statements +
                ",\n" + LINE_OFFSET + "rem=" + rem +
                "\n}";
    }
}
