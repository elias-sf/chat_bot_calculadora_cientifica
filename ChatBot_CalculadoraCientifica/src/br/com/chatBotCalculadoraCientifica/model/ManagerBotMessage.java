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
 * Classe respons�vel pelo gerenciamento das mensagem recebidas e enviadas para o usu�rio.
 * @param LOGGER,sendResponse- objeto que encapsula e envia as mensagem.
 * @param baseResponse- objeto que descreve ao usu�rio mensagem ser� enviada.
 * @param botForReading- objeto Telegram para leitura e envio das mensagem.
 * @param offset - vetor para selecionar as mensagem recebidas.
 * @param mapaMenu - objeto map com as descri��es dos menus de comando.
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
	 * M�todo que chama o m�todo envia mensagem para usu�rio.
	 * @param update-objeto com mensagem do usu�rio.
	 * @param message-mensagem que ser� enviada ao usu�rio.
	 */

    public static void sendMessage(String message, Update update) {
        sendMessage(message, update, new ForceReply());
    }
    
    /**
   	 * M�todo que envia mensagem para usu�rio.
   	 * @param update - objeto com mensgem do usu�rio.
   	 * @param message - mensagem que ser� enviada ao usu�rio. 
   	 * @param keyboard- objeto de mensagem customizada.
   	 * @throws RuntimeException. 
   	 */

    public synchronized static void sendMessage(String message, Update update, Keyboard keyboard) {
        
    	LOGGER.info("[INICIO] Rodando m�todo que retorna uma resposta ao usu�rio");
    	
    	baseResponse = new ObjectFactory().getBotInstance()
                .execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

        sendResponse = new ObjectFactory().getBotInstance()
                .execute(new SendMessage(update.message().chat().id(), message).parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true).disableNotification(true).replyMarkup(keyboard));

        if (!sendResponse.isOk()) {
            LOGGER.error("Falha ao enviar mensagem");
            LOGGER.error("Mensagem não enviada" + update.message().text());
            throw new RuntimeException("Erro ao enviar a mensagem para usuário");
        }
        
    	LOGGER.info("[FIM] Fim do m�todo que retorna uma resposta ao usu�rio");

    }

    /**
   	 * M�todo que recupera as repostas enviadas pelo usu�rio.
   	 * @param update - objeto com mensgem do usu�rio.
   	 * @return Update.
   	 */
    
    public synchronized static Update readAnswer(Update update) {
    	LOGGER.info("[INICIO] Rodando m�todo que recupera resposta do usu�rio a um comando");
    	
        nextOffset(update);
        List<Update> answerUpdates = null;
        while (answerUpdates == null || answerUpdates.isEmpty()) {
            answerUpdates = getUpdates();
        }
        
        LOGGER.info("[FIM] Fim do m�todo que recupera resposta do usu�rio a um comando");
        
        return answerUpdates.get(0);
        
        
    }
    
    /**
   	 * M�todo que controla o vetor de sele��o da lista de mensagens.
   	 * @param update - objeto com mensgem do usu�rio.
   	 */

    public static void nextOffset(Update update) {
        offset = update.updateId() + 1;
    }

    /**
   	 * M�todo que recupera as mensagens enviadas pelo usu�rio.
   	 * @param update-objeto com mensagem do usu�rio.
   	 * @return List-lista de updates com todas as mensagens enviadas.
   	 */
    
    public synchronized static List<Update> getUpdates() {
    	
    	LOGGER.info("[INICIO] Rodando m�todo que recupera mensagens do usu�rio");
    	
        List<Update> updates = null;
        updates = botForReading.execute(new GetUpdates().limit(100).offset(offset)).updates();
        
    	LOGGER.info("[FIM] Fim do m�todo que recupera mensagens do usu�rio");

        
        return updates;

    }

    /**
   	 * M�todo que envia a foto solicitado pelo usu�rio.
   	 * @param update objeto com mensgem do usu�rio.
   	 * @param arquivoFoto-foto das equa��es das express�es matem�ticas.
   	 */
    public static void enviarFoto(Update update, File arquivoFoto) {
        botForReading.execute(new SendPhoto(update.message().chat().id(),
                arquivoFoto));
    }

    /**
   	 * M�todo que retorna objeto TelegramBot.
   	 * @return TelegramBot-objeto do tipo TelegramBot.
   	 */
    public static TelegramBot getBotForReading() {
        return botForReading;
    }

    /**
   	 * M�todo que recebe objeto TelegramBot.
   	 * @param botForReading- objeto do tipo TelegramBot.
   	 */
    public static void setBotForReading(TelegramBot botForReading) {
        ManagerBotMessage.botForReading = botForReading;
    }
    
    /**
   	 * M�todo que recebe valor do vetor de mensagens.
   	 * @param valor- valor do ponteiro do vetor.
   	 */
    public static void setOffset(int valor) {
        offset = valor;
    }

    /**
   	 * M�todo que retorna objeto menu de comandos do bot
   	 * @return Hashtable- map com todas as informa��es do menu de comandos
   	 */
    public static Hashtable<String, String> getMapaMenu() {
        return mapaMenu;
    }

    /**
   	 * M�todo que recebe objeto menu de comandos do bot.
   	 * @param mapaMenuCarregado-map com todas as informa��es do menu de comandos.
   	 */
    public static void setMapaMenu(Hashtable<String, String> mapaMenuCarregado) {
        mapaMenu = mapaMenuCarregado;
    }

}
