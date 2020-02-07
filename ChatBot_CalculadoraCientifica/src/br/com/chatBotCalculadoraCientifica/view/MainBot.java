package br.com.chatBotCalculadoraCientifica.view;

import br.com.chatBotCalculadoraCientifica.controller.ControllerBot;

/**
 * classe que inicializa o bot
 *
 * @param args
 * @throws sem exce��es
 */

public class MainBot {

    public static void main(String[] args) {

        ControllerBot controleBot = new ControllerBot();
        controleBot.runBot();

    }

}
