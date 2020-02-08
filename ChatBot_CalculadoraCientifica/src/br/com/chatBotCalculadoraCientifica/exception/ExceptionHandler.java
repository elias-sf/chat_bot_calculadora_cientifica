package br.com.chatBotCalculadoraCientifica.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.log4j.Logger;

/**
 * Classe que lança exceção quando acontece algum erro nas thread.
 */

public class ExceptionHandler extends Throwable implements UncaughtExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger("botCalculadora");

    /**
     * Método que recebe a exceção e devolve uma mensagem do erro lançado, mais a pilha da exceção.
     * @param Thread.
     * @param Throwable.
     */
    
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("[ERRO] Ocorreu um erro ao processar a  thread: " + t.getName() + ", " + e.getMessage());
        e.printStackTrace();
    }
}
