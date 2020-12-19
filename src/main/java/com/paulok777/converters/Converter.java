package com.paulok777.converters;

import com.paulok777.program.Line;
import com.paulok777.program.statements.*;
import com.paulok777.program.statements.printable.Printable;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class Converter {
    private static final String START_OF_CLASS = "public class Pseudo {\n";
    private static final String END_OF_CLASS = "}";
    private static final String START_OF_FUNCTION = "static void f";
    private static final String MIDDLE_OF_FUNCTION = "() {\n";
    private static final String END_OF_FUNCTION = "}\n\n";
    private static final String PRINT_FUNCTION_START = "System.out.println(";
    private static final String PRINT_FUNCTION_END = ");\n";
    private static final String GOTO_FUNCTION_START = "f";
    private static final String GOTO_FUNCTION_END = "();\n";
    private static final String END_FUNCTION = "System.exit(0);\n";
    private static final String FOR_FUNCTION_START = "for(int ";
    private static final String FOR_FUNCTION_MIDDLE = " {\n";
    private static final String FOR_FUNCTION_END = "}\n";
    private static final String IF_FUNCTION_MIDDLE = " {\n";
    private static final String IF_FUNCTION_END = "}\n";
    private static final String PRINT = "print";
    private static final String GOTO = "goto";
    private static final String END = "end";
    private static final String FOR = "for";
    private static final String IF = "if";
    private static final String CONCAT = "+";
    private static final String MAIN_FUNCTION_START = "public static void main(String[] args) {\n";
    private static final String CREATE_NEW_FILE_EXCEPTION = "Can't create new file";
    private static final String WRITE_TO_FILE_EXCEPTION = "Can't write to file";

    public static void convert(TreeSet<Line> program, File file) {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.out.println(CREATE_NEW_FILE_EXCEPTION);
            System.exit(1);
        }

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(START_OF_CLASS.getBytes());

            StringBuilder mainFunction = new StringBuilder();
            mainFunction.append(MAIN_FUNCTION_START);

            for (Line line: program) {
                mainFunction.append(GOTO_FUNCTION_START)
                            .append(line.getNumber())
                            .append(GOTO_FUNCTION_END);
            }

            mainFunction.append(END_OF_FUNCTION);
            bos.write(mainFunction.toString().getBytes());

            for (Line line: program) {
                StringBuilder function = new StringBuilder(START_OF_FUNCTION);
                function.append(line.getNumber()).append(MIDDLE_OF_FUNCTION);

                List<Statement> statements = line.getStatements();

                for (Statement statement : statements) {
                    convertStatements(statement, function);
                }

                function.append(END_OF_FUNCTION);
                bos.write(function.toString().getBytes());
            }

            bos.write(END_OF_CLASS.getBytes());
        } catch (IOException ex) {
            System.out.println(WRITE_TO_FILE_EXCEPTION);
            System.exit(1);
        }
    }

    private static void convertStatements(Statement statement, StringBuilder buffer) {
        switch (statement.getStatementType()) {
            case PRINT:
                buffer.append(PRINT_FUNCTION_START);
                Print print = (Print) statement;
                List<Printable> printableList = print.getPrintableList();

                for (Printable printable : printableList) {
                    buffer.append(printable.getPrintableString()).append(CONCAT);
                }

                buffer.deleteCharAt(buffer.length() - 1);

                buffer.append(PRINT_FUNCTION_END);
                break;
            case GOTO:
                buffer.append(GOTO_FUNCTION_START);
                Goto goTo = (Goto) statement;

                buffer.append(goTo.getExpression().getPrintableString(true));
                buffer.append(GOTO_FUNCTION_END);
                break;
            case END:
                buffer.append(END_FUNCTION);
                break;
            case FOR:
                buffer.append(FOR_FUNCTION_START);
                For forr = (For) statement;
                String header = forr.getHeader();
                buffer.append(header, header.indexOf("(") + 1, header.indexOf(")") + 1);
                buffer.append(FOR_FUNCTION_MIDDLE);
                List<Statement> statementsInsideFor = forr.getStatements();

                for (Statement statementInsideFor : statementsInsideFor) {
                    convertStatements(statementInsideFor, buffer);
                }
                buffer.append(FOR_FUNCTION_END);
                break;
            case IF:
                If iff = (If) statement;
                header = iff.getHeader();
                buffer.append(header);
                buffer.append(IF_FUNCTION_MIDDLE);
                List<Statement> statementsInsideIf = iff.getStatements();

                for (Statement statementInsideIf : statementsInsideIf) {
                    convertStatements(statementInsideIf, buffer);
                }

                buffer.append(IF_FUNCTION_END);
        }
    }
}
