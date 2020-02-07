package br.com.chatBotCalculadoraCientifica.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * classe que ger�ncias as leituras dos arquivos de propriedades
 *
 * @param Logger,props
 * @throws IOException
 */

public abstract class ManagerProperties {

    private static final Logger LOGGER = Logger.getGlobal();
    private static Properties props = new Properties();

    public static String token() throws FileNotFoundException, IOException {

        LOGGER.info("[INICIO] carregando o token do bot");

        carregarPropriedades("config/token/token.properties");

        LOGGER.info("[FIM] carregando o token do bot");

        return props.getProperty("bot.token");

    }

    public static Hashtable<String, String> menu() {

        LOGGER.info("[INICIO] carregando o menu de op��es do bot");
        Hashtable<String, String> mapaMenu = new Hashtable<>();
        try {
            carregarPropriedades("config/menu/menu.properties");
            mapaMenu.put("menu", props.getProperty("bot.menu.comandos"));
            mapaMenu.put("/CB", props.getProperty("bot.menu.calculo.diverso"));
            mapaMenu.put("/CVD", props.getProperty("bot.menu.derivada.valor"));
            mapaMenu.put("/CSD", props.getProperty("bot.menu.derivada.simbolica"));
            mapaMenu.put("/CVI", props.getProperty("bot.menu.integral.valor"));
            mapaMenu.put("/Ajuda", props.getProperty("bot.menu.ajuda"));
            return mapaMenu;
        } catch (IOException e) {
            LOGGER.severe("Erro a carregar o menu, verifique o caminho ou arquivo de propriedade do token");
            e.printStackTrace();
            mapaMenu.put("erro", "Reinicie o seu bot");
            return mapaMenu;
        }

    }

    public static File carregarImagem() {
        File arquivoImagem = new File("config/image/tabela_calculo.png");
        return arquivoImagem;

    }

    private static void carregarPropriedades(String caminho) throws FileNotFoundException, IOException {
        props.load(new FileInputStream(caminho));
    }
}
