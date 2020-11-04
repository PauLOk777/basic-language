package com.paulok777;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final int groupCount = 12;
    private static final String REGEXP_FOR_DEFINING_TOKEN =
            "((?<= )\"[^\"]*\"(?=\\s))|((?<= )\\d+(?=\\s)|^\\d+(?= ))" +
                    "|((?<= )\\d+\\.(?=\\s)|(?<= )\\.\\d+(?=\\s)|(?<= )\\d+\\.\\d+(?=\\s))" +
            "|((?<= )print(?= ))|((?<= )goto(?= ))|((?<= )end(?= ))" +
                    "|((?<= );(?= ))|((?<= ):(?= ))|((?<= )rem(?= ))|(\n)|((?<= )\\+(?= ))|(\\S+)";

    public static List<Token> tokenizeString(String row) {
        List<Token> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEXP_FOR_DEFINING_TOKEN);
        Matcher matcher = pattern.matcher(row);
        while(matcher.find()) {
            int groupNumber = groupCount - 1;
            for (int i = 1; i <= groupCount; i++) {
                if (matcher.group(i) != null) {
                    groupNumber = i - 1;
                    break;
                }
            }
            Token.TokenType tokenType = Token.TokenType.values()[groupNumber];
            tokens.add(new Token(matcher.group(), tokenType));
        }

        return tokens;
    }

    public static List<List<Token>> parsePageToTokens(List<String> page) {
        List<List<Token>> matrixOfTokens = new ArrayList<>();

        for (String s : page) {
            matrixOfTokens.add(tokenizeString(s));
        }

        return matrixOfTokens;
    }

    public static void main(String[] args) {
        List<List<Token>> matrixOfTokens = parsePageToTokens(Reader.getLines("test.txt"));
        for (List<Token> rowOfTokens : matrixOfTokens) {
            System.out.println(rowOfTokens);
        }
    }
}
