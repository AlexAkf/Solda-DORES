package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

// Hugo
// Classe para definir fonte personalizadas.
// COMO USAR:
// .setFont(Fonte.inserirFonte().deriveFont(Complementa aqui com tamanho e estilo da fonte);

public class Fonte {  
    public static Font inserirFonte() {
        try {
            Font fonte = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/Baloo2-VariableFont_wght.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fonte);
            return fonte;
        } catch (IOException | FontFormatException e) {
            return new Font("SansSerif", Font.PLAIN, 12);
        }
    }
}
