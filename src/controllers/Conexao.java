package controllers;

import java.sql.*;

/**
 * Classe para gerenciar a conexão com o MySQL
 * 
 * @author Hugo
 * @author Alex
 */

public class Conexao {

    /*
     * Atributos static final para configurar a conexão com o DB
     * private = só quem está nessa classe pode alterar = mais segurança
     * final = uma constante = valor não alterável
     * Mantenham as constantes em CAPSLOCK para padronizar
     */

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/soldadores";
    private static final String USER = "root";
    private static final String PASS = "root"; // No mundo real a senha e usuário não ficariam expostos nas linhas de
                                               // código

    public static Connection getConexao() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS); // Cria a conexão
        } catch (ClassNotFoundException | SQLException ex) {
            // .getMessage() adicionado para mostrar apenas a mensagem do erro, sem aquelas
            // coisas estranhas a mais
            throw new RuntimeException(
                    "Algo de errado aconteceu com a conexão com o banco de dados.\nErro: " + ex.getMessage());
        }
    }
}