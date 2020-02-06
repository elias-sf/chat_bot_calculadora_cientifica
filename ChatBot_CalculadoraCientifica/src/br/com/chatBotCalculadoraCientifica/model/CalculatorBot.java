package br.com.chatBotCalculadoraCientifica.model;

import java.text.DecimalFormat;

import org.mariuszgromada.math.mxparser.Expression;

import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;

import br.com.chatBotCalculadoraCientifica.controller.ControllerBotMessage;

public class CalculatorBot {

	private DecimalFormat format = new DecimalFormat("0.###");
	private String erro = "Erro a executar o calculo, por favor reveja a equação \n \n ";

	public synchronized void calculoValor(String equação, Update update) {
		String resultado;
		Double resultadoCalculo = new Expression(equação.trim()).calculate();
		if ("NaN".equals(resultadoCalculo.toString())) {
			resultado = "Erro a executar o calculo, por favor reveja a equação \n \n ";
		} else {
			resultado = format.format(resultadoCalculo);
		}
		enviarResultadoCalculo(resultado, update);
	}

	public synchronized void calculoSimbolico(String equação, Update update) {
		String resultado;
		try {
			Calculator calc = new Calculator();
			resultado = calc.differentiate(equação.trim(), "x").get().toString();
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
