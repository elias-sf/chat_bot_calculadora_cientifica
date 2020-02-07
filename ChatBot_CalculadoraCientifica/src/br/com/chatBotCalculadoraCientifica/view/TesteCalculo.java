package br.com.chatBotCalculadoraCientifica.view;

import com.deriv.calculator.Calculator;
import org.mariuszgromada.math.mxparser.Expression;

public class TesteCalculo {

    public static void main(String[] args) throws Exception {
        // integral polinomios
        Expression et2 = new Expression("int(sin(3*x^2) + 1*x^2, x, -0, 2 )");
        System.out.println("Resultado integral2 " + et2.calculate());

        try {

            Calculator calc2 = new Calculator();
            System.out.println(calc2.differentiate("tan(2*c^3)", "x").get().toString());
        } catch (Exception e1) {
            System.err.println("Erro");
        }
    }
}
