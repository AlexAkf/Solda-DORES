package controllers;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Classe para gerenciar a conexão com o MySQL
 *
 * @author Hugo
 * @author Alex
 */

public class Conexao {

    /* Atributos static final para configurar a conexão com o DB
       private = só quem está nessa classe pode alterar = mais segurança
       final = uma constante = valor não alterável
       Mantenham as constantes em CAPSLOCK para padronizar */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/soldadores";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; // No mundo real, a senha e o usuário não ficariam expostos nas linhas de código

    /**
     * Cria e retorna uma conexão com o DB
     *
     * @return Connection ativa com o MySQL
     */
    public static Connection getConexao() {
        try {
            Class.forName(DRIVER);  // Carrega o driver do JDBC (Java Database Connectivity)
            return DriverManager.getConnection(URL, USUARIO, SENHA); // Cria a conexão
        } catch (ClassNotFoundException | SQLException erro) {

            /* .getMessage() adicionado para mostrar apenas a mensagem do erro, sem as coisas estranhas
               JOptionPane inserido para que o usuário também seja notificado */
            JOptionPane.showMessageDialog(null, "Algo de errado aconteceu com a conexão com o banco de dados.\nErro: " + erro.getMessage());
            throw new RuntimeException(erro);   // Relança o erro original, cru com os termos técnicos
        }
    }
}