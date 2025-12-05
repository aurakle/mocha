package dev.aurakle.mocha.compiler;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class Lexer {
    private final String source;
    private int pos;
    private ArrayList<Token> result;

    public Lexer(String source) {
        this.source = source;
    }

    private void addToken(Token.Type type, int startPos) {
        result.add(new Token(type, source.substring(startPos, pos), LineColumn.from(source, startPos)));
    }

    private boolean hasNext() {
        return pos < source.length();
    }

    private char peek() {
        return source.charAt(pos);
    }

    private char pop() {
        char c = peek();
        pos++;
        return c;
    }

    private String takeWhile(Predicate<Character> predicate) {
        var builder = new StringBuilder();

        while (hasNext() && predicate.test(peek())) {
            builder.append(pop());
        }

        return builder.toString();
    }

    private String word() {
        return takeWhile(c -> Character.isAlphabetic(c) || c == '_');
    }

    private Optional<Token.Type> tryWord() {
        int p = pos;
        Token.Type type = null;

        type = switch (word()) {
            case "if" -> Token.Type.IF;
            case "then" -> Token.Type.THEN;
            case "else" -> Token.Type.ELSE;
            case "con" -> Token.Type.CON;
            case "mut" -> Token.Type.MUT;
            case "and" -> Token.Type.AND;
            case "or" -> Token.Type.OR;
            case "type" -> Token.Type.TYPE;
            default -> null;
        };

        if (type == null) {
            pos = p;
        }

        return Optional.ofNullable(type);
    }

    private Optional<Token.Type> trySymbol() {
        int p = pos;
        Token.Type type = null;

        if (!hasNext()) {
            return Optional.empty();
        }

        char c1 = pop();

        if (hasNext()) {
            char c2 = pop();

            type = switch ("" + c1 + c2) {
                case "==" -> Token.Type.EQ;
                //TODO: other symbols
                default -> null;
            };
        }

        if (type == null) {
            type = switch (c1) {
                case '+' -> Token.Type.ADD;
                //TODO: other symbols
                default -> null;
            };
        }

        if (type == null) {
            pos = p;
        }

        return Optional.ofNullable(type);
    }

    public Token[] getOrComputeResult() {
        if (result != null) {
            return (Token[]) result.toArray(new Token[0]);
        }

        result = new ArrayList<>();

        while (hasNext()) {
            takeWhile(c -> Character.isWhitespace(c));

            int startPos = pos;
            var type = tryWord()
                .or(() -> trySymbol())
                .orElseThrow(() -> new IllegalStateException("meow we need a proper error here"));

            addToken(type, startPos);
        }

        return result.toArray(new Token[0]);
    }
}
