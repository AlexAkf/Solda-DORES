/*
 * será responsável execução dos comandos sql
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import controllers.Conexao;
import tela_principal.TelaPrincipal;

/**
 *
 * @author fschi
 */
public class LoginConexao {

    public void InserirUsuario() {

        Connection conn = null;

        String sql = "INSERT INTO usuarios (login, senha_hash) VALUES (?, ?)";

        conn = Conexao.getConexao();//conectar ao banco de dados

        PreparedStatement stmt = null;

        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Login.usuario);
            stmt.setString(2, Login.senha);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no banco de dados. Erro: " + ex);
        } finally {
            Conexao.fecharConexao(conn, stmt);
        }
    }

    public void verificarUsuario() {

        Connection conn = null;

        String sql = "SELECT * FROM usuarios WHERE login = '" + Login.usuario + "'";

        conn = Conexao.getConexao();//conectar ao banco de dados

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();

            rs.next();

            String usuario1 = rs.getString("usuario");
            String senha1 = rs.getString("senha");
            System.out.println(usuario1);
            System.out.println(senha1);

            //testar se o usuario é o mesmo digitado
            if (usuario1.equals(Login.usuario) && senha1.equals(Login.senha)) {
                TelaPrincipal tp = new TelaPrincipal();
                tp.setVisible(true);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao entrar no banco de dados. Erro: " + ex);
        } finally {
            Conexao.fecharConexao(conn, stmt);
        }
    }

}
