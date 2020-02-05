package br.com.chatBotCalculadoraCientifica.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import br.com.chatBotCalculadoraCientifica.model.ManagerProperties;
import br.com.chatBotCalculadoraCientifica.model.ObjectFactory;

public class ControllerBot {

	/**
	 * classe que que controla as ações do bot
	 * 
	 * @param Logger,bot,offset,threadPool,mapaMenu,sendResponse,baseResponse
	 * 
	 * @throws sem
	 *             exceções
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
		//List<Update> updates = bot.execute(new GetUpdates().limit(100).offset(0)).updates();

//		if (updates != null || !updates.isEmpty()) {
//			//ControllerBotMessage.nextOffset(updates.get((updates.size()) - 1));
//			ControllerBotMessage.nextOffset(updates.get((updates.size())));
//		}

		LOGGER.info("[FIM] Bot inicializado");
	}

	public void runBot() {

		// implementar o mapa
		this.mapaMenu = ManagerProperties.menu();

		ThreadFactory defaultFactory = Executors.defaultThreadFactory();

		this.threadPool = Executors.newCachedThreadPool( new ObjectFactory(defaultFactory));

		
		while (true) {
			
			List<Update> updates = ControllerBotMessage.getUpdates();
			updates.stream().forEach(update -> {
				
				ControllerBotMessage.nextOffset(update);
				//this.lock.lock();
				ControlleThread controllerThread = new ControlleThread(update, mapaMenu);
				threadPool.execute(controllerThread);
//				try {
//					controllerThread.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				//this.lock.unlock();
				
			});
			
		}

	}

//	public Runnable inicializaThread(Update update, Hashtable<String, String> mapaMenu) {
//
//		ControlleThread controlleThread = new ControlleThread(update, mapaMenu);
//		return controlleThread;
//	}

}
