package br.com.chatBotCalculadoraCientifica.controller;

import java.io.File;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;

import br.com.chatBotCalculadoraCientifica.model.CalculatorBot;
import br.com.chatBotCalculadoraCientifica.model.ManagerProperties;

public class ControllerTask {
	private static final Logger LOGGER = Logger.getGlobal();
	// private Update update;
	private Hashtable<String, String> mapaMenu;
	private CalculatorBot calculadora = new CalculatorBot();
	private int contador = 0;
	private static AtomicBoolean flagCalculoFazendo = new AtomicBoolean(false);
	// public ControllerTask(Update update, Hashtable<String, String> mapaMenu) {
	// this.update = update;
	// }

	public ControllerTask(Hashtable<String, String> mapaMenu) {
		this.mapaMenu = mapaMenu;
	}

	public synchronized void executeTask(String comando, Update update) {

		
		System.out.println("Contador: " + this.contador);
		Set<Thread> todasAsThreads = Thread.getAllStackTraces().keySet();
		System.out.println("QTD Thread: " + todasAsThreads.size());

		for (Thread thread : todasAsThreads) {
			System.out.println("Nome da thread " + thread.getName());
		}

		LOGGER.info("[INICIO] do controller de tarefas " + "processar pedido do usuário");
		//
		// if (this.mapaMenu.contains("erro")) {
		// ControllerBotMessage.sendMessage(mapaMenu.get("erro"), this.update, new
		// ForceReply());
		// throw new RuntimeException("Erro a carregar itens\"\r\n" + "do menu, por
		// favor reiniciar o bot");
		// }
		// comando.substring(1,comando.length())
		String mensagem = valorMapa(comando);
		System.out.println(comando);
		Update answerUpdate;
		switch (comando.trim()) {
		case "/CB":
			System.out.println("----------Fazendo o CB");
			System.out.println("---------------Nome da thread " + Thread.currentThread().getName());
			sendAnswer(mensagem, update);
			answerUpdate = retrieveResponse(update);
			calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
			sendAnswer(mapaMenu.get("menu"), update);

			// List<Update> updatesDeals = ControllerBotMessage.recebeMensagem(update);
			// ControllerBotMessage.sendMessage(mensagem, update, new ForceReply());
			// Update answerUpdate = ControllerBotMessage.readAnswer(update);
			// ControllerBotMessage.sendMessage(answerUpdate.message().text(), answerUpdate,
			// new ForceReply());
			// this.calculadora.calculoValor(answerUpdate.message().text(), answerUpdate);

			break;
		case "/CVD":
			System.out.println("Fazendo o CVD");
			sendAnswer(mensagem, update);
			answerUpdate = retrieveResponse(update);
			calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
			sendAnswer(mapaMenu.get("menu"), update);
			break;
		case "/CVI":
			// System.out.println("8888888888888888888888888Entrou que entrou :" +
			// Thread.currentThread().getName());
			// System.out.println("8888888888888888888888888 FLAG :" +
			// flagCalculoFazendo.get());
			// System.out.println("Fazendo o CVI");
			sendAnswer(mensagem, update);
			answerUpdate = retrieveResponse(update);
			calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
			// this.flagCalculoFazendo.set(false);
			sendAnswer(mapaMenu.get("menu"), update);
			break;
		case "/CSD":
			sendAnswer(mensagem, update);
			answerUpdate = retrieveResponse(update);
			calculaEquacaoSimbolica(answerUpdate.message().text(), answerUpdate);
			sendAnswer(mapaMenu.get("menu"), update);
			break;
		case "/IMG":
			File arquivoFoto = ManagerProperties.carregarImagem();
			ControllerBotMessage.enviarFoto(update, arquivoFoto);
			sendAnswer(mapaMenu.get("menu"), update);
			break;
		case "/Ajuda":
			mensagem = this.mapaMenu.get("/Ajuda");
			// ControllerBotMessage.sendMessage(mensagem, update, new ForceReply());
			sendAnswer(mensagem, update);
			sendAnswer(mapaMenu.get("menu"), update);
			break;
		case "menu":
		case "/start":
			// Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
			System.out.println("UUUUUUUUUUU Thread MENU :" + Thread.currentThread().getName());
			System.out.println("UUUUUUUUUUUUUUUUUUUUU FLAG :" + flagCalculoFazendo.get());
//			if (this.flagCalculoFazendo.get()) {
//				try {
//					System.out.println("****************Thread que parou :" + Thread.currentThread().getName());
//					this.wait();
//					System.out.println("****************Thread que voltou :" + Thread.currentThread().getName());
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			mensagem = this.mapaMenu.get("menu");
			// ControllerBotMessage.sendMessage(mensagem, update, new ForceReply());
			sendAnswer(mensagem, update);
			break;
		// }

		//
		}

		// if (!this.flagCalculoFazendo.get()) {
		// sendAnswer(mapaMenu.get("menu"), update);
		// }
		// aCABANDO RETIRAR O CONTADOR
		contador++;
		// this.notifyAll();
	}

	private String valorMapa(String chave) {
		return this.mapaMenu.get(chave);
	}

	private synchronized void sendAnswer(String mensagem, Update update) {
		ControllerBotMessage.sendMessage(mensagem, update, new ForceReply());
	}

	private synchronized Update retrieveResponse(Update update) {
		return ControllerBotMessage.readAnswer(update);
	}

	private synchronized void calculaEquacaoValor(String mensagem, Update answerUpdate) {
		this.flagCalculoFazendo.set(true);
		System.out.println("8888888888888888888888888Entrou que entrou :" + Thread.currentThread().getName());
		System.out.println("8888888888888888888888888 FLAG ENTRADA:" + flagCalculoFazendo.get());
		this.calculadora.calculoValor(mensagem, answerUpdate);
		this.flagCalculoFazendo.set(false);
		System.out.println("8888888888888888888888888 FLAG SAIDA:" + flagCalculoFazendo.get());

	}

	private synchronized void calculaEquacaoSimbolica(String mensagem, Update answerUpdate) {
		System.out.println("8888888888888888888888888Entrou que entrou :" + Thread.currentThread().getName());
		this.flagCalculoFazendo.set(true);
		this.calculadora.calculoSimbolico(mensagem, answerUpdate);
		this.flagCalculoFazendo.set(false);
	}
}
