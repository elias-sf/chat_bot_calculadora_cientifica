package br.com.chatBotCalculadoraCientifica.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.log4j.Logger;

public class MainTeste {

    private static final Logger LOGGER = Logger.getLogger("botCalculadora");

    public static void main(String[] args) throws IOException {
        // Cria��o do objeto bot com as informa��es de acesso

        LOGGER.info("[INICIO] Iniciando o construtor");

//		switch (str) {
//		case !match.find():
//			
//			break;
//
//		default:
//			break;
//		}

        TelegramBot bot = new TelegramBot("1035161133:AAEI_6Ere1QcXYkhVA8319z2uwzgUl8nz4k");

        // objeto respons�vel por receber as mensagens
        GetUpdatesResponse updatesResponse;
        // objeto respons�vel por gerenciar o envio de respostas
        SendResponse sendResponse;
        // objeto respons�vel por gerenciar o envio de a��es do chat
        BaseResponse baseResponse;

        // controle de off-set, isto �, a partir deste ID ser� lido as mensagens
        // pendentes na fila
        int m = 0;

        // loop infinito pode ser alterado por algum timer de intervalo curto
        while (true) {

            // executa comando no Telegram para obter as mensagens pendentes a partir de um
            // off-set (limite inicial)
            updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

            // lista de mensagens
            List<Update> updates = updatesResponse.updates();

            // an�lise de cada a��o da mensagem
            for (Update update : updates) {

                // atualiza��o do off-set
                m = update.updateId() + 1;

                System.out.println("Recebendo mensagem:" + update.message().text());

                // envio de "Escrevendo" antes de enviar a resposta
                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
                // verifica��o de a��o de chat foi enviada com sucesso

                if ("i".equals(update.message().text())) {

                    // Base64.encode(FileUtils.readFileToByteArray(new
                    // File("config/image/tabela_calculo.png")));
                    //
                    // FileUtils.readFileToByteArray(new File("config/image/tabela_calculo.png"))
                    //
                    // File arquivoImagem = new File("config/image/tabela_calculo.png");

                    // SendPhoto send = new SendPhoto().setChatId("@calculadora_cientifica_bot")
                    // .setPhoto(new File("config/image/tabela_calculo.png"))
                    // .setCaption("Teste");
                    //
                    // //sendResponse = bot.execute(new SendMessage(send,"hai"));
                    // SendDocument dc = new SendDocument("@calculadora_cientifica_bot", new
                    // File("config/image/tabela_calculo.png"));
                    // bot.execute(send);
                    //

                    // SendPhoto send = new SendPhoto("@calculadora_cientifica_bot",
                    // FileUtils.readFileToByteArray(new File("config/image/tabela_calculo.png"))).
                    // caption("teste");

                    bot.execute(
                            new SendPhoto(update.message().chat().id(), new File("config/image/tabela_calculo.png")));

                    // sendResponse = bot.execute(new
                    // SendMessage(update.message().chat().id(),send));

                }

                System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
                // envio da mensagem de resposta
                sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "N�o foi"));
                // verifica��o de mensagem enviada com sucesso
                System.out.println("Mensagem Enviada?" + sendResponse.isOk());

            }

        }
    }
}
