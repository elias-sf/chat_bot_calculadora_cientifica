package br.com.chatBotCalculadoraCientifica.view;

import br.com.chatBotCalculadoraCientifica.controller.ControllerBot;

/**
 * Classe Main que inicia a aplicação
 */

public class MainBot {

    public static void main(String[] args) {

        ControllerBot controleBot = new ControllerBot();
        controleBot.runBot();

    }

}
