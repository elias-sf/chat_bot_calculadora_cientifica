package br.com.chatBotCalculadoraCientifica.model;

import java.text.DecimalFormat;

import com.deriv.calculator.Calculator;
import com.pengrad.telegrambot.model.Update;
import org.mariuszgromada.math.mxparser.Expression;
import org.apache.log4j.Logger;

/**
 * Classe respons�vel pelos calculos solicitado pelo usu�rio.
 */

public class CalculatorBot {
	
	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
	private DecimalFormat format = new DecimalFormat("0.###");
    private String erro = "Erro a executar o calculo, por favor reveja a equacao \n \n ";

    /**
   	 * M�todo que executa o calculo de cada thread, retornando um valor n�merico.
   	 * @param equacao- express�o matem�tica para ser calculada.
   	 * @param update- objeto com mensgem do usu�rio.
   	 */
    
	public synchronized void calculoValor(String equacao, Update update) {
		
		LOGGER.info("[INICIO] Rodando calculo que retona o valor n�merico da equa��o");
		
		String resultado;
		Double resultadoCalculo = new Expression(equacao.trim()).calculate();
		if ("NaN".equals(resultadoCalculo.toString())) {
			resultado = getErro();
		} else {
			resultado = format.format(resultadoCalculo);
		}
		enviarResultadoCalculo(resultado, update);
		
		LOGGER.info("[INICIO] Finalizando m�todo calculoValor ");

		
	}
	
	/**
   	 * M�todo que executa o calculo de cada thread, retornando um valor simb�lico.
   	 * @param equacao- express�o matem�tica para ser calculada.
   	 * @param update-objeto com mensgem do usu�rio.
   	 */

    public synchronized void calculoSimbolico(String equacao, Update update) {
    	
    	LOGGER.info("[INICIO] Rodando calculo que retona o valor simb�lico da equa��o");
    	
        String resultado;
        try {
            Calculator calc = new Calculator();
            resultado = calc.differentiate(equacao.trim(), "x").get().toString();
            if ("0".equals(resultado))
                resultado = getErro();
        } catch (Exception e) {
            resultado = getErro();
            LOGGER.error("[ERRO] Equa��o gerou um erro a ser calculada");
        }
        enviarResultadoCalculo(resultado, update);
        
        LOGGER.info("[INICIO] Finalizando m�todo calculoSimbolico ");
    }
    
    /**
   	 * M�todo que envia o resultado para o usu�rio.
   	 * @param resultado- express�o matem�tica calculada. 
   	 * @param update - objeto com mensgem do usu�rio.
   	 */

    private synchronized void enviarResultadoCalculo(String resultado, Update update) {
        ManagerBotMessage.sendMessage(resultado, update);
    }
    
    /**
   	 * M�todo que retorna um erro do calculo.
   	 * @return String - mensagem de erro do calculo.
   	 */

    public String getErro() {
        return erro;
    }
}
