package br.com.chatBotCalculadoraCientifica.view;

import com.deriv.calculator.Calculator;
import org.mariuszgromada.math.mxparser.Expression;

public class TesteCalculo {

    public static void main(String[] args) throws Exception {
        // integral polinomios
        Expression et2 = new Expression("int(sin(3*x^2) + 1*x^2, x, -0, 2 )");
        System.out.println("Resultado integral2 " + et2.calculate());

        //derivada polinomios
        Calculator calc = new Calculator();
        String a = calc.differentiate("ln(x^2)/e^(x^2) + (3*x)^4x", "x").get().toString();
        System.out.println(a);

        Calculator calc2 = new Calculator();
        String a1 = calc2.differentiate("ln(x^2) + tan(3*x^4) + 2", "x").get().toString();
        System.out.println(a1);
    }
}
