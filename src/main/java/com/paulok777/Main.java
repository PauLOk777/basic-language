package com.paulok777;

import com.paulok777.converters.Converter;
import com.paulok777.expanders.Expander;
import com.paulok777.lexers.Lexer;
import com.paulok777.lexers.Token;
import com.paulok777.parsers.Parser;
import com.paulok777.program.Line;
import com.paulok777.readers.Reader;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

public class Main {
    private static final String PSEUDO_FILE = "Pseudo.java";

    public static void main(String[] args) {
        List<String> lines = Reader.getLines("test.txt");
        List<List<Token>> matrixOfTokens = Lexer.parsePageToTokens(lines);
        TreeSet<Line> abstractSyntaxTree = Parser.getAbstractSyntaxTree(matrixOfTokens);
        File file = new File(PSEUDO_FILE);
        Converter.convert(abstractSyntaxTree, file);
        Expander.execute(file);
    }
}
