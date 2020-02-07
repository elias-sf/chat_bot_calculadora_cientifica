package br.com.chatBotCalculadoraCientifica.controller;

import java.io.File;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import br.com.chatBotCalculadoraCientifica.model.CalculatorBot;
import br.com.chatBotCalculadoraCientifica.model.ManagerProperties;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;

public class ControllerTask {
    private static final Logger LOGGER = Logger.getGlobal();
    private static AtomicBoolean flagCalculoFazendo = new AtomicBoolean(true);
    private Hashtable<String, String> mapaMenu;
    private CalculatorBot calculadora = new CalculatorBot();

    public ControllerTask(Hashtable<String, String> mapaMenu) {
        this.mapaMenu = mapaMenu;
    }

    public synchronized void executeTask(String comando, Update update) {

        LOGGER.info("[INICIO] do controller de tarefas " + "processar pedido do usu�rio");
        String mensagem = valorMapa(comando);
        Update answerUpdate;
        switch (comando.trim()) {
            case "/CB":
                sendAnswer(mensagem, update);
                answerUpdate = retrieveResponse(update);
                calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
                sendAnswer(mapaMenu.get("menu"), update);

                break;
            case "/CVD":
                System.out.println("Fazendo o CVD");
                sendAnswer(mensagem, update);
                answerUpdate = retrieveResponse(update);
                calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
                sendAnswer(mapaMenu.get("menu"), update);

                break;
            case "/CVI":
                sendAnswer(mensagem, update);
                answerUpdate = retrieveResponse(update);
                calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
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
                sendAnswer(mensagem, update);
                sendAnswer(mapaMenu.get("menu"), update);
                break;
            default:
                if (ControllerTask.flagCalculoFazendo.get()) {
                    mensagem = this.mapaMenu.get("menu");
                    sendAnswer(mensagem, update);
                }
        }
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
        ControllerTask.flagCalculoFazendo.set(false);
        this.calculadora.calculoValor(mensagem, answerUpdate);
        ControllerTask.flagCalculoFazendo.set(true);
    }

    private synchronized void calculaEquacaoSimbolica(String mensagem, Update answerUpdate) {
        ControllerTask.flagCalculoFazendo.set(false);
        this.calculadora.calculoSimbolico(mensagem, answerUpdate);
        ControllerTask.flagCalculoFazendo.set(true);
    }
}
