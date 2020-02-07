package br.com.chatBotCalculadoraCientifica.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.chatBotCalculadoraCientifica.model.ManagerProperties;
import br.com.chatBotCalculadoraCientifica.model.ObjectFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.apache.log4j.Logger;

//import java.util.logging.Logger;

public class ControllerBot {

    /**
     * classe que que controla as a��es do bot
     *
     * @param Logger,bot,offset,threadPool,mapaMenu,sendResponse,baseResponse
     * @throws sem
     * exce��es
     */

    //private static final Logger LOGGER = Logger.getGlobal();
    private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private TelegramBot bot;
    private ExecutorService threadPool;
    private Hashtable<String, String> mapaMenu;

    public ControllerBot() {

        LOGGER.info("[INICIO] Iniciando o construtor");

        this.bot = new ObjectFactory().getBotInstance();
        // Passa o bot para o gerenciamento do controllerBotMessage
        ControllerBotMessage.setBotForReading(this.bot);
        LOGGER.info("[FIM] Bot inicializado");
    }

    public void runBot() {

        this.mapaMenu = ManagerProperties.menu();
        ControllerBotMessage.setOffset(0);
        ControllerBotMessage.setMapaMenu(this.mapaMenu);
        ObjectFactory factory = new ObjectFactory();
        this.threadPool = Executors.newCachedThreadPool(factory);

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
