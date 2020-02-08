package br.com.chatBotCalculadoraCientifica.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * Classe responsável por gerenciar e recuperar os arquivos de propriedades respostas das mensagens.
 * @param LOGGER.
 * @param props - Objeto que lê os arquivos de propriedade.
 */

public abstract class ManagerProperties {

	private static final Logger LOGGER = Logger.getLogger("botCalculadora");
    private static Properties props = new Properties();
    
    /**
     * Método responsável por recuperar o token do bot.
     * @return String-informação da chave(id) do bot.
     */

    public static String token() throws FileNotFoundException, IOException {

        LOGGER.info("[INICIO] Rodando o método que carrega token do bot");

        carregarPropriedades("config/token/token.properties");

        LOGGER.info("[FIM] Finalizando o método que carrega token do bot");
        
        return props.getProperty("bot.token");

    }

    /**
     * Método responsável por recuperar as informações do menu de comando.
     * @return Hashtable - objeto map com as descrições dos menus de comando.
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
            
            LOGGER.info("[FIM] Finalizando o método que carrega o menu de comandos");
            return mapaMenu;
        } catch (IOException e) {
            LOGGER.error("Erro a carregar o menu, verifique o caminho ou arquivo de propriedade");
            e.printStackTrace();
            mapaMenu.put("erro", "Reinicie o seu bot");
            return mapaMenu;
        }

        
    }
    
    /**
     * Método responsável por recuperar a imagem das expressões matemáticas.
     * @return File - retorna a imagem das expressões.
     */
    public static File carregarImagem() {
    	LOGGER.info("[INICIO] Rodando o método que carrega imagem das expressões");
    	
        File arquivoImagem = new File("config/image/tabela_calculo.png");
        return arquivoImagem;

    }

    /**
     * Método responsável por recuperar os arquivos de propriedades.
     */
    private static void carregarPropriedades(String caminho) throws FileNotFoundException, IOException {
        props.load(new FileInputStream(caminho));
    }
}
