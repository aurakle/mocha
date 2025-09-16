package dev.aurakle.mocha.compiler;

public record Token(Type type, String slice, int start) {
    public static enum Type {
        IF,
        THEN,
        ELSE
    }
}
