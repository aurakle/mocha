package dev.aurakle.mocha.executable;

import java.util.Arrays;

import dev.aurakle.mocha.compiler.Lexer;

public class Mocha {
    public static void main(String[] args) {
        var lexer = new Lexer("if then else +-*/{and}(or)!=");
        System.out.println(Arrays.toString(lexer.getOrComputeResult()));
    }
}
