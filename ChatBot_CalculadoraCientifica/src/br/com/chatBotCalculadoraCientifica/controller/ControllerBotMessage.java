package br.com.chatBotCalculadoraCientifica.controller;

import java.io.File;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.chatBotCalculadoraCientifica.model.ObjectFactory;

public abstract class ControllerBotMessage {

	private static final Logger LOGGER = Logger.getGlobal();
	private static SendResponse sendResponse;
	private static BaseResponse baseResponse;
	private static TelegramBot botForReading;
	private static int offset;
	private static Hashtable<String, String> mapaMenu;
	
	
	
	public static void sendMessage(String message, Update update) {
		
		                
		sendMessage(message, update, new ForceReply());
	}

	public synchronized static void sendMessage(String message, Update update, Keyboard keyboard) {

		// keyboard = new ForceReply();
		
		//Tratamento de erro das repo
		

		baseResponse = new ObjectFactory().getBotInstance()
				.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

		sendResponse = new ObjectFactory().getBotInstance()
				.execute(new SendMessage(update.message().chat().id(), message).parseMode(ParseMode.HTML)
						.disableWebPagePreview(true).disableNotification(true).replyMarkup(keyboard));

		if (!sendResponse.isOk()) {
			LOGGER.severe("Falha ao enviar mensagem");
			LOGGER.severe("Mensagem não enviada" + update.message().text());
			throw new RuntimeException("Erro ao enviar a mensagem para usuário");
		}
	}

	public synchronized static Update readAnswer(Update update) {
		System.out.println("Entrei no recebeMensagem");
		nextOffset(update);
		List<Update> answerUpdates = null;
		while (answerUpdates == null || answerUpdates.isEmpty()) {
			answerUpdates = getUpdates();
		}
		//System.out.println("ACHADO =========== " + answerUpdates.get(0));
		return answerUpdates.get(0);
	}

	public static void nextOffset(Update update) {
		//System.out.println("OFFESET " + offset);
		offset = update.updateId() + 1;
	}

	public synchronized static List<Update> getUpdates() {

		System.out.println("Fazendo o getUpdates");

		List<Update> updates = null;

		updates = botForReading.execute(new GetUpdates().limit(100).offset(offset)).updates();

		//String qtd = String.valueOf(updates.size());
		
//		Integer qtd = updates.size();
//		
//		System.out.println("QTD MSG REC: " + qtd.toString());
		//System.out.println(updates);

		// if(updates.isEmpty())

		return updates;
		// while (updates == null || updates.isEmpty()) {
		// updates = botForReading.execute(new
		// GetUpdates().limit(100).offset(offset)).updates();
		// if (!updates.isEmpty()) {
		// nextOffset(updates.get(updates.size() - 1));
		// return updates;
		// }
		//// try {
		//// Thread.sleep(1000);
		//// } catch (InterruptedException e) {
		//// LOGGER.log(Level.SEVERE, "[Erro] Erro no método getUpdate no momento de
		// espera da thread");
		//// e.printStackTrace();
		//// }
		// }
		// return updates;

	}
	
	public static void enviarFoto(Update update, File arquivoFoto) {
		botForReading.execute(new SendPhoto(update.message().chat().id(), 
				arquivoFoto));
	}

	public static TelegramBot getBotForReading() {
		return botForReading;
	}

	public static void setBotForReading(TelegramBot botForReading) {
		ControllerBotMessage.botForReading = botForReading;
	}

	public static void setOffset(int valor) {
		offset = valor;
	}

	public static Hashtable<String, String> getMapaMenu() {
		return mapaMenu;
	}

	public static void setMapaMenu(Hashtable<String, String> mapaMenuCarregado) {
		mapaMenu = mapaMenuCarregado;
	}

}
