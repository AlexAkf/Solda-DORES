package dao;

import controllers.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Usuarios;

/**
 * Classe responsável pelos comandos SQL referente a tabela de usuários do DB
 * e de certa forma também responsável pelo processo de login e autenticação,
 * substituindo nossa antiga classe de login e conexão genérica
 * 
 * Padrão DAO (Data Accesss Object), é ele quem faz o processo de CRUD (Create,
 * Read, Update e Delete)
 * 
 * @author Alex
 */

public class UsuariosDAO {

   // Abertura de conexão generalizada para não precisar abrir e fechar várias
   // conexões e sobrecarregar o DB
   private final Connection conn;

   /**
    * Basicamente quando o DAO for instanciado ele vai fazer uma conexão pelo
    * Conexao.java
    * 
    * @throws SQLException quando houver erro, ele irá para a tela de quem chamou o
    *                      erro
    */

   public UsuariosDAO() throws SQLException {
      conn = Conexao.getConexao();
   }

   // Retorna um usuário com base no login informado
   public Usuarios buscar_login(String login) throws SQLException {
      String sql = "SELECT * FROM usuarios WHERE login=?";

      // Processo "moderno" de abertura de conexão com fechamento automático
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setString(1, login);
         try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
               var usuario = new Usuarios();
               usuario.setId(rs.getInt("id"));
               usuario.setNome(rs.getString("nome"));
               usuario.setSenha(rs.getString("senha"));
               usuario.setPerfil(rs.getString("perfil"));
               usuario.setAtivo(rs.getBoolean("condicao"));
               return usuario;
            }
         }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Erro ao buscar usuário.\nErro: " + ex.getMessage());
         throw ex;
      }
      return null;
   }

   // ! =============== CREATE ===============
   public void inserir(Usuarios usuario) throws SQLException {

      /*
       * Método que insere os usuários no DB
       * ele tem o PreparedStatement para evitar SQL injection,
       * que é um tipo de ataque onde usam comandos do próprio SQL
       */

      String sql = """
              INSERT INTO usuarios(nome, cpf, email, login, senha, senha_padrao, cargo, condicao, perfil,
                                      id_supervisor, sinete, validade_certificado, ultima_solda
                                   )
              VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

      // Mais uma vez o estilo try-with-resources para fechamento automático do stmt
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setString(1, usuario.getNome());
         stmt.setString(2, usuario.getCpf());
         stmt.setString(3, usuario.getEmail());
         stmt.setString(4, usuario.getLogin());
         stmt.setString(5, usuario.getSenha());
         stmt.setBoolean(6, usuario.isSenhaPadrao());
         stmt.setString(7, usuario.getCargo());
         stmt.setBoolean(8, usuario.isAtivo());
         stmt.setString(9, usuario.getPerfil());

         // Acessando o objeto de Usuarios para pegar o id e poder usar as suas
         // informações
         if (usuario.getSupervisor() != null && usuario.getSupervisor().getId() != 0) {
            stmt.setInt(10, usuario.getSupervisor().getId());
         } else {
            stmt.setNull(10, Types.INTEGER);
         }

         stmt.setString(11, usuario.getSinete());

         if (usuario.getValidade() != null) {
            stmt.setDate(12, Date.valueOf(usuario.getValidade()));
         } else {
            stmt.setNull(12, Types.DATE);
         }

         if (usuario.getSolda() != null) {
            stmt.setDate(13, Date.valueOf(usuario.getSolda()));
         } else {
            stmt.setNull(13, Types.DATE);
         }

         stmt.executeUpdate(); // Executa o comando SQL no DB
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Erro ao inserir usuário.\nErro: " + ex.getMessage());
         throw ex;
      }
   }

   // ! =============== READ ===============
   public List<Usuarios> listar() throws SQLException {
      List<Usuarios> lista = new ArrayList<>();
      String sql = """
            SELECT usuarios.*,
               usuarios_supervisor.id AS supervisor_id,
               usuarios_supervisor.nome AS supervisor_nome
            FROM usuarios
            LEFT JOIN usuarios AS usuarios_supervisor ON usuarios.id_supervisor = usuarios_supervisor.id
            """;

      try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
         while (rs.next()) {
            var usuario = new Usuarios();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setEmail(rs.getString("email"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setSenhaPadrao(rs.getBoolean("senha_padrao"));
            usuario.setCargo(rs.getString("cargo"));
            usuario.setAtivo(rs.getBoolean("condicao"));
            usuario.setPerfil(rs.getString("perfil"));
            usuario.setSinete(rs.getString("sinete"));

            if (rs.getDate("validade_certificado") != null) {
               usuario.setValidade(rs.getDate("validade_certificado").toLocalDate());
            } else {
               usuario.setValidade(null);
            }

            if (rs.getDate("ultima_solda") != null) {
               usuario.setSolda(rs.getDate("ultima_solda").toLocalDate());
            } else {
               usuario.setSolda(null);
            }

            // O supervisor é um objeto interno, por isso a auto referência no model
            if (rs.getObject("supervisor_id") != null) {
               var supervisor = new Usuarios();
               supervisor.setId(rs.getInt("supervisor_id"));
               supervisor.setNome(rs.getString("supervisor_nome"));
               usuario.setSupervisor(supervisor);
            }

            lista.add(usuario);
         }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Erro ao listar usuários.\nErro: " + ex.getMessage());
         throw ex;
      }
      return lista;
   }

   // ! =============== UPDATE ===============
   // O processo de update é uma cópia descarada do create, nem vale a pena o
   // comentário
   public void atualizar(Usuarios usuario) throws SQLException {
      String sql = """
            UPDATE usuarios SET
               nome=?, cpf=?, email=?, login=?, senha=?, senha_padrao=?, cargo=?,
               condicao=?, perfil=?, id_supervisor=?, sinete=?, validade_certificado=?,
               ultima_solda=?
            WHERE id=?
            """;
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setString(1, usuario.getNome());
         stmt.setString(2, usuario.getCpf());
         stmt.setString(3, usuario.getEmail());
         stmt.setString(4, usuario.getLogin());
         stmt.setString(5, usuario.getSenha());
         stmt.setBoolean(6, usuario.isSenhaPadrao());
         stmt.setString(7, usuario.getCargo());
         stmt.setBoolean(8, usuario.isAtivo());
         stmt.setString(9, usuario.getPerfil());

         if (usuario.getSupervisor() != null && usuario.getSupervisor().getId() != 0) {
            stmt.setInt(10, usuario.getSupervisor().getId());
         } else {
            stmt.setNull(10, Types.INTEGER);
         }

         stmt.setString(11, usuario.getSinete());

         if (usuario.getValidade() != null) {
            stmt.setDate(12, Date.valueOf(usuario.getValidade()));
         } else {
            stmt.setNull(12, Types.DATE);
         }

         if (usuario.getSolda() != null) {
            stmt.setDate(13, Date.valueOf(usuario.getSolda()));
         } else {
            stmt.setNull(13, Types.DATE);
         }

         stmt.setInt(14, usuario.getId());
         stmt.executeUpdate();
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário.\nErro: " + ex.getMessage());
         throw ex;
      }
   }

   // ! =============== DELETE ===============
   public void deletar(int id) throws SQLException {
      String sql = "DELETE FROM usuarios WHERE id=?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setInt(1, id);
         stmt.executeUpdate();
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Erro ao deletar usuário.\nErro: " + ex.getMessage());
         throw ex;
      }
   }
}