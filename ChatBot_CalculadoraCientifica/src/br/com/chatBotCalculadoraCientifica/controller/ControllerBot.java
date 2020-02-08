package br.com.chatBotCalculadoraCientifica.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.chatBotCalculadoraCientifica.model.ManagerBotMessage;
import br.com.chatBotCalculadoraCientifica.model.ManagerProperties;
import br.com.chatBotCalculadoraCientifica.model.ObjectFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.apache.log4j.Logger;

public class ControllerBot {

	/**
	 * Classe responsável por instanciar o bot do Telegram e iniciar as thread para
	 * respostas das mensagens
	 * @param LOGGER,bot-Objeto do Telegram, threadPool-pool de thread, controlas as
	 * thread executadas, mapaMenu - objeto map com as descrições dos menus de comando
	 */

	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
	private TelegramBot bot;
	private ExecutorService threadPool;
	private Hashtable<String, String> mapaMenu;

	/**
	 * Construtor da classe ControllerBot, ele devolve um objeto do tipo TelegramBot
	 */

	public ControllerBot() {

		LOGGER.info("[INICIO] Iniciando o construtor da ControllerBot");

		this.bot = new ObjectFactory().getBotInstance();
		// Passa o bot para o gerenciamento do controllerBotMessage
		ManagerBotMessage.setBotForReading(this.bot);

		LOGGER.info("[FIM] Finalizando o construtor da ControllerBot");
	}

	/**
	 * Método que roda inicia as thred e a aplicação depois da classe instanciada
	 */
	public void runBot() {

		LOGGER.info("[INICIO] Rodando o método runBot");

		this.mapaMenu = ManagerProperties.menu();
		ManagerBotMessage.setOffset(0);
		ManagerBotMessage.setMapaMenu(this.mapaMenu);
		ObjectFactory factory = new ObjectFactory();
		this.threadPool = Executors.newCachedThreadPool(factory);

		while (true) {
			List<Update> updates = ManagerBotMessage.getUpdates();
			updates.stream().forEach(update -> {
				ManagerBotMessage.nextOffset(update);
				ControlleThread controllerThread = new ControlleThread(update, mapaMenu);
				this.threadPool.execute(controllerThread);

			});

		}

	}

}
