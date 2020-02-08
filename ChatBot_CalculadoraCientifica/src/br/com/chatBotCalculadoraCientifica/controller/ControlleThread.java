package br.com.chatBotCalculadoraCientifica.controller;

import java.util.Hashtable;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;

import br.com.chatBotCalculadoraCientifica.model.ManagerBotMessage;
import br.com.chatBotCalculadoraCientifica.model.ManagerTask;
import org.apache.log4j.Logger;

/**
 * Classe respons�vel por controlar as thread que ser�o executadas, implementando a interface Runnable.
 * @param LOGGER .
 * @param update - objeto com mensgem do usu�rio.
 * @param threadPool-pool de thread.
 */

public class ControlleThread implements Runnable {
	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private Update update;
    private Hashtable<String, String> mapaMenu;

	/**
	 * Construtor da ControlleThread.
	 * @param update - objeto com mensgem do usu�rio.
	 * @param threadPool-pool de thread.
	 * @param mapaMenu - objeto map com as descri��es dos menus de comando.
	 */
    
    public ControlleThread(Update update, Hashtable<String, String> mapaMenu) {
        this.update = update;
        this.mapaMenu = mapaMenu;
    }

    /**
	 * M�todo que executa cada thread inicializada.
	 * @throws RuntimeException - exce��o lan�ada durante execu��o. 
	 */
    @Override
    public void run() {

        LOGGER.info("[INICIO] Executando a thread que processa a solicita��o do usu�rio");
        
        if (this.mapaMenu.contains("erro")) {
            ManagerBotMessage.sendMessage(mapaMenu.get("erro"), this.update, new ForceReply());
            throw new RuntimeException("Erro a carregar itens \n \n" + " do menu, por favor reiniciar o bot");
        }
        String comando = this.update.message().text();
        ManagerTask task = new ManagerTask(this.mapaMenu);
        task.executeTask(comando, update);
        
        LOGGER.info("[FIM] Finalizando o m�todo que processa a thread ");

    }

}
