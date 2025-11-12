package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Usuario;
import models.Empresa;
import javax.swing.JOptionPane;

/**
 * Classe responsável pelos comandos SQL referente a tabela de usuários
 * ou seja, o processo de login e autenticação também, subistituindo as antigas classes
 * de login e loginconexao
 * 
 * Padrão DAO (Data Access Object) -> ele quem faz os CRUD
 * 
 * @author Alex
 */
public class UsuarioDAO {

    // Abertura de conexão generalizada, pra não precisar abrir e fechar multiplas conexões e sobrecarregar o banco
    private final Connection conn;

    /**
     * Basicamente quando o DAO for instanciado ele vai fazer uma conexão pelo conexao.java
     * e esse @throws SQLException vai fazer com que, 
     * quando haja um erro, ela irá para a tela de quem chamou, seja na de login, cadastro ou whatever
     * @throws java.sql.SQLException
     */
    public UsuarioDAO() throws SQLException {
        conn = Conexao.getConexao();
    }

    // retorna um usuario com base no login informado
    public Usuario buscarLogin(String login) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE login=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // processo moderno de abertura de conexao com fechamento automatico
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    usuario.setCargo(rs.getString("cargo"));
                    usuario.setCondicao(rs.getBoolean("condicao"));
                    return usuario;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar usuário.\nErro: " + ex.getMessage());
            throw ex;
        }
        return null;
    }

    
    // ================================ AUTENTICAR LOGIN ================================
    public void inserir(Usuario usuario) throws SQLException {
        /**
         * método que insere os usuários no banco de dados
         * ele tem o PreparedStatement para evitar SQL injection -> que é um tipo de ataque onde usam comandos do próprio SQL
         * mais uma vez com o estilo try-with-resources para fechamento automático do stmt
         */
        String sql = """
            INSERT INTO usuarios
            (nome, cpf, email, login, senha, senha_padrao, cargo, condicao,
             perfil, fk_empresa, id_supervisor, sinete, validade_certificado, ultima_solda)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getLogin());
            stmt.setString(5, usuario.getSenha());
            stmt.setBoolean(6, usuario.isSenhaPadrao());
            stmt.setString(7, usuario.getCargo());
            stmt.setBoolean(8, usuario.isCondicao());
            stmt.setString(9, usuario.getPerfil());

            // acessando o objeto de empresa para pegar o id e poder usar suas informações
            if (usuario.getEmpresa() != null && usuario.getEmpresa().getId() != null) {
                stmt.setInt(10, usuario.getEmpresa().getId());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            // mesmo processo da empresa mas agora com o supervisor
            if (usuario.getSupervisor() != null && usuario.getSupervisor().getId() != 0) {
                stmt.setInt(11, usuario.getSupervisor().getId());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setString(12, usuario.getSinete());

                if (usuario.getValidadeCertificado() != null) {
                    stmt.setDate(13, Date.valueOf(usuario.getValidadeCertificado()));
                } else {
                    stmt.setNull(13, Types.DATE);
                }

                if (usuario.getUltimaSolda() != null) {
                    stmt.setDate(14, Date.valueOf(usuario.getUltimaSolda()));
                } else {
                    stmt.setNull(14, Types.DATE);
                }

            // Aqui executa o comando SQL no banco
            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir usuário.\nErro: " + ex.getMessage());
            throw ex;
        }
    }
    
    
    // ================================ READ ================================
    public List<Usuario> listar() throws SQLException{
        //método que lista todos os usuários do banco de dados
        List<Usuario> lista = new ArrayList<>();

        String sql = """
            SELECT 
                usuarios.*, 
                empresas.id AS empresa_id, 
                empresas.nome AS empresa_nome,
                usuarios_supervisor.id AS supervisor_id, 
                usuarios_supervisor.nome AS supervisor_nome
            FROM usuarios
            LEFT JOIN empresas ON usuarios.fk_empresa = empresas.id
            LEFT JOIN usuarios AS usuarios_supervisor ON usuarios.id_supervisor = usuarios_supervisor.id
        """;

        try(Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setSenhaPadrao(rs.getBoolean("senha_padrao"));
                usuario.setCargo(rs.getString("cargo"));
                usuario.setCondicao(rs.getBoolean("condicao"));
                usuario.setPerfil(rs.getString("perfil"));
                usuario.setSinete(rs.getString("sinete"));

                Date validade = rs.getDate("validade_certificado");
                if (validade != null)
                    usuario.setValidadeCertificado(validade.toLocalDate());

                Date ultima = rs.getDate("ultima_solda");
                if (ultima != null)
                    usuario.setUltimaSolda(ultima.toLocalDate());

                // Empresa (objeto interno)
                if (rs.getObject("empresa_id") != null) {
                    Empresa emp = new Empresa();
                    emp.setId(rs.getInt("empresa_id"));
                    emp.setNome(rs.getString("empresa_nome"));
                    usuario.setEmpresa(emp);
                }

                // Supervisor (objeto interno)
                if (rs.getObject("supervisor_id") != null) {
                    Usuario supervisor = new Usuario();
                    supervisor.setId(rs.getInt("supervisor_id"));
                    supervisor.setNome(rs.getString("supervisor_nome"));
                    usuario.setSupervisor(supervisor);
                }

                lista.add(usuario);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuários.\n" + ex.getMessage());
            throw ex;
        }

        return lista;
    }
    
    
    
   
    
    
    
    
    
    
    
    
    


    // ================================
    // UPDATE (ATUALIZAR USUÁRIO)
    // ================================
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = """
            UPDATE usuarios SET
                nome=?, cpf=?, email=?, login=?, senha=?, senha_padrao=?,
                cargo=?, condicao=?, perfil=?, fk_empresa=?, id_supervisor=?,
                sinete=?, validade_certificado=?, ultima_solda=?
            WHERE id=?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getLogin());
            ps.setString(5, usuario.getSenha());
            ps.setBoolean(6, usuario.isSenhaPadrao());
            ps.setString(7, usuario.getCargo());
            ps.setBoolean(8, usuario.isCondicao());
            ps.setString(9, usuario.getPerfil());

            if (usuario.getEmpresa() != null && usuario.getEmpresa().getId() != null)
                ps.setInt(10, usuario.getEmpresa().getId());
            else
                ps.setNull(10, Types.INTEGER);

            if (usuario.getSupervisor() != null && usuario.getSupervisor().getId() != 0) {
                ps.setInt(11, usuario.getSupervisor().getId());
            } else {
                ps.setNull(11, Types.INTEGER);
            }
            ps.setString(12, usuario.getSinete());

            if (usuario.getValidadeCertificado() != null)
                ps.setDate(13, Date.valueOf(usuario.getValidadeCertificado()));
            else
                ps.setNull(13, Types.DATE);

            if (usuario.getUltimaSolda() != null)
                ps.setDate(14, Date.valueOf(usuario.getUltimaSolda()));
            else
                ps.setNull(14, Types.DATE);

            ps.setInt(15, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário.\n" + ex.getMessage());
            throw ex;
        }
    }

    // ================================
    // DELETE (REMOVER USUÁRIO)
    // ================================
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar usuário.\n" + ex.getMessage());
            throw ex;
        }
    }
}