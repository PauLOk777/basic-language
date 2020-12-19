package com.paulok777.program.statements;

import java.util.List;

public class For extends Statement {

    public static final String FOR = "for";

    private String header;
    private final List<Statement> statements;

    public For(String header, List<Statement> statements) {
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
        return FOR;
    }

    @Override
    public String toString() {
        return "For {" +
                "statements=" + statements +
                '}';
    }
}
