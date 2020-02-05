package br.com.chatBotCalculadoraCientifica.model;

import java.text.DecimalFormat;

import org.mariuszgromada.math.mxparser.Expression;

import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;

import br.com.chatBotCalculadoraCientifica.controller.ControllerBotMessage;

public class CalculatorBot {

	
	private DecimalFormat format = new DecimalFormat("0.###");

//	private Expression inicializadorCalculo(String equa��o) {
//		return new Expression(equa��o);
//	}

	public synchronized void calculoValor(String equa��o, Update update) {
		System.out.println("Entrei na calculadora");
		Double resultadoCalculo = new Expression(equa��o).calculate();
		String resultado = format.format(resultadoCalculo);
		enviarResultadoCalculo(resultado, update);
		//String resultado = format.format(inicializadorCalculo(equa��o).calculate());
//		System.out.println("Resultado " + resultado);
//		ControllerBotMessage.sendMessage(resultado, update);

	}

	public synchronized void calculoSimbolico(String equa��o, Update update) {
		System.out.println("Entrei no : calculo Simbolico " );
		Calculator calc = new Calculator();
		String resultado = calc.differentiate(equa��o,"x").get().toString();
		System.out.println("Resultado " + resultado);
		enviarResultadoCalculo(resultado, update);
	}

	private synchronized void enviarResultadoCalculo(String resultado, Update update) {
		ControllerBotMessage.sendMessage(resultado, update);
	}
}
