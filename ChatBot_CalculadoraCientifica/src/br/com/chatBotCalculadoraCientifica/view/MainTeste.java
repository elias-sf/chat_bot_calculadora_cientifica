package br.com.chatBotCalculadoraCientifica.view;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;

import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class MainTeste {
	
	public static void main(String[] args) throws IOException {
		//Criação do objeto bot com as informações de acesso
				TelegramBot bot = new TelegramBot("1035161133:AAEI_6Ere1QcXYkhVA8319z2uwzgUl8nz4k");

				//objeto responsável por receber as mensagens
				GetUpdatesResponse updatesResponse;
				//objeto responsável por gerenciar o envio de respostas
				SendResponse sendResponse;
				//objeto responsável por gerenciar o envio de ações do chat
				BaseResponse baseResponse;
				
				//controle de off-set, isto é, a partir deste ID será lido as mensagens pendentes na fila
				int m=0;
				
				//loop infinito pode ser alterado por algum timer de intervalo curto
				while (true){
				
					//executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
					updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
					
					//lista de mensagens
					List<Update> updates = updatesResponse.updates();

					//análise de cada ação da mensagem
					for (Update update : updates) {
						
						//atualização do off-set
						m = update.updateId()+1;
						
						System.out.println("Recebendo mensagem:"+ update.message().text());
						
						//envio de "Escrevendo" antes de enviar a resposta
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						//verificação de ação de chat foi enviada com sucesso
						
						if("imagem".equals(update.message().text())) {
							
//							Base64.encode(FileUtils.readFileToByteArray(new File("config/image/tabela_calculo.png")));
//							
//							FileUtils.readFileToByteArray(new File("config/image/tabela_calculo.png"))
//							
//							File arquivoImagem = new File("config/image/tabela_calculo.png");
							
							SendPhoto send = new SendPhoto().setChatId("@calculadora_cientifica_bot")
							.setPhoto(new File("config/image/tabela_calculo.png"))
							.setCaption("Teste");
							
						
							
//							SendPhoto send = new SendPhoto("@calculadora_cientifica_bot",
//									FileUtils.readFileToByteArray(new File("config/image/tabela_calculo.png"))).
//							caption("teste");
							
							
							//sendResponse = bot.execute(new SendMessage(update.message().chat().id(),send));
							
						}
						
						System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
						
						//envio da mensagem de resposta
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Não foi"));
						//verificação de mensagem enviada com sucesso
						System.out.println("Mensagem Enviada?" +sendResponse.isOk());
						
					}

				}
	}
}
