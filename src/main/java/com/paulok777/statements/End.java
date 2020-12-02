package com.paulok777.statements;

public class End extends Statement {
    public static final String END = "end";

    @Override
    public String getStatementType() {
        return END;
    }

    @Override
    public String toString() {
        return "\n" + STATEMENT_OFFSET+ "End{}";
    }
}
