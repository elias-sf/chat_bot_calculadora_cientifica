package br.com.chatBotCalculadoraCientifica.model;

import br.com.chatBotCalculadoraCientifica.exception.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * classe que fabrica uma instancia os objetos com padr�o singleton para alguns
 * metodos
 *
 * @param Logger,bot,defaultFactory,numero
 * @throws IOException
 */

public class ObjectFactory implements ThreadFactory {

    private static final Logger LOGGER = Logger.getGlobal();
    private static TelegramBot bot;
    private static int numero;
    private ThreadFactory defaultFactory;


    public ObjectFactory() {
    }

    public ObjectFactory(ThreadFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    public TelegramBot getBotInstance() {
        LOGGER.info("[INICIO] fabricando instancia do bot");

        try {
            if (bot == null) {
                bot = new TelegramBot(ManagerProperties.token());
            }
        } catch (IOException e) {
            LOGGER.severe("[ERRO] falha a gerar o objeto bot, rever o gerente de propriedades do token");
            e.printStackTrace();
        }

        LOGGER.info("[FIM] fabricando instancia do bot");

        return bot;
    }

    @Override
    public Thread newThread(Runnable tarefa) {
        LOGGER.log(Level.INFO, "=============[INICIO] fabricando instancia da thread - Inicia a f�brica===================");
        // criando uma thread usando para fabrica padr�o

        Thread thread = new Thread(tarefa, "Thread Task User-" + numero);
        // personalizando a thread, colocando exce��es que podem ocorrer com outras
        // treads e vincular ao m�todo main
        numero++;
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        LOGGER.log(Level.INFO, "[FIM] fim da fabrica��o da instancia da thread");

        return thread;
    }
}
