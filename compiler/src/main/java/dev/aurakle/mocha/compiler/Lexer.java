package dev.aurakle.mocha.compiler;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import dev.aurakle.mocha.compiler.Token.Type;

public class Lexer {
    private final String source;
    private int pos;
    private ArrayList<Token> result;

    public Lexer(String source) {
        this.source = source;
    }

    private void addToken(Type type, int startPos) {
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

    private Optional<Type> tryWord() {
        int p = pos;
        Type type = null;

        type = switch (word()) {
            case "if" -> Type.IF;
            case "then" -> Type.THEN;
            case "else" -> Type.ELSE;
            case "con" -> Type.CON;
            case "mut" -> Type.MUT;
            case "and" -> Type.AND;
            case "or" -> Type.OR;
            case "type" -> Type.TYPE;
            default -> null;
        };

        if (type == null) {
            pos = p;
        }

        return Optional.ofNullable(type);
    }

    private Optional<Type> trySymbol() {
        Type type = null;

        if (!hasNext()) {
            return Optional.empty();
        }

        char c1 = pop();

        if (hasNext()) {
            char c2 = pop();

            type = switch ("" + c1 + c2) {
                case "==" -> Type.EQ;
                case "!=" -> Type.NEQ;
                case ">=" -> Type.GTEQ;
                case "<=" -> Type.LTEQ;
                default -> null;
            };

            if (type == null) {
                pos--;
            }
        }

        if (type == null) {
            type = switch (c1) {
                case '+' -> Type.ADD;
                case '-' -> Type.SUB;
                case '*' -> Type.MUL;
                case '/' -> Type.DIV;
                case '^' -> Type.POW;
                case '%' -> Type.REM;
                case '>' -> Type.GT;
                case '<' -> Type.LT;
                case '{' -> Type.OPEN_BLOCK;
                case '}' -> Type.CLOSE_BLOCK;
                case '(' -> Type.OPEN_PAREN;
                case ')' -> Type.CLOSE_PAREN;
                default -> null;
            };

            if (type == null) {
                pos--;
            }
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
