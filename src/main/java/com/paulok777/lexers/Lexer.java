package com.paulok777.lexers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final int groupCount = 14;
    private static final String REGEXP_FOR_DEFINING_TOKEN =
                    "((?<=[ ;])\"[^\"]*\"(?=[\\s;])|(?<=[ ;])'[^']*'(?=[\\s;]))" +
                    "|((?<= )for\\([-\\w\\d<>+=;!]+\\)(?=\\s))" +
                    "|((?<= )if\\([\\w\\d<>=!]+\\)(?=\\s))" +
                    "|((?<=[ ;+])\\d+(?=[\\s;+])|^\\d+(?=[ ;+])|^\\d+$)" +
                    "|((?<=[ ;+])\\d+\\.(?=[\\s;+])|(?<=[ ;+])\\.\\d+(?=[\\s;+])|(?<=[ ;+])\\d+\\.\\d+(?=[\\s;+])|(?<=[ ;+])\\.(?=[\\s;+]))" +
                    "|((?<= )print(?=\\s))" +
                    "|((?<= )goto(?= ))" +
                    "|((?<= )end(?=\\s))" +
                    "|(;)" +
                    "|(:)" +
                    "|((?<= )rem(?=\\s))" +
                    "|(\n)" +
                    "|((?<=[ \\d.])\\+(?=[ \\d.]))" +
                    "|(\\S+)";

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
            if (tokenType.equals(Token.TokenType.COMMENT)) break;
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
}
