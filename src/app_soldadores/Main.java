package app_soldadores;

import tela_login.TelaLogin;

/**
 * Classe executora, ela quem dará vida ao nosso software
 * 
 * @author Alex
 * @author Hugo
 * @author Rafael Moreira
 * @author Rafael Silva
 * @author Rafhael Muzzi
 * @version
 * @since 13-10-225
 */

public class Main {
    public static void main(String[] args) {
        TelaLogin login = new TelaLogin();
        login.setVisible(true);
    }
}