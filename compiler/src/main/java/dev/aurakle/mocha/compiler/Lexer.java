package dev.aurakle.mocha.compiler;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Lexer {
    private final String source;
    private int pos;
    private ArrayList<Token> result;

    public Lexer(String source) {
        this.source = source;
    }

    private void addToken(Token.Type type, int startPos) {
        result.add(new Token(type, source.substring(startPos, pos), startPos));
    }

    private String takeWhile(Predicate<Character> predicate) {
        var builder = new StringBuilder();

        while (pos < source.length() && predicate.test(source.charAt(pos))) {
            builder.append(source.charAt(pos));
            pos++;
        }

        return builder.toString();
    }

    private String word() {
        return takeWhile(c -> Character.isAlphabetic(c) || c == '_');
    }

    public Token[] getOrComputeResult() {
        if (result != null) {
            return (Token[]) result.toArray(new Token[0]);
        }

        result = new ArrayList<>();

        while (pos < source.length()) {
            takeWhile(c -> Character.isWhitespace(c));

            int startPos = pos;
            Token.Type type = null;

            type = switch (word()) {
                case "if" -> Token.Type.IF;
                case "then" -> Token.Type.THEN;
                case "else" -> Token.Type.ELSE;
                default -> null;
            };

            if (type == null) {

            }

            addToken(type, startPos);
        }

        return (Token[]) result.toArray(new Token[0]);
    }
}
