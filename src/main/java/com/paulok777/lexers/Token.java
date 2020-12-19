package com.paulok777.lexers;

public class Token {
    private final Object info;
    private final TokenType tokenType;

    public enum TokenType {
        STRING,
        FOR,
        IF,
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
        ERROR
    }

    public Token(Object info, TokenType tokenType) {
        if (tokenType.equals(TokenType.INTEGER)) {
            this.info = Long.valueOf((String) info);
        } else if (tokenType.equals(TokenType.DECIMAL)) {
            if (info.toString().equals(".")) {
                this.info = 0.0;
            } else {
                this.info = Double.valueOf((String) info);
            }
        } else {
            this.info = info;
        }
        this.tokenType = tokenType;
    }

    public Object getInfo() {
        return info;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "Token{" +
                "info=" + (info.equals("\n") ? "\\n" : info) +
                ", tokenType=" + tokenType +
                '}';
    }
}
