package br.com.chatBotCalculadoraCientifica.model;

import java.io.File;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import org.apache.log4j.Logger;

/**
 * Classe responsável por gerenciar as tarefas(comandos) solicitados
 * respostas das mensagens
 * @param LOGGER
 * @param flagCalculoFazendo - flag de controle para as thread quando forem imprimir o menu dos tipos de comandos,
 * @param mapaMenu - objeto map com as descrições dos menus de comando
 * @param calculadora - objeto responsável pelos calculos.
 */

public class ManagerTask {
	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private static AtomicBoolean flagCalculoFazendo = new AtomicBoolean(true);
    private Hashtable<String, String> mapaMenu;
    private CalculatorBot calculadora = new CalculatorBot();

    /**
	 * Construtor da ManagerTask
	 * @param mapaMenu - objeto map com as descrições dos menus de comando
	 */
    public ManagerTask(Hashtable<String, String> mapaMenu) {
        this.mapaMenu = mapaMenu;
    }

    /**
	 * Metodo responsável pela ação dos comandos do menu
	 * @param comando - mensagem de qual comando o usuário solicitou,
	 * @param update - objeto com mensgem do usuário
	 */
    public synchronized void executeTask(String comando, Update update) {

        LOGGER.info("[INICIO] Rodando o método que executa os comandos do menu");

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
                ManagerBotMessage.enviarFoto(update, arquivoFoto);
                sendAnswer(mapaMenu.get("menu"), update);

                break;
            case "/Ajuda":
                mensagem = this.mapaMenu.get("/Ajuda");
                sendAnswer(mensagem, update);
                sendAnswer(mapaMenu.get("menu"), update);
                break;
            default:
                if (ManagerTask.flagCalculoFazendo.get()) {
                    mensagem = this.mapaMenu.get("menu");
                    sendAnswer(mensagem, update);
                }
        }
        
        LOGGER.info("[FIM] Finalizando o método que executa os comandos do menu");
    }

    /**
   	 * Metodo que retorna o valor do mapa de menus
   	 * @param chave - id de qual item específico do menu deseja recuperar
   	 * @return String - retorna a mensagem específica do menu de comandos
   	 */
    private String valorMapa(String chave) {
        return this.mapaMenu.get(chave);
    }

    /**
   	 * Metodo que chama o método de enviar mensagens
   	 * @param update - objeto com mensgem do usuário, message - mensagem que será enviada ao usuário, 
   	 */
    private synchronized void sendAnswer(String mensagem, Update update) {
        ManagerBotMessage.sendMessage(mensagem, update, new ForceReply());
    }

    /**
   	 * Metodo que retorna a reposta do usuário
   	 * @param update - objeto com mensagem do usuário
   	 * @return Update - resposta do usuário
   	 */
    private synchronized Update retrieveResponse(Update update) {
        return ManagerBotMessage.readAnswer(update);
    }

    /**
   	 * Metodo chama a calculadora de valores númericos 
   	 * @param answerUpdate - objeto com mensagem(resposta) do usuário, mensagem - equação fornecida pelo usuário
   	 */
    private synchronized void calculaEquacaoValor(String mensagem, Update answerUpdate) {
    	LOGGER.info("[INICIO] Rodando o método calculaEquacaoValor");
    	
        ManagerTask.flagCalculoFazendo.set(false);
        this.calculadora.calculoValor(mensagem, answerUpdate);
        ManagerTask.flagCalculoFazendo.set(true);
        
        LOGGER.info("[FIM] Fim do método calculaEquacaoValor");
    }

    /**
   	 * Metodo chama a calculadora de valores simbólicos 
   	 * @param answerUpdate - objeto com mensagem(resposta) do usuário, mensagem - equação fornecida pelo usuário
   	 */
    private synchronized void calculaEquacaoSimbolica(String mensagem, Update answerUpdate) {
    	LOGGER.info("[INICIO] Rodando o método calculaEquacaoSimbolica");
    	
        ManagerTask.flagCalculoFazendo.set(false);
        this.calculadora.calculoSimbolico(mensagem, answerUpdate);
        ManagerTask.flagCalculoFazendo.set(true);
        
        LOGGER.info("[FIM] Fim do método calculaEquacaoSimbolica");
    }
}
