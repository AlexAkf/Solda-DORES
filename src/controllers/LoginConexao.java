/*
 * será responsável execução dos comandos sql
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import tela_principal.TelaPrincipal;

/**
 * 
 * @author Hugo, Alex
 */

public class LoginConexao {

    public void InserirUsuario() {

        Connection conn = null;

        String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";

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

    public boolean verificarUsuario() {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // Comando alterado para validação de login com usuário ativo
        try {
            
            conn = Conexao.getConexao();//conectar ao banco de dados
            
            /* O banco mostra a senha e o status de atividade do login fornecido,
             * se o SELECT não encontrar nada o rs.next retorna FALSE
             */
            String sqlUsuario = "SELECT senha, condicao FROM usuarios WHERE login = ?";
            
            stmt = conn.prepareStatement(sqlUsuario);
            stmt.setString(1, Login.usuario);
            rs = stmt.executeQuery();
            
            // Confere se o login fornecido está no BD
            if(!rs.next()){
                JOptionPane.showMessageDialog(null, "Usuário incorreto.");
                return false;
            }
            
            // Confere se é uma conta ativa
            boolean contaAtiva = rs.getBoolean("condicao");
            if(!contaAtiva){
                JOptionPane.showMessageDialog(null, "Usuário inativo. Entre em contato com o administrador!");
                return false;
            }
            
            // Confere se a senha bate com o registro no BD
            String sqlSenha = rs.getString("senha");
            if(!sqlSenha.equals(Login.senha)){
                JOptionPane.showMessageDialog(null, "Senha incorreta.");
                return false;
            }
            
            TelaPrincipal tp = new TelaPrincipal();
            tp.setVisible(true);
            return true;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao entrar no banco de dados. Contacte o suporte.\nErro:\n" + ex);
            return false;
        } finally {
            Conexao.fecharConexao(conn, stmt, rs);
        }
    }
}