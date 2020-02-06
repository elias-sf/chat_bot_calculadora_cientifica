package br.com.chatBotCalculadoraCientifica.controller;

import br.com.chatBotCalculadoraCientifica.model.ManagerProperties;
import br.com.chatBotCalculadoraCientifica.model.ObjectFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class ControllerBot {
    /**
     * classe que que controla as ações do bot
     *
     * @param Logger,bot,offset,threadPool,mapaMenu,sendResponse,baseResponse
     * @throws sem
     * exceções
     */

    private static final Logger LOGGER = Logger.getGlobal();
    private TelegramBot bot;
    private ExecutorService threadPool;
    private Hashtable<String, String> mapaMenu;
    private Lock lock = new ReentrantLock();

    public ControllerBot() {
        LOGGER.info("[INICIO] Iniciando o construtor");

        this.bot = new ObjectFactory().getBotInstance();
        // Passa o bot para o gerenciamento do controllerBotMessage
        ControllerBotMessage.setBotForReading(this.bot);
        ControllerBotMessage.setOffset(0);

        LOGGER.info("[FIM] Bot inicializado");
    }

    public void runBot() {

        // implementar o mapa
        this.mapaMenu = ManagerProperties.menu();

        ThreadFactory defaultFactory = Executors.defaultThreadFactory();

        this.threadPool = Executors.newCachedThreadPool(new ObjectFactory(defaultFactory));

        while (true) {
            List<Update> updates = ControllerBotMessage.getUpdates();
            updates.stream().forEach(update -> {
                ControllerBotMessage.nextOffset(update);

                ControlleThread controllerThread = new ControlleThread(update, mapaMenu);
                threadPool.execute(controllerThread);
            });
        }

    }
}
