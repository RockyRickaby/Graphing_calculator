package com.graphcalc;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class Main {
    public static void main(String[] args) {
        Function func = new Function("f", "a + b + c", "a", "b", "c");
        System.out.println(func.calculate(1,2,3));
        loopExample();
        Function f = new Function(" ");
        System.out.println(f.calculate(1));
    }

    // good practice!
    public static void loopExample() {
        Function f = new Function("f", "-x", "x");
        Argument x = new Argument("x");
        Expression e = new Expression("f(x)", f, x);
        for (int i = 0; i < 100; i++) {
            x.setArgumentValue(i);
            System.out.println((int) e.calculate());
        }
    }

    // merely an example of what can be done with the math library
    public static void example() {
        System.out.println("Hello world!");
        Function At = new Function("At(b, h) = 1/2 * b * h");
        Expression e1 = new Expression("At(2,4)", At);
        System.out.println(e1.getExpressionString());
        System.out.println(e1.calculate());
        e1.defineArgument("b", 3);
        e1.defineArgument("h", 10);
        System.out.println(e1.getExpressionString());
        System.out.println(e1.calculate());
        Argument b = new Argument("b = 3");
        Argument h = new Argument("h = 10");
        Expression e2 = new Expression("At(b, h)", At, b, h);
        System.out.println(e2.getExpressionString());
        System.out.println(e2.calculate());
    }
}