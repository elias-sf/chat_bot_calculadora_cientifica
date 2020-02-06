package br.com.chatBotCalculadoraCientifica.model;

import br.com.chatBotCalculadoraCientifica.controller.ControllerBotMessage;
import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;
import org.mariuszgromada.math.mxparser.Expression;

import java.text.DecimalFormat;

public class CalculatorBot {
    private DecimalFormat format = new DecimalFormat("0.###");

    public synchronized void calculoValor(String equacao, Update update) {
        System.out.println("Entrei na calculadora");
        Double resultadoCalculo = new Expression(equacao).calculate();
        String resultado = format.format(resultadoCalculo);
        enviarResultadoCalculo(resultado, update);
    }

    public synchronized void calculoSimbolico(String equacao, Update update) {
        System.out.println("Entrei no : calculo Simbolico ");
        Calculator calc = new Calculator();
        String resultado = calc.differentiate(equacao, "x").get().toString();
        System.out.println("Resultado " + resultado);
        enviarResultadoCalculo(resultado, update);
    }

    private synchronized void enviarResultadoCalculo(String resultado, Update update) {
        ControllerBotMessage.sendMessage(resultado, update);
    }
}
