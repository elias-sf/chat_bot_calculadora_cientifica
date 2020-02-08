package br.com.chatBotCalculadoraCientifica.model;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
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
import org.apache.log4j.Logger;

/**
 * Classe responsável pelo gerenciamento das mensagem recebidas e enviadas para o usuário.
 * @param LOGGER,sendResponse- objeto que encapsula e envia as mensagem.
 * @param baseResponse- objeto que descreve ao usuário mensagem será enviada.
 * @param botForReading- objeto Telegram para leitura e envio das mensagem.
 * @param offset - vetor para selecionar as mensagem recebidas.
 * @param mapaMenu - objeto map com as descrições dos menus de comando.
 * 
 */

public abstract class ManagerBotMessage {

	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private static SendResponse sendResponse;
    private static BaseResponse baseResponse;
    private static TelegramBot botForReading;
    private static int offset;
    private static Hashtable<String, String> mapaMenu;
    
    /**
	 * Método que chama o método envia mensagem para usuário.
	 * @param update-objeto com mensagem do usuário.
	 * @param message-mensagem que será enviada ao usuário.
	 */

    public static void sendMessage(String message, Update update) {
        sendMessage(message, update, new ForceReply());
    }
    
    /**
   	 * Método que envia mensagem para usuário.
   	 * @param update - objeto com mensgem do usuário.
   	 * @param message - mensagem que será enviada ao usuário. 
   	 * @param keyboard- objeto de mensagem customizada.
   	 * @throws RuntimeException. 
   	 */

    public synchronized static void sendMessage(String message, Update update, Keyboard keyboard) {
        
    	LOGGER.info("[INICIO] Rodando método que retorna uma resposta ao usuário");
    	
    	baseResponse = new ObjectFactory().getBotInstance()
                .execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

        sendResponse = new ObjectFactory().getBotInstance()
                .execute(new SendMessage(update.message().chat().id(), message).parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true).disableNotification(true).replyMarkup(keyboard));

        if (!sendResponse.isOk()) {
            LOGGER.error("Falha ao enviar mensagem");
            LOGGER.error("Mensagem nÃ£o enviada" + update.message().text());
            throw new RuntimeException("Erro ao enviar a mensagem para usuÃ¡rio");
        }
        
    	LOGGER.info("[FIM] Fim do método que retorna uma resposta ao usuário");

    }

    /**
   	 * Método que recupera as repostas enviadas pelo usuário.
   	 * @param update - objeto com mensgem do usuário.
   	 * @return Update.
   	 */
    
    public synchronized static Update readAnswer(Update update) {
    	LOGGER.info("[INICIO] Rodando método que recupera resposta do usuário a um comando");
    	
        nextOffset(update);
        List<Update> answerUpdates = null;
        while (answerUpdates == null || answerUpdates.isEmpty()) {
            answerUpdates = getUpdates();
        }
        
        LOGGER.info("[FIM] Fim do método que recupera resposta do usuário a um comando");
        
        return answerUpdates.get(0);
        
        
    }
    
    /**
   	 * Método que controla o vetor de seleção da lista de mensagens.
   	 * @param update - objeto com mensgem do usuário.
   	 */

    public static void nextOffset(Update update) {
        offset = update.updateId() + 1;
    }

    /**
   	 * Método que recupera as mensagens enviadas pelo usuário.
   	 * @param update-objeto com mensagem do usuário.
   	 * @return List-lista de updates com todas as mensagens enviadas.
   	 */
    
    public synchronized static List<Update> getUpdates() {
    	
    	LOGGER.info("[INICIO] Rodando método que recupera mensagens do usuário");
    	
        List<Update> updates = null;
        updates = botForReading.execute(new GetUpdates().limit(100).offset(offset)).updates();
        
    	LOGGER.info("[FIM] Fim do método que recupera mensagens do usuário");

        
        return updates;

    }

    /**
   	 * Método que envia a foto solicitado pelo usuário.
   	 * @param update objeto com mensgem do usuário.
   	 * @param arquivoFoto-foto das equações das expressões matemáticas.
   	 */
    public static void enviarFoto(Update update, File arquivoFoto) {
        botForReading.execute(new SendPhoto(update.message().chat().id(),
                arquivoFoto));
    }

    /**
   	 * Método que retorna objeto TelegramBot.
   	 * @return TelegramBot-objeto do tipo TelegramBot.
   	 */
    public static TelegramBot getBotForReading() {
        return botForReading;
    }

    /**
   	 * Método que recebe objeto TelegramBot.
   	 * @param botForReading- objeto do tipo TelegramBot.
   	 */
    public static void setBotForReading(TelegramBot botForReading) {
        ManagerBotMessage.botForReading = botForReading;
    }
    
    /**
   	 * Método que recebe valor do vetor de mensagens.
   	 * @param valor- valor do ponteiro do vetor.
   	 */
    public static void setOffset(int valor) {
        offset = valor;
    }

    /**
   	 * Método que retorna objeto menu de comandos do bot
   	 * @return Hashtable- map com todas as informações do menu de comandos
   	 */
    public static Hashtable<String, String> getMapaMenu() {
        return mapaMenu;
    }

    /**
   	 * Método que recebe objeto menu de comandos do bot.
   	 * @param mapaMenuCarregado-map com todas as informações do menu de comandos.
   	 */
    public static void setMapaMenu(Hashtable<String, String> mapaMenuCarregado) {
        mapaMenu = mapaMenuCarregado;
    }

}
