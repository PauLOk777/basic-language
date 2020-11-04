package com.paulok777;

public class Token {
    private Object info;
    private final TokenType tokenType;

    public enum TokenType {
        STRING,
        INTEGER,
        DECIMAL,
        PRINT,
        GOTO,
        END,
        PRINT_SEPARATOR,
        STATEMENT_SEPARATOR,
        COMMENT,
        NEW_LINE,
        SUM,
        ERROR;
    }

    public Token(Object info, TokenType tokenType) {
        if (tokenType.equals(TokenType.INTEGER)) {
            this.info = Long.valueOf((String) info);
        } else if (tokenType.equals(TokenType.DECIMAL)) {
            this.info = Double.valueOf((String) info);
        } else {
            this.info = info;
        }
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "Token{" +
                "info=" + (info.equals("\n") ? "\\n" : info) +
                ", tokenType=" + tokenType +
                '}';
    }
}
