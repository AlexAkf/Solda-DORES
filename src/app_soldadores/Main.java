package app_soldadores;

import javax.swing.ImageIcon;
import tela_login.TelaLogin;

/**
 * Classe executora, é a principal do sistema. Ela quem dará vida ao nosso
 * software iniciando o app e exibindo a tela de login
 *
 * @author Alex
 * @author Hugo
 * @author Rafael Moreira
 * @author Rafael Silva
 * @author Rafhael Muzzi
 * @version
 * @since 13-10-2025
 */

public class Main {

    public static void main(String[] args) {
        var login = new TelaLogin();
        login.setVisible(true);
    }
}