package br.com.chatBotCalculadoraCientifica.model;

import java.text.DecimalFormat;

import org.mariuszgromada.math.mxparser.Expression;

import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;

import br.com.chatBotCalculadoraCientifica.controller.ControllerBotMessage;

public class CalculatorBot {

	private DecimalFormat format = new DecimalFormat("0.###");
	private String erro = "Erro a executar o calculo, por favor reveja a equa��o \n \n ";

	// private Expression inicializadorCalculo(String equa��o) {
	// return new Expression(equa��o);
	// }

	public synchronized void calculoValor(String equa��o, Update update) {
		System.out.println("/////////////Entrei na calculadora " + Thread.currentThread().getName());
		String resultado;
		Double resultadoCalculo = new Expression(equa��o.trim()).calculate();
		System.out.println("///////////Resultado " + resultadoCalculo.toString());
		if ("NaN".equals(resultadoCalculo.toString())) {
			resultado = "Erro a executar o calculo, por favor reveja a equa��o \n \n ";
		} else {
			resultado = format.format(resultadoCalculo);
		}
		enviarResultadoCalculo(resultado, update);
		// String resultado = format.format(inicializadorCalculo(equa��o).calculate());
		// System.out.println("Resultado " + resultado);
		// ControllerBotMessage.sendMessage(resultado, update);

	}

	public synchronized void calculoSimbolico(String equa��o, Update update) {
		System.out.println("Entrei no : calculo Simbolico ");
		String resultado;
		try {
			Calculator calc = new Calculator();
			resultado = calc.differentiate(equa��o.trim(), "x").get().toString();
			if ("0".equals(resultado))
				resultado = getErro();
		} catch (Exception e) {
			resultado = getErro();
		}
		System.out.println("Resultado " + resultado);
		enviarResultadoCalculo(resultado, update);
	}

	private synchronized void enviarResultadoCalculo(String resultado, Update update) {
		ControllerBotMessage.sendMessage(resultado, update);
	}

	public String getErro() {
		return erro;
	}
}
