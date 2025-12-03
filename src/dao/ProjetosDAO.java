package dao;

import controllers.Conexao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import models.Projetos;
import javax.swing.JOptionPane;

/**
 * DAO responsável por realizar operações CRUD na tabela "projetos". Segue
 * padrão baseado no EquipamentosDAO.
 *
 * @author Rafael Silva
 */
public class ProjetosDAO {

    private final Connection conn;

    public ProjetosDAO() {
        this.conn = Conexao.getConexao();
    }

    // ==========================================================
    //  BUSCA ID DA EMPRESA PELO NOME (autocomplete usa isto)
    // ==========================================================
    public int buscarIdSupervisorPorNome(String nome) {
        String sql = "SELECT id FROM usuarios WHERE nome = ? AND cargo = 'supervisor' LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ID do supervisor: " + e.getMessage());
        }
        return -1;
    }

    // ==========================================================
    //  BUSCA ID DO SUPERVISOR PELO NOME
    // ==========================================================
    public int buscarIdEmpresaPorNome(String nome) {
        String sql = "SELECT id FROM empresas WHERE nome = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ID da empresa: " + e.getMessage());
        }
        return -1;
    }

    // ==========================================================
    //  CREATE — INSERIR PROJETO
    // ==========================================================
    public boolean inserir(Projetos p) {
        if (p.getNome() == null || p.getNome().isBlank()
                || p.getEmpresa() == null || p.getEmpresa().isBlank()
                || p.getSupervisor() == null || p.getSupervisor().isBlank()) {

            JOptionPane.showMessageDialog(null, "Preencha: Nome, Empresa e Supervisor.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int idEmpresa = buscarIdEmpresaPorNome(p.getEmpresa());
        if (idEmpresa == -1) {
            JOptionPane.showMessageDialog(null, "Empresa não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int idSupervisor = buscarIdSupervisorPorNome(p.getSupervisor());
        if (idSupervisor == -1) {
            JOptionPane.showMessageDialog(null, "Supervisor não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = """
            INSERT INTO projetos (nome, fk_empresa, fk_supervisor, inicio, prazo, descricao, condicao)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setInt(2, idEmpresa);

            // supervisor pode ser null em tabela, mas aqui assumimos que foi encontrado
            ps.setInt(3, idSupervisor);

            if (p.getInicio() != null) {
                ps.setDate(4, Date.valueOf(p.getInicio()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            if (p.getPrazo() != null) {
                ps.setDate(5, Date.valueOf(p.getPrazo()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setString(6, p.getDescricao());
            ps.setString(7, p.getCondicao());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir projeto:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // ==========================================================
    //  READ — LISTAR PROJETOS (JOIN para pegar nomes)
    // ==========================================================
    public List<Projetos> listar() {
        List<Projetos> lista = new ArrayList<>();

        String sql = """
            SELECT
                p.id,
                p.nome,
                e.nome AS empresa,
                u.nome AS supervisor,
                p.inicio,
                p.prazo,
                p.descricao,
                p.condicao
            FROM projetos p
            LEFT JOIN empresas e ON p.fk_empresa = e.id
            LEFT JOIN usuarios u ON p.fk_supervisor = u.id
            ORDER BY p.id
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Projetos p = new Projetos();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmpresa(rs.getString("empresa"));       // nome da empresa
                p.setSupervisor(rs.getString("supervisor")); // nome do supervisor

                Date dInicio = rs.getDate("inicio");
                Date dPrazo = rs.getDate("prazo");
                p.setInicio(dInicio != null ? dInicio.toLocalDate() : null);
                p.setPrazo(dPrazo != null ? dPrazo.toLocalDate() : null);

                p.setDescricao(rs.getString("descricao"));
                p.setCondicao(rs.getString("condicao"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar projetos: " + e.getMessage());
        }

        return lista;
    }

    // ==========================================================
    //  UPDATE — ATUALIZAR PROJETO
    // ==========================================================
    public boolean atualizar(Projetos p) {
        if (p.getNome() == null || p.getNome().isBlank()
                || p.getEmpresa() == null || p.getEmpresa().isBlank()
                || p.getSupervisor() == null || p.getSupervisor().isBlank()) {

            JOptionPane.showMessageDialog(null, "Preencha: Nome, Empresa e Supervisor.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int idEmpresa = buscarIdEmpresaPorNome(p.getEmpresa());
        int idSupervisor = buscarIdSupervisorPorNome(p.getSupervisor());

        if (idEmpresa == -1 || idSupervisor == -1) {
            JOptionPane.showMessageDialog(null, "Empresa ou supervisor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = """
            UPDATE projetos SET
                nome = ?, fk_empresa = ?, fk_supervisor = ?, inicio = ?, prazo = ?, descricao = ?, condicao = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setInt(2, idEmpresa);
            ps.setInt(3, idSupervisor);

            if (p.getInicio() != null) {
                ps.setDate(4, Date.valueOf(p.getInicio()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            if (p.getPrazo() != null) {
                ps.setDate(5, Date.valueOf(p.getPrazo()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setString(6, p.getDescricao());
            ps.setString(7, p.getCondicao());
            ps.setInt(8, p.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar projeto:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // ==========================================================
    //  DELETE
    // ==========================================================
    public boolean deletar(int idProjeto) {
        String sql = "DELETE FROM projetos WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProjeto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir projeto:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // AUTOCOMPLETE: buscar empresas que comecem com termo
    public List<String> buscarEmpresas(String termo) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nome FROM empresas WHERE nome LIKE ? ORDER BY nome LIMIT 10";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, termo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empresas: " + e.getMessage());
        }
        return lista;
    }

    // AUTOCOMPLETE: buscar supervisores (usuarios com cargo='supervisor')
    public List<String> buscarSupervisores(String termo) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nome FROM usuarios WHERE cargo = 'supervisor' AND nome LIKE ? ORDER BY nome LIMIT 10";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, termo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar supervisores: " + e.getMessage());
        }
        return lista;
    }
}
