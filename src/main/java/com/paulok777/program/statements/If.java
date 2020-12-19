package com.paulok777.program.statements;

import java.util.List;

public class If extends Statement {

    public static final String IF = "if";

    private final List<Statement> statements;

    public If(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String getStatementType() {
        return IF;
    }

    @Override
    public String toString() {
        return "If {" +
                "statements=" + statements +
                '}';
    }
}
