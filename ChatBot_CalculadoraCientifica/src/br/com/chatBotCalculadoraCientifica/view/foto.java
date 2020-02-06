package br.com.chatBotCalculadoraCientifica.view;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

public class foto extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onUpdateReceived(Update arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public String getBotToken() {
        // TODO Auto-generated method stub
        return null;
    }

    public void name() {
        SendPhoto send = new SendPhoto().setChatId("@calculadora_cientifica_bot")
                .setPhoto(new File("config/image/tabela_calculo.png"))
                .setCaption("Teste");
    }
}
