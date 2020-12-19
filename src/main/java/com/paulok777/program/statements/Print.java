package com.paulok777.program.statements;

import com.paulok777.program.Line;
import com.paulok777.program.statements.printable.Printable;

import java.util.List;

public class Print extends Statement {

    public static final String PRINT = "print";

    private final List<Printable> printableList;

    public Print(List<Printable> printableList) {
        this.printableList = printableList;
    }

    public List<Printable> getPrintableList() {
        return printableList;
    }

    @Override
    public String getStatementType() {
        return PRINT;
    }

    @Override
    public String toString() {
        return "\n" + STATEMENT_OFFSET + "Print {" +
                "\n" + STATEMENT_OFFSET + Line.LINE_OFFSET + "printableList=" + printableList +
                "\n" + STATEMENT_OFFSET + "}";
    }
}
