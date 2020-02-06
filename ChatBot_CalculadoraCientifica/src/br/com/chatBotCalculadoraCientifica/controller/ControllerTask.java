package br.com.chatBotCalculadoraCientifica.controller;

import br.com.chatBotCalculadoraCientifica.model.CalculatorBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;

import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Logger;


public class ControllerTask {
    private static final Logger LOGGER = Logger.getGlobal();
    // private Update update;
    private Hashtable<String, String> mapaMenu;
    private CalculatorBot calculadora = new CalculatorBot();
    private int contador = 0;
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

        LOGGER.info("[INICIO] do controller de tarefas " + "processar pedido do usu�rio");
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
                System.out.println("Fazendo o CB");
                sendAnswer(mensagem, update);
                answerUpdate = retrieveResponse(update);
                calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);


                //List<Update> updatesDeals = ControllerBotMessage.recebeMensagem(update);
//			ControllerBotMessage.sendMessage(mensagem, update, new ForceReply());
//			Update answerUpdate = ControllerBotMessage.readAnswer(update);
//			ControllerBotMessage.sendMessage(answerUpdate.message().text(), answerUpdate, new ForceReply());
//			this.calculadora.calculoValor(answerUpdate.message().text(), answerUpdate);

                break;
            case "/CVD":
                System.out.println("Fazendo o CVD");
                sendAnswer(mensagem, update);
                answerUpdate = retrieveResponse(update);
                calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
                break;
            case "/CVI":
                System.out.println("Fazendo o CVI");
                sendAnswer(mensagem, update);
                answerUpdate = retrieveResponse(update);
                calculaEquacaoValor(answerUpdate.message().text(), answerUpdate);
                break;
            case "/CSD":
                System.out.println("Fazendo o CSD");
                sendAnswer(mensagem, update);
                answerUpdate = retrieveResponse(update);
                calculaEquacaoSimbolica(answerUpdate.message().text(), answerUpdate);
                break;
            case "/Ajuda":
                mensagem = this.mapaMenu.get("/Ajuda");
                //ControllerBotMessage.sendMessage(mensagem, update, new ForceReply());
                sendAnswer(mensagem, update);
                break;

            case "/Imagem_tabela_derivada":

                break;

            case "/Imagem_tabela_integral":

                break;
            default:
                mensagem = this.mapaMenu.get("menu");
                //ControllerBotMessage.sendMessage(mensagem, update, new ForceReply());
                sendAnswer(mensagem, update);
                break;
        }

        //aCABANDO RETIRAR O CONTADOR
        contador++;
        LOGGER.info("[FIM] do controller de tarefas " + "processar pedido do usu�rio");
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
        this.calculadora.calculoValor(mensagem, answerUpdate);
    }

    private synchronized void calculaEquacaoSimbolica(String mensagem, Update answerUpdate) {
        this.calculadora.calculoSimbolico(mensagem, answerUpdate);
	}
}
