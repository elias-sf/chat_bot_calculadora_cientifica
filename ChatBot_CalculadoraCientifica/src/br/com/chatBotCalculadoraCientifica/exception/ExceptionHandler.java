package br.com.chatBotCalculadoraCientifica.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Logger;

public class ExceptionHandler extends Throwable implements UncaughtExceptionHandler {

    private static final Logger LOGGER = Logger.getGlobal();

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.severe("[ERRO] Ocorreu um erro ao processar a  thread: " + t.getName() + ", " + e.getMessage());
        e.printStackTrace();
    }

//	public void ErroCarregarMenuException(String message) {
//		LOGGER.severe(message);
//	}
//	


}
