package br.com.chatBotCalculadoraCientifica.model;

import java.text.DecimalFormat;

import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;
import org.mariuszgromada.math.mxparser.Expression;

public class CalculatorBot {

    private DecimalFormat format = new DecimalFormat("0.###");
    private String erro = "Erro a executar o calculo, por favor reveja a equacao \n \n ";

    public synchronized void calculoValor(String equacao, Update update) {
        String resultado;
        Double resultadoCalculo = new Expression(equacao.trim()).calculate();
        if ("NaN".equals(resultadoCalculo.toString())) {
            resultado = getErro();
        } else {
            resultado = format.format(resultadoCalculo);
        }
        enviarResultadoCalculo(resultado, update);
    }

    public synchronized void calculoSimbolico(String equacao, Update update) {
        String resultado;
        try {
            Calculator calc = new Calculator();
            resultado = calc.differentiate(equacao.trim(), "x").get().toString();
            if ("0".equals(resultado))
                resultado = getErro();
        } catch (Exception e) {
            resultado = getErro();
        }
        enviarResultadoCalculo(resultado, update);
    }

    private synchronized void enviarResultadoCalculo(String resultado, Update update) {
        ManagerBotMessage.sendMessage(resultado, update);
    }

    public String getErro() {
        return erro;
    }
}
