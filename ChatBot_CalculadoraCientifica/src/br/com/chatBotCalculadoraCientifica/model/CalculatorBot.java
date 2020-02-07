package br.com.chatBotCalculadoraCientifica.model;

import java.text.DecimalFormat;

import br.com.chatBotCalculadoraCientifica.controller.ControllerBotMessage;
import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;
import org.mariuszgromada.math.mxparser.Expression;

public class CalculatorBot {

    private DecimalFormat format = new DecimalFormat("0.###");
    private String erro = "Erro a executar o calculo, por favor reveja a equa��o \n \n ";

    public synchronized void calculoValor(String equa��o, Update update) {
        String resultado;
        Double resultadoCalculo = new Expression(equa��o.trim()).calculate();
        if ("NaN".equals(resultadoCalculo.toString())) {
            resultado = "Erro a executar o calculo, por favor reveja a equa��o \n \n ";
        } else {
            resultado = format.format(resultadoCalculo);
        }
        enviarResultadoCalculo(resultado, update);
    }

    public synchronized void calculoSimbolico(String equa��o, Update update) {
        String resultado;
        try {
            Calculator calc = new Calculator();
            resultado = calc.differentiate(equa��o.trim(), "x").get().toString();
            if ("0".equals(resultado))
                resultado = getErro();
        } catch (Exception e) {
            resultado = getErro();
        }
        enviarResultadoCalculo(resultado, update);
    }

    private synchronized void enviarResultadoCalculo(String resultado, Update update) {
        ControllerBotMessage.sendMessage(resultado, update);
    }

    public String getErro() {
        return erro;
    }
}
