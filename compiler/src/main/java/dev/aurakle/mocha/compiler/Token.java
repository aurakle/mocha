package dev.aurakle.mocha.compiler;

public record Token(Type type, String slice, LineColumn pos) {
    public static enum Type {
        IF,
        THEN,
        ELSE,
        CON,
        MUT,
        AND,
        OR,
        EQ,
        NEQ,
        GT,
        LT,
        GTEQ,
        LTEQ,
        ADD,
        SUB,
        MUL,
        DIV,
        REM,
        POW,
        NOT,
        TYPE,
        ARROW_LEFT,
        ARROW_RIGHT,
        OPEN_BLOCK,
        CLOSE_BLOCK,
        OPEN_PAREN,
        CLOSE_PAREN,
    }
}
