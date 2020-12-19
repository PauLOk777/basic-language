package com.paulok777.program.statements;

import java.util.List;

public class If extends Statement {

    public static final String IF = "if";

    private String header;
    private final List<Statement> statements;

    public If(String header, List<Statement> statements) {
        this.header = header;
        this.statements = statements;
    }

    public String getHeader() {
        return header;
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
