package com.paulok777;

import com.paulok777.printable.Printable;
import com.paulok777.statements.Goto;
import com.paulok777.statements.Print;
import com.paulok777.statements.Statement;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class Converter {
    private static final String PSEUDO_FILE = "Pseudo.java";
    private static final String START_OF_CLASS = "public class Pseudo {\n";
    private static final String END_OF_CLASS = "\n}";
    private static final String START_OF_FUNCTION = "void f";
    private static final String MIDDLE_OF_FUNCTION = "() {\n";
    private static final String END_OF_FUNCTION = "\n}\n\n";
    private static final String PRINT_FUNCTION_START = "System.out.print(";
    private static final String PRINT_FUNCTION_END = ");\n";
    private static final String GOTO_FUNCTION_START = "f";
    private static final String GOTO_FUNCTION_END = "();\n";
    private static final String END_FUNCTION = "System.exit(0);\n";
    private static final String PRINT = "print";
    private static final String GOTO = "goto";
    private static final String END = "end";
    private static final String CONCAT = "+";

    public static void convert(TreeSet<Line> program) {
        File file = new File(PSEUDO_FILE);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.out.println("Can't create new file");
            return;
        }

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(START_OF_CLASS.getBytes());

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
                            function.append(goTo.getExpression().getSum().intValue());
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
            System.out.println("Can't write to file");
        }
    }

    public static void main(String[] args) {
        List<String> lines = Reader.getLines("test.bsc");
        List<List<Token>> matrixOfTokens = Lexer.parsePageToTokens(lines);
        TreeSet<Line> abstractSyntaxTree = Parser.getAbstractSyntaxTree(matrixOfTokens);
        for (Line line: abstractSyntaxTree) {
            System.out.println(line);
        }
        convert(abstractSyntaxTree);
    }
}
