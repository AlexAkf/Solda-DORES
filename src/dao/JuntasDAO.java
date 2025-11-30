package dao;

import controllers.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Juntas;

/**
 *
 * @author hugos
 */
public class JuntasDAO {

    private final Connection conn;

    // O construtor sempre será utilizado ao criar uma nova instancia dessa classe.
    public JuntasDAO() {
        this.conn = Conexao.getConexao();
    }

    // Método para busca ID do projeto pelo nome.
    private int buscarIdProjetoPorNome(String nomeProjeto) {
        String sql = "SELECT id FROM projetos WHERE nome = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeProjeto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar ID do projeto: " + e.getMessage());
        }

        return -1; // não encontrado
    }

    // ========= CREATE, INSERIR JUNTA =========
    public boolean inserirJunta(Juntas j) {
        String sql = "INSERT INTO juntas (fk_projeto, condicao, comprimento, codigo) "
                + "VALUES (?, ?, ?, ?)";

        // Validação padrão do seu estilo
        if (j.getProjeto() == null || j.getProjeto().isBlank()
                || j.getStatus() == null || j.getStatus().isBlank()
                || j.getCodigo() == null || j.getCodigo().isBlank()
                || j.getComprimento() <= 0) {

            JOptionPane.showMessageDialog(null,
                    "Preencha todos os campos obrigatórios!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Converte o nome do projeto em ID
        int idProjeto = buscarIdProjetoPorNome(j.getProjeto());
        if (idProjeto == -1) {
            JOptionPane.showMessageDialog(null,
                    "Projeto não encontrado no sistema!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProjeto);
            stmt.setString(2, j.getStatus());
            stmt.setDouble(3, j.getComprimento());
            stmt.setString(4, j.getCodigo());

            int linhas = stmt.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao inserir junta:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ========= READ, LISTAR JUNTAS =========
    public List<Juntas> listarJuntas() {
        List<Juntas> lista = new ArrayList<>();

        // SQL com JOIN para buscar o nome do projeto (p.nome)
        String sql = """
        SELECT j.id, p.nome AS projeto, j.condicao, j.comprimento, j.codigo
        FROM juntas j JOIN projetos p ON j.fk_projeto = p.id ORDER BY j.id;
        """;

        // Usando try-with-resources para garantir o fechamento
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Juntas j = new Juntas();
                // Mapeamento das colunas do ResultSet para o objeto Juntas
                j.setId(rs.getInt("id"));
                j.setProjeto(rs.getString("projeto"));
                j.setStatus(rs.getString("condicao"));
                j.setComprimento(rs.getDouble("comprimento"));
                j.setCodigo(rs.getString("codigo"));

                lista.add(j);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar juntas: " + e.getMessage());
        }
        return lista;
    }

    // ========= UPDATE, ATUALIZAR JUNTA =========
    public boolean atualizarJunta(Juntas j) {
        String sql = "UPDATE juntas SET fk_projeto=?, condicao=?, comprimento=?, codigo=? WHERE id=?";

        if (j.getProjeto() == null || j.getProjeto().isBlank()
                || j.getStatus() == null || j.getStatus().isBlank()
                || j.getCodigo() == null || j.getCodigo().isBlank()
                || j.getComprimento() <= 0) {

            JOptionPane.showMessageDialog(null,
                    "Preencha todos os campos obrigatórios!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int idProjeto = buscarIdProjetoPorNome(j.getProjeto());
        if (idProjeto == -1) {
            JOptionPane.showMessageDialog(null,
                    "Projeto não encontrado!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProjeto);
            stmt.setString(2, j.getStatus());
            stmt.setDouble(3, j.getComprimento());
            stmt.setString(4, j.getCodigo());
            stmt.setInt(5, j.getId());

            int linhas = stmt.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar junta:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean deletarJunta(int idJunta) {
        String sql = "DELETE FROM juntas WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idJunta);

            int linhas = stmt.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao excluir junta:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Metódo de pesquisa para o autocomplete.
    public List<String> buscarProjetosPorNome(String termo) {
        List<String> lista = new ArrayList<>();

        String sql = "SELECT nome FROM projetos WHERE nome LIKE ? LIMIT 10";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, termo + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar projetos: " + e.getMessage());
        }

        return lista;
    }
}
