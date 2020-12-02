package com.paulok777.program.statements;

import com.paulok777.program.Line;
import com.paulok777.program.statements.printable.Expression;

public class Goto extends Statement {
    public static final String GOTO = "goto";
    private final Expression expression;

    public Goto(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String getStatementType() {
        return GOTO;
    }

    @Override
    public String toString() {
        return "\n" + STATEMENT_OFFSET + "Goto {" +
                "\n " + STATEMENT_OFFSET + Line.LINE_OFFSET + "expression=" + expression +
                "\n" + STATEMENT_OFFSET + "}";
    }
}
