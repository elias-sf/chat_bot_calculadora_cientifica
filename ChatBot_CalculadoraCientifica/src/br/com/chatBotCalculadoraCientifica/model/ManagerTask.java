package br.com.chatBotCalculadoraCientifica.model;

import java.io.File;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import org.apache.log4j.Logger;

/**
 * Classe respons�vel por gerenciar as tarefas(comandos) solicitados
 * respostas das mensagens
 * @param LOGGER
 * @param flagCalculoFazendo - flag de controle para as thread quando forem imprimir o menu dos tipos de comandos,
 * @param mapaMenu - objeto map com as descri��es dos menus de comando
 * @param calculadora - objeto respons�vel pelos calculos.
 */

public class ManagerTask {
	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private static AtomicBoolean flagCalculoFazendo = new AtomicBoolean(true);
    private Hashtable<String, String> mapaMenu;
    private CalculatorBot calculadora = new CalculatorBot();

    /**
	 * Construtor da ManagerTask
	 * @param mapaMenu - objeto map com as descri��es dos menus de comando
	 */
    public ManagerTask(Hashtable<String, String> mapaMenu) {
        this.mapaMenu = mapaMenu;
    }

    /**
	 * Metodo respons�vel pela a��o dos comandos do menu
	 * @param comando - mensagem de qual comando o usu�rio solicitou,
	 * @param update - objeto com mensgem do usu�rio
	 */
    public synchronized void executeTask(String comando, Update update) {

        LOGGER.info("[INICIO] Rodando o m�todo que executa os comandos do menu");

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
        
        LOGGER.info("[FIM] Finalizando o m�todo que executa os comandos do menu");
    }

    /**
   	 * Metodo que retorna o valor do mapa de menus
   	 * @param chave - id de qual item espec�fico do menu deseja recuperar
   	 * @return String - retorna a mensagem espec�fica do menu de comandos
   	 */
    private String valorMapa(String chave) {
        return this.mapaMenu.get(chave);
    }

    /**
   	 * Metodo que chama o m�todo de enviar mensagens
   	 * @param update - objeto com mensgem do usu�rio, message - mensagem que ser� enviada ao usu�rio, 
   	 */
    private synchronized void sendAnswer(String mensagem, Update update) {
        ManagerBotMessage.sendMessage(mensagem, update, new ForceReply());
    }

    /**
   	 * Metodo que retorna a reposta do usu�rio
   	 * @param update - objeto com mensagem do usu�rio
   	 * @return Update - resposta do usu�rio
   	 */
    private synchronized Update retrieveResponse(Update update) {
        return ManagerBotMessage.readAnswer(update);
    }

    /**
   	 * Metodo chama a calculadora de valores n�mericos 
   	 * @param answerUpdate - objeto com mensagem(resposta) do usu�rio, mensagem - equa��o fornecida pelo usu�rio
   	 */
    private synchronized void calculaEquacaoValor(String mensagem, Update answerUpdate) {
    	LOGGER.info("[INICIO] Rodando o m�todo calculaEquacaoValor");
    	
        ManagerTask.flagCalculoFazendo.set(false);
        this.calculadora.calculoValor(mensagem, answerUpdate);
        ManagerTask.flagCalculoFazendo.set(true);
        
        LOGGER.info("[FIM] Fim do m�todo calculaEquacaoValor");
    }

    /**
   	 * Metodo chama a calculadora de valores simb�licos 
   	 * @param answerUpdate - objeto com mensagem(resposta) do usu�rio, mensagem - equa��o fornecida pelo usu�rio
   	 */
    private synchronized void calculaEquacaoSimbolica(String mensagem, Update answerUpdate) {
    	LOGGER.info("[INICIO] Rodando o m�todo calculaEquacaoSimbolica");
    	
        ManagerTask.flagCalculoFazendo.set(false);
        this.calculadora.calculoSimbolico(mensagem, answerUpdate);
        ManagerTask.flagCalculoFazendo.set(true);
        
        LOGGER.info("[FIM] Fim do m�todo calculaEquacaoSimbolica");
    }
}
