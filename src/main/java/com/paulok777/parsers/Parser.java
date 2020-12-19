package com.paulok777.parsers;

import com.paulok777.exceptions.BasicSyntaxException;
import com.paulok777.lexers.Token;
import com.paulok777.program.Line;
import com.paulok777.program.statements.*;
import com.paulok777.program.statements.printable.Expression;
import com.paulok777.program.statements.printable.Printable;
import com.paulok777.program.statements.printable.PrintableString;
import com.paulok777.program.statements.printable.expressions.Summary;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Parser {

    private static final String LINE_NUMBER_EXCEPTION = "Exception with line number, current line: ";
    private static final String STATEMENT_EXCEPTION = "Statement exception, current statement: ";
    private static final String PRINTABLE_SEQUENCE_EXCEPTION = "Printable sequence exception, " +
            "must be ';' or ':' or 'rem', current: ";
    private static final String EXPRESSION_SEQUENCE_EXCEPTION = "Expression sequence exception, " +
            "must be ';' or ':' or '+' or 'rem', current: ";
    private static final String EXPRESION_EXCEPTION = "Expression exception, must be integer or decimal, current: ";
    private static final String DUPLICATE_ROWS_EXCEPTION = "Duplicate rows exception, two same rows ";

    public static TreeSet<Line> getAbstractSyntaxTree(List<List<Token>> tokenMatrix) {
        TreeSet<Line> program = new TreeSet<>();

        for (int i = 0; i < tokenMatrix.size(); i++) {
            if (tokenMatrix.get(i).size() == 1) continue;
            Object userLineNumber = tokenMatrix.get(i).get(0).getInfo();
            try {
                boolean isAdded = program.add(createNewLine(tokenMatrix.get(i)));
                if (!isAdded) throw new BasicSyntaxException(DUPLICATE_ROWS_EXCEPTION);
            } catch (BasicSyntaxException ex) {
                String physicalLineString = "physical line number: " + (i + 1);
                String userLineString = "line number: " + userLineNumber;
                System.out.println("(" + physicalLineString + ", " + userLineString + ") " + ex.getMessage());
                System.exit(0);
            }
        }

        return program;
    }

    private static Line createNewLine(List<Token> tokens) {
        Integer lineNumber = getNumberLine(tokens.remove(0));

        Rem rem = null;
        List<Statement> statements = new ArrayList<>();

        while (tokens.size() != 0) {
            Token token = tokens.get(0);
            if (token.getTokenType().equals(Token.TokenType.COMMENT)) {
                rem = new Rem();
                tokens.clear();
            } else if (token.getTokenType().equals(Token.TokenType.NEW_LINE)) {
                break;
            } else {
                statements.add(getStatement(tokens));
            }
        }

        return new Line(lineNumber, statements, rem);
    }

    private static Integer getNumberLine(Token token) {
        if (token.getTokenType().equals(Token.TokenType.INTEGER)) {
            return Integer.parseInt(token.getInfo().toString());
        } else if (token.getTokenType().equals(Token.TokenType.DECIMAL)) {
            double number = Double.parseDouble(token.getInfo().toString());
            if (new Integer((int) number).doubleValue() == number)
                return (int) number;
        }
        throw new BasicSyntaxException(LINE_NUMBER_EXCEPTION + token.getInfo());
    }

    private static Statement getStatement(List<Token> tokens) {
        Token token = tokens.remove(0);
        switch (token.getTokenType()) {
            case PRINT:
                List<Printable> printableList = getPrintableList(tokens);
                if (tokens.size() != 0 && tokens.get(0).getTokenType().equals(Token.TokenType.STATEMENT_SEPARATOR)) {
                    tokens.remove(0);
                }
                return new Print(printableList);
            case GOTO:
                Expression expression = getExpression(tokens);
                if (tokens.size() != 0 && tokens.get(0).getTokenType().equals(Token.TokenType.STATEMENT_SEPARATOR)) {
                    tokens.remove(0);
                }
                return new Goto(expression);
            case END:
                return new End();
            case IF:
                String header = token.getInfo().toString();
                List<Statement> statements = new ArrayList<>();
                while (tokens.size() != 1) {
                    statements.add(getStatement(tokens));
                }
                return new If(header, statements);
            case FOR:
                header = token.getInfo().toString();
                statements = new ArrayList<>();
                while (tokens.size() != 1) {
                    if (!Token.TokenType.STATEMENT_SEPARATOR.equals(token.getTokenType()))
                        statements.add(getStatement(tokens));
                }
                return new For(header, statements);
            default:
                throw new BasicSyntaxException(STATEMENT_EXCEPTION + token.getInfo());
        }
    }

    private static List<Printable> getPrintableList(List<Token> tokens) {
        List<Printable> printableList = new ArrayList<>();

        boolean isNextPrintSeparator = false;
        while (tokens.get(0).getTokenType() != Token.TokenType.NEW_LINE) {
            Token token = tokens.get(0);

            if (isNextPrintSeparator && !token.getTokenType().equals(Token.TokenType.PRINT_SEPARATOR)
                    && !token.getTokenType().equals(Token.TokenType.STATEMENT_SEPARATOR)
                    && !token.getTokenType().equals(Token.TokenType.COMMENT))
                throw new BasicSyntaxException(PRINTABLE_SEQUENCE_EXCEPTION + token.getInfo());

            if (isNextPrintSeparator && (token.getTokenType().equals(Token.TokenType.STATEMENT_SEPARATOR)
                    || token.getTokenType().equals(Token.TokenType.COMMENT))) {
                break;
            }

            if (isNextPrintSeparator) {
                tokens.remove(token);
                isNextPrintSeparator = false;
                continue;
            }

            if (token.getTokenType().equals(Token.TokenType.STRING)) {
                tokens.remove(token);
                printableList.add(new PrintableString((String) token.getInfo()));
                isNextPrintSeparator = true;
            } else {
                printableList.add(getExpression(tokens));
                isNextPrintSeparator = true;
            }
        }

        return printableList;
    }

    private static Expression getExpression(List<Token> tokens) {
        Expression expression = new Summary();

        boolean nextSign = false;
        while (tokens.get(0).getTokenType() != Token.TokenType.NEW_LINE) {
            Token token = tokens.get(0);

            if (nextSign && !token.getTokenType().equals(Token.TokenType.SUM)
                    && !token.getTokenType().equals(Token.TokenType.PRINT_SEPARATOR)
                    && !token.getTokenType().equals(Token.TokenType.STATEMENT_SEPARATOR)
                    && !token.getTokenType().equals(Token.TokenType.COMMENT))
                throw new BasicSyntaxException(EXPRESSION_SEQUENCE_EXCEPTION + token.getInfo());

            if (token.getTokenType().equals(Token.TokenType.PRINT_SEPARATOR)
                    || token.getTokenType().equals(Token.TokenType.STATEMENT_SEPARATOR)
                    || token.getTokenType().equals(Token.TokenType.COMMENT)) {
                break;
            }

            if (nextSign && token.getTokenType().equals(Token.TokenType.SUM)) {
                expression.setNumberOfSigns(expression.getNumberOfSigns() + 1);
                tokens.remove(token);
                nextSign = false;
                continue;
            }

            if (token.getTokenType().equals(Token.TokenType.INTEGER)
                    || token.getTokenType().equals(Token.TokenType.DECIMAL)) {
                expression.getNumbers().add((Number) token.getInfo());
                tokens.remove(token);
                nextSign = true;
            } else {
                throw new BasicSyntaxException(EXPRESION_EXCEPTION + token.getInfo());
            }
        }

        return expression;
    }
}
