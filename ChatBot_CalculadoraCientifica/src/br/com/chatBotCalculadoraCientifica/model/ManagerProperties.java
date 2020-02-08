package br.com.chatBotCalculadoraCientifica.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * Classe respons�vel por gerenciar e recuperar os arquivos de propriedades
 * respostas das mensagens
 * @param LOGGER
 * @param props - Objeto que le os arquivos de propriedade
 */

public abstract class ManagerProperties {

	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private static Properties props = new Properties();
    
    /**
     * M�todo respons�vel por recuperar o token do bot
     * @return String-informa��o da chave(id) do bot
     */

    public static String token() throws FileNotFoundException, IOException {

        LOGGER.info("[INICIO] Rodando o m�todo que carrega token do bot");

        carregarPropriedades("config/token/token.properties");

        LOGGER.info("[FIM] Finalizando o m�todo que carrega token do bot");
        
        return props.getProperty("bot.token");

    }

    /**
     * M�todo respons�vel por recuperar as informa��es do menu de comando
     * @return Hashtable - objeto map com as descri��es dos menus de comando
     */
    
    public static Hashtable<String, String> menu() {

        LOGGER.info("[INICIO] Carregando o menu de comandos");
        
        Hashtable<String, String> mapaMenu = new Hashtable<>();
        try {
            carregarPropriedades("config/menu/menu.properties");
            mapaMenu.put("menu", props.getProperty("bot.menu.comandos"));
            mapaMenu.put("/CB", props.getProperty("bot.menu.calculo.diverso"));
            mapaMenu.put("/CVD", props.getProperty("bot.menu.derivada.valor"));
            mapaMenu.put("/CSD", props.getProperty("bot.menu.derivada.simbolica"));
            mapaMenu.put("/CVI", props.getProperty("bot.menu.integral.valor"));
            mapaMenu.put("/Ajuda", props.getProperty("bot.menu.ajuda"));
            
            LOGGER.info("[FIM] Finalizando o m�todo que carrega o menu de comandos");
            return mapaMenu;
        } catch (IOException e) {
            LOGGER.error("Erro a carregar o menu, verifique o caminho ou arquivo de propriedade");
            e.printStackTrace();
            mapaMenu.put("erro", "Reinicie o seu bot");
            return mapaMenu;
        }

        
    }
    
    /**
     * M�todo respons�vel por recuperar a imagem das express�es matem�ticas
     * @return File - retorna a imagem das express�es
     */
    public static File carregarImagem() {
    	LOGGER.info("[INICIO] Rodando o m�todo que carrega imagem das express�es");
    	
        File arquivoImagem = new File("config/image/tabela_calculo.png");
        return arquivoImagem;

    }

    /**
     * M�todo respons�vel por recuperar os arquivos de propriedades
     */
    private static void carregarPropriedades(String caminho) throws FileNotFoundException, IOException {
        props.load(new FileInputStream(caminho));
    }
}
