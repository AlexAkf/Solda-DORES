package controllers;

import java.sql.*;
/**
 * @author Hugo, Alex
 * Classe para gerenciar a conexão com o MySQL
 */

public class Conexao{

    /* 
     *Atributos static final para configurar a conexão com o BD
     * final = uma constante = valor não alterável
     *
     * Manter constantes em CAPSLOCK
     */

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/soldadores";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConexao(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);   // Cria a conexão
        } catch (ClassNotFoundException | SQLException ex){
            throw new RuntimeException("Algo aconteceu de errado com a conexão com o banco.\nErro: " + ex.getMessage());    // getMessage adicionado para mostrar apenas a mensagem do erro 
        }
    }
}