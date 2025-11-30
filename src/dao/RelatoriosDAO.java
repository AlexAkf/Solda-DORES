package dao;

import controllers.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Relatorios;

/**
 *
 * @author Rafael
 */

public class RelatoriosDAO {

    private final Connection conn;

    public RelatoriosDAO() {
        this.conn = Conexao.getConexao();
    }

    /**
     * INSERIR NOVO RELATÓRIO
     */
    public boolean inserirRelatorio(Relatorios relatorio) {
        String sql = """
                INSERT INTO relatorios (fk_gestor, nome, descricao, caminho, condicao)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, relatorio.getFkGestor());
            stmt.setString(2, relatorio.getNome());
            stmt.setString(3, relatorio.getDescricao());
            stmt.setString(4, relatorio.getCaminho());
            stmt.setBoolean(5, relatorio.isCondicao());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir relatório: " + e.getMessage());
            return false;
        }
    }

    /**
     * LISTAR RELATÓRIOS PARA TABELA (TelaRelatorios)
     * Apenas as colunas que serão exibidas
     */
    public List<Relatorios> listarRelatoriosParaTela() {
        List<Relatorios> lista = new ArrayList<>();
        String sql = """
                SELECT nome, descricao, caminho, condicao, criado_em
                FROM relatorios
                ORDER BY criado_em DESC
                """;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Relatorios rel = new Relatorios();
                rel.setNome(rs.getString("nome"));
                rel.setDescricao(rs.getString("descricao"));
                rel.setCaminho(rs.getString("caminho"));
                rel.setCondicao(rs.getBoolean("condicao"));
                rel.setCriadoEm(rs.getTimestamp("criado_em"));
                lista.add(rel);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar relatórios: " + e.getMessage());
        }

        return lista;
    }

    /**
     * BUSCAR POR ID
     */
    public Relatorios buscarPorId(int id) {
        String sql = "SELECT * FROM relatorios WHERE id = ?";
        Relatorios rel = null;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rel = new Relatorios();
                rel.setId(rs.getInt("id"));
                rel.setFkGestor(rs.getInt("fk_gestor"));
                rel.setNome(rs.getString("nome"));
                rel.setDescricao(rs.getString("descricao"));
                rel.setCaminho(rs.getString("caminho"));
                rel.setCondicao(rs.getBoolean("condicao"));
                rel.setCriadoEm(rs.getTimestamp("criado_em"));
                rel.setAtualizadoEm(rs.getTimestamp("atualizado_em"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar relatório: " + e.getMessage());
        }

        return rel;
    }

    /**
     * ATUALIZAR RELATÓRIO
     */
    public boolean atualizarRelatorio(Relatorios relatorio) {
        String sql = """
                UPDATE relatorios
                SET nome = ?, descricao = ?, caminho = ?, condicao = ?, atualizado_em = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, relatorio.getNome());
            stmt.setString(2, relatorio.getDescricao());
            stmt.setString(3, relatorio.getCaminho());
            stmt.setBoolean(4, relatorio.isCondicao());
            stmt.setInt(5, relatorio.getId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar relatório: " + e.getMessage());
            return false;
        }
    }

    /**
     * DELETAR POR ID
     */
    public boolean deletarRelatorio(int id) {
        String sql = "DELETE FROM relatorios WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao deletar relatório: " + e.getMessage());
            return false;
        }
    }
}