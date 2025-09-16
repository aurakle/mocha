package dev.aurakle.mocha.executable;

import java.util.Arrays;

import dev.aurakle.mocha.compiler.Lexer;

public class Mocha {
    public static void main(String[] args) {
        System.out.println("Meow");
        var lexer = new Lexer("if then else");
        System.out.println(Arrays.toString(lexer.getOrComputeResult()));
    }
}
