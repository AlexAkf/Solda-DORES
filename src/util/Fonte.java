package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Hugo
 * 
 * Classe para definir fonte personalizadas.
 * COMO USAR:
 * .setFont(Fonte.inserirFonte().deriveFont(Complementa aqui com tamanho e estilo da fonte));
 */

public class Fonte {  
    public static Font inserirFonte() {
        try (InputStream is = Fonte.class.getResourceAsStream("/ssrc/fonts/Baloo2-VariableFont_wght.ttf")) {
            if (is == null) {
                throw new IOException("Fonte não encontrada no caminho /fonts/Baloo2-VariableFont_wght.ttf");
            }
            
            Font fonte = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fonte);
            return fonte;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 12);
        }
    }
}