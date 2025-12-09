package util;

import java.awt.*;
import java.io.*;

/**
 * Classe genérica criada para definir fontes personalizadas
 * 
 * * COMO USAR?
 * Exemplo com a fonte Poppins
 * Para carregar e aplicar uma fonte com tamanho específico:
 * .setFont(Fonte.inserirFonte("Poppins-Bold.ttf", 24f));
 * 
 * Se precisar de um estilo ou tamanho diferente, recomendo que tenha uma
 * variável de fonte base, e apenas modifique
 * var base = Fonte.inserirFonte("Poppins-Regular.ttf", 15f)
 * .setFont(base.deriveFont(Font.ITALIC, 18f))
 * 
 * @author Hugo
 * @author Alex
 */

public class Fonte {

    // Caminho base de todas as fontes
    private static final String PASTA = "/fonts/";

    /**
     * Aqui vai carregar uma fonte personalizada pelo nome do arquivo e registra
     * 
     * @param arquivo é o nome do arquivo da fonte
     * @param tamanho é o tamanho da fonte
     * @return o resultado é a fonte personalizada
     */

    public static Font inserirFonte(String arquivo, float tamanho) {
        String caminho = PASTA + arquivo;
        try (InputStream is = Fonte.class.getResourceAsStream(caminho)) {
            if (is == null) {
                // Se a fonte não for encontrada, lança uma exceção e retorna a fonte padrão
                throw new IOException("Fonte não encontrada no caminho: " + caminho);
            }

            // Cria a fonte apartir do InputStream, setando com o tamanho inicial
            var fonte = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(tamanho);

            // Registra no ambiente gráfico para que o Java reconheça a fonte
            var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fonte);
            return fonte;
        } catch (IOException | FontFormatException ex) {
            System.err.println("Erro ao carregar a fonte " + arquivo);

            // Fonte padrão se tudo der errado
            return new Font("SansSerif", Font.PLAIN, 12);
        }
    }
}