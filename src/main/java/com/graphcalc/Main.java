package com.graphcalc;

import javax.swing.SwingUtilities;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Plot();
            }
        });
        // loopExample();
    }

    // good practice!
    public static void loopExample() {
        Function f = new Function("f(x) = x^2*sin(x^2)");
        Argument x = new Argument("x", 1);
        Expression e = new Expression("f(x)", f, x);

        long it = System.nanoTime();
        e.calculate();
        System.out.println((System.nanoTime() - it) / 1e6);
        System.out.println(f.getComputingTime());

        it = System.nanoTime();
        e.calculate();
        System.out.println((System.nanoTime() - it) / 1e6);
        System.out.println(f.getComputingTime());

        it = System.nanoTime();
        for (int i = 0; i < 5000; i++) {
            f.calculate(i);
            f.calculate(i);
            f.calculate(i);
            f.calculate(i);
            f.calculate(i);
            // System.out.println(f.calculate(i));
        }
        System.out.printf("Time elapsed: %fms\n", (System.nanoTime() - it) / 1e6);
    }

    public static void moreExamples() {
        Function func = new Function("f(a,b,c) = a + b + c");
        System.out.println(func.calculate(1,2,3));
        loopExample();
        //Function f = new Function("f(x) = 5");
        //System.out.println(f.calculate(15));
        Argument a = new Argument("x", 1),
                 k = new Argument("y", 0);
        Expression e = new Expression("x^2 + y^2");
        for (int i = 0; i < func.getArgumentsNumber(); i++) {
            System.out.println(func.getArgument(i).getArgumentName());
        }
        e.addArguments(a, k);
        System.out.println(e.calculate());
        //func = new Function("f(a,b,c) = a + b + c");
        func.setArgumentValue(0, 3);
        System.out.println(func.calculate());
        System.out.println();
    }

    // merely an example of what can be done with the math library
    public static void example() {
        // System.out.println("Hello world!");
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