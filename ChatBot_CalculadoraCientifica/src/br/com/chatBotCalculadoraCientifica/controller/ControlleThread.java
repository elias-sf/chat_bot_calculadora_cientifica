package br.com.chatBotCalculadoraCientifica.controller;

import java.util.Hashtable;
import java.util.logging.Logger;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;

import br.com.chatBotCalculadoraCientifica.model.ManagerBotMessage;
import br.com.chatBotCalculadoraCientifica.model.ManagerTask;

public class ControlleThread implements Runnable {
    private static final Logger LOGGER = Logger.getGlobal();
    private Update update;
    private Hashtable<String, String> mapaMenu;

    public ControlleThread(Update update, Hashtable<String, String> mapaMenu) {
        this.update = update;
        this.mapaMenu = mapaMenu;
    }

    @Override
    public void run() {

        LOGGER.info("[INICIO] do controller das thread " + "processar pedido do usu�rio");
        if (this.mapaMenu.contains("erro")) {
            ManagerBotMessage.sendMessage(mapaMenu.get("erro"), this.update, new ForceReply());
            throw new RuntimeException("Erro a carregar itens \n \n" + " do menu, por favor reiniciar o bot");
        }
        String comando = this.update.message().text();
        ManagerTask task = new ManagerTask(this.mapaMenu);
        task.executeTask(comando, update);

    }

}
