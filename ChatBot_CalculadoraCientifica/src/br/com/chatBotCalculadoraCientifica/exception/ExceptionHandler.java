package br.com.chatBotCalculadoraCientifica.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.log4j.Logger;

/**
 * Classe que lan�a exce��o quando acontece algum erro nas thread.
 */

public class ExceptionHandler extends Throwable implements UncaughtExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger("botCalculadora");

    /**
     * M�todo que recebe a exce��o e devolve uma mensagem do erro lan�ado, mais a pilha da exce��o.
     * @param Thread.
     * @param Throwable.
     */
    
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("[ERRO] Ocorreu um erro ao processar a  thread: " + t.getName() + ", " + e.getMessage());
        e.printStackTrace();
    }
}
