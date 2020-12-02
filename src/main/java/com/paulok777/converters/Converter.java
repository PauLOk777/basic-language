package com.paulok777.converters;

import com.paulok777.program.Line;
import com.paulok777.program.statements.Goto;
import com.paulok777.program.statements.Print;
import com.paulok777.program.statements.Statement;
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
    private static final String PRINT = "print";
    private static final String GOTO = "goto";
    private static final String END = "end";
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
                    switch (statement.getStatementType()) {
                        case PRINT:
                            function.append(PRINT_FUNCTION_START);
                            Print print = (Print) statement;
                            List<Printable> printableList = print.getPrintableList();

                            for (Printable printable : printableList) {
                                function.append(printable.getPrintableString()).append(CONCAT);
                            }

                            function.deleteCharAt(function.length() - 1);

                            function.append(PRINT_FUNCTION_END);
                            break;
                        case GOTO:
                            function.append(GOTO_FUNCTION_START);
                            Goto goTo = (Goto) statement;

                            function.append(goTo.getExpression().getPrintableString(true));
                            function.append(GOTO_FUNCTION_END);
                            break;
                        case END:
                            function.append(END_FUNCTION);
                            break;
                    }
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
}
