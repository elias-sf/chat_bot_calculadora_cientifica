package br.com.chatBotCalculadoraCientifica.model;

import java.text.DecimalFormat;

import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;
import org.mariuszgromada.math.mxparser.Expression;
import org.apache.log4j.Logger;

/**
 * Classe responsável pelos calculos solicitado pelo usuário
 */

public class CalculatorBot {
	
	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
	private DecimalFormat format = new DecimalFormat("0.###");
    private String erro = "Erro a executar o calculo, por favor reveja a equacao \n \n ";

    /**
   	 * Método que executa o calculo de cada thread, retornando um valor númerico
   	 * @param equacao- expressão matemática para ser calculada
   	 * @param update- objeto com mensgem do usuário
   	 */
    
	public synchronized void calculoValor(String equacao, Update update) {
		
		LOGGER.info("[INICIO] Rodando calculo que retona o valor númerico da equação");
		
		String resultado;
		Double resultadoCalculo = new Expression(equacao.trim()).calculate();
		if ("NaN".equals(resultadoCalculo.toString())) {
			resultado = getErro();
		} else {
			resultado = format.format(resultadoCalculo);
		}
		enviarResultadoCalculo(resultado, update);
		
		LOGGER.info("[INICIO] Finalizando método calculoValor ");

		
	}
	
	/**
   	 * Método que executa o calculo de cada thread, retornando um valor simbólico
   	 * @param equacao- expressão matemática para ser calculada
   	 * @param update-objeto com mensgem do usuário
   	 */

    public synchronized void calculoSimbolico(String equacao, Update update) {
    	
    	LOGGER.info("[INICIO] Rodando calculo que retona o valor simbólico da equação");
    	
        String resultado;
        try {
            Calculator calc = new Calculator();
            resultado = calc.differentiate(equacao.trim(), "x").get().toString();
            if ("0".equals(resultado))
                resultado = getErro();
        } catch (Exception e) {
            resultado = getErro();
            LOGGER.error("[ERRO] Equação gerou um erro a ser calculada");
        }
        enviarResultadoCalculo(resultado, update);
        
        LOGGER.info("[INICIO] Finalizando método calculoSimbolico ");
    }
    
    /**
   	 * Método que envia o resultado para o usuário.
   	 * @param resultado- expressão matemática calculada,  update - objeto com mensgem do usuário
   	 */

    private synchronized void enviarResultadoCalculo(String resultado, Update update) {
        ManagerBotMessage.sendMessage(resultado, update);
    }
    
    /**
   	 * Método que retorna um erro do calculo.
   	 * @return String - mensagem de erro do calculo.
   	 */

    public String getErro() {
        return erro;
    }
}
