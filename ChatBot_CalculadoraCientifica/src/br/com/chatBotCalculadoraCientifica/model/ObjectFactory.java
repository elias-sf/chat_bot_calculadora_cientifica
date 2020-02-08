package br.com.chatBotCalculadoraCientifica.model;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;

import br.com.chatBotCalculadoraCientifica.exception.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import org.apache.log4j.Logger;


/**
 * Classe responsável por instanciar os objeto do bot e as thread
 * @param LOGGER
 * @param bot-Objeto do Telegram
 * @param numero - identificador das thread inicializadas
 */


public class ObjectFactory implements ThreadFactory {

	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private static TelegramBot bot;
    private static int numero;

    /**
	 * Construtor da classe ObjectFactory
	 */
    public ObjectFactory() {

    }
    
    
    /**
	 * Método que cria(instancia) objeto TelegramBot
	 * @return TelegramBot - objeto TelegramBot para manipulação do chat bot
	 * @throws RuntimeException - exceção lançada durante execução
	 */
    public TelegramBot getBotInstance() {

        LOGGER.info("[INICIO] Iniciando a fabrica de objeto TelegramBot");

        try {
            if (bot == null) {
                bot = new TelegramBot(ManagerProperties.token());
            }
        } catch (IOException e) {
            LOGGER.error("[ERRO] falha a gerar o objeto bot, rever o gerente de propriedades do token");
            e.printStackTrace();
            throw new RuntimeException("Erro a instanciar o objeto TelegramBot - veja se o token foi inicializado");
        }

        LOGGER.info("[FIM] Finalizando a fabrica de objeto TelegramBot");

        return bot;
    }

    /**
   	 * Método que cria(instancia) as threads
   	 * @param tarefa - objeto runnable fornecido para instanciar a thread
   	 * @return Thread - objeto que executa as tarefas solicitadas
   	 */
    @Override
    public Thread newThread(Runnable tarefa) {
        
    	LOGGER.info("[INICIO] Iniciando a fabrica de Thread");
    	
        Thread thread = new Thread(tarefa, "Thread Task User-" + numero);
        numero++;
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        
        LOGGER.info("[INICIO] Finalizando a fabrica de Thread");
        return thread;
    }
}
