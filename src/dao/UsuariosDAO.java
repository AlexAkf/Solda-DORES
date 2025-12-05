package dao;

import controllers.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Usuarios;

/**
 * Classe responsável pelos comandos SQL referente a tabela de usuários do DB e
 * de certa forma também responsável pelo processo de login e autenticação,
 * substituindo nossa antiga classe de login e conexão genérica
 *
 * Padrão DAO (Data Accesss Object), é ele quem faz o processo de CRUD (Create,
 * Read, Update e Delete)
 *
 * @author Alex
 */

public class UsuariosDAO {

    // Abertura de conexão generalizada para não precisar abrir e fechar várias conexões e sobrecarregar o DB
    private final Connection CONN;

    /**
     * Basicamente quando o DAO for instanciado ele vai fazer uma conexão pelo
     * Conexao.java
     *
     * @throws SQLException irá para a tela de quem chamou o erro
     */
    
    public UsuariosDAO() throws SQLException {
        CONN = Conexao.getConexao();
    }

    // Retorna um usuário com base no login informado
    public Usuarios buscar_login(String login) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE login=?";

        /* Estilo try-with-resources do stmt, um jeito "moderno" de abertura de conexão
           com fechamento "automático", enquanto pega os dados do DB */
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    var usuario = new Usuarios();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    usuario.setAtivo(rs.getBoolean("condicao"));
                    usuario.setCargo(rs.getString("cargo"));
                    return usuario;
                }
            }
        } catch (SQLException erro) {
            throw erro;
        }
        return null;    // Retorna null pra caso o login não exista
    }

    // ! =============== CREATE ===============
    public void inserir(Usuarios usuario) throws SQLException {

        /* Método que insere os usuários no DB, ele tem o PreparedStatement para evitar
           SQL injection, que é um tipo de ataque onde usam comandos do próprio SQL */
        String sql = """
              INSERT INTO usuarios(nome, cpf, email, login, senha, senha_padrao, cargo, condicao, perfil,
                                      id_supervisor, sinete, validade_certificado, ultima_solda
                                   )
              VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getLogin());
            stmt.setString(5, usuario.getSenha());
            stmt.setBoolean(6, usuario.isSenhaPadrao());
            stmt.setString(7, usuario.getCargo());
            stmt.setBoolean(8, usuario.isAtivo());
            stmt.setString(9, usuario.getPerfil());

            // Acessando um objeto de Usuarios já criado para pegar o id e poder usar as suas informações
            if (usuario.getSupervisor() != null && usuario.getSupervisor().getId() != 0) {
                stmt.setInt(10, usuario.getSupervisor().getId());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            stmt.setString(11, usuario.getSinete());

            // Condição para pegar uma data caso a validade não seja null (no caso, se não for soldador)
            if (usuario.getValidade() != null) {
                stmt.setDate(12, Date.valueOf(usuario.getValidade()));  // Converte o tipo de data do LocalDate para a do MySQL
            } else {
                stmt.setNull(12, Types.DATE);
            }

            // Mesmo esquema que a validade do certificado mas para a solda
            if (usuario.getSolda() != null) {
                stmt.setDate(13, Date.valueOf(usuario.getSolda()));
            } else {
                stmt.setNull(13, Types.DATE);
            }

            stmt.executeUpdate(); // Executa o comando SQL no DB
        } catch (SQLException erro) {
            throw erro;
        }
    }

    // ! =============== READ ===============
    public List<Usuarios> listar() throws SQLException {
        List<Usuarios> lista = new ArrayList<>();   // É preciso criar uma lista dos usuários do banco para fazer a leitura
        
        // Somente usuários ativos são listados por causa do "pseudo delete"
        String sql = """
            SELECT usuarios.*,
               usuarios_supervisor.id AS supervisor_id,
               usuarios_supervisor.nome AS supervisor_nome
            FROM usuarios
            LEFT JOIN usuarios AS usuarios_supervisor ON usuarios.id_supervisor = usuarios_supervisor.id
            WHERE usuarios.condicao = true
            """;

        try (Statement stmt = CONN.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuários.\nErro: " + erro.getMessage());
            throw erro;
        }
        return lista;
    }

    // ! =============== UPDATE ===============
    public void atualizar(Usuarios usuario) throws SQLException {
        // Verifica o sinete antes de atualizar
        if ("soldador".equalsIgnoreCase(usuario.getCargo()) && usuario.getSinete() != null) {
            // Verifica se o sinete já existe pois ele é único por soldador
            String sqlCheck = "SELECT id FROM usuarios WHERE sinete = ? AND id <> ?";
            try (PreparedStatement stmtCheck = CONN.prepareStatement(sqlCheck)) {
                stmtCheck.setString(1, usuario.getSinete());
                stmtCheck.setInt(2, usuario.getId());
                try (ResultSet rs = stmtCheck.executeQuery()) {
                    if (rs.next()) {
                        throw new SQLException("O sinete informado já está em uso por outro soldador.");
                    }
                }
            }
        } else {
            usuario.setSinete(null); // Garante que não terá sinete para quem não for soldador
        }

        // Tirando o comando de update, o resto é só uma cópia descarada do processo de create
        String sql = """
            UPDATE usuarios SET
               nome=?, cpf=?, email=?, login=?, senha=?, senha_padrao=?, cargo=?,
               condicao=?, perfil=?, id_supervisor=?, sinete=?, validade_certificado=?,
               ultima_solda=?
            WHERE id=?
            """;

        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
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
        } catch (SQLException erro) {
            throw erro;
        }
    }

    // ! =============== DELETE ===============
    public void deletar(int id) throws SQLException {
        String sqlDelete = "DELETE FROM usuarios WHERE id=?";
        String sqlInative = "UPDATE usuarios SET condicao = false WHERE id=?";

        // Caminho "padrão" de delete no banco de dados, onde realmente o perfil é apagado
        try (PreparedStatement stmt = CONN.prepareStatement(sqlDelete)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException excecao) {
            // Se bater na exceção, é porque a conta possui registro em tabelas importantes
            try (PreparedStatement stmt = CONN.prepareStatement(sqlInative)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();   // Como não é possível deletar, a conta é inativada
            } catch (SQLException erro) {
                // Bloco de catch vazio para não ter duplo "spam" para o usuário
            }
        }
    }

    // Retorna um usuário com base no id informado. O código é mais do mesmo
    public Usuarios buscar_id(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
                    }
                    if (rs.getDate("ultima_solda") != null) {
                        usuario.setSolda(rs.getDate("ultima_solda").toLocalDate());
                    }

                    // Trecho do supervisor, que foi o ser que deu motivo a esse bloco de pesquisa
                    int id_supervisor = rs.getInt("id_supervisor");
                    if (!rs.wasNull()) {
                        var supervisor = new Usuarios();
                        supervisor.setId(id_supervisor);
                        usuario.setSupervisor(supervisor);
                    }
                    return usuario;
                }
            }
        }
        return null;
    }
    
    public void atualizarSenha(int id, String novaSenha) throws SQLException {
        String sql = "UPDATE usuarios SET senha = ?, senha_padrao = false WHERE id = ?";

        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
    
    public boolean isSenhaPadrao(int id) throws SQLException {
        String sql = "SELECT senha_padrao FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("senha_padrao");
                } else {
                    throw new SQLException("Usuário não encontrado");
                }
            }
        }
    }
    
    public Usuarios buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
                        usuario.setValidade(rs.getDate("valididade_certificado").toLocalDate());
                    }

                    if (rs.getDate("ultima_solda") != null) {
                        usuario.setSolda(rs.getDate("ultima_solda").toLocalDate());
                    }

                    return usuario;
                }
            }
        }
        return null;
    }
    
    public void reativar(int id) throws SQLException {
        String sql = "UPDATE usuarios SET condicao = true WHERE id = ?";

        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}