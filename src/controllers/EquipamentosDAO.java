package controllers;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Equipamentos;

/**
 *
 * @author Hugo
 */
public class EquipamentosDAO {

    // Método para inserir novo equipamento
    public boolean inserirEquipamento(Equipamentos eq) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.getConexao();

            // 1️⃣ Verifica se soldador existe e tem cargo "soldador"
            Integer idSoldador = null;
            if (eq.getSoldador() != null && !eq.getSoldador().isEmpty()) {
                String sqlVerifica = "SELECT id FROM usuarios WHERE nome = ? AND cargo = 'soldador'";
                stmt = conn.prepareStatement(sqlVerifica);
                stmt.setString(1, eq.getSoldador());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    idSoldador = rs.getInt("id");
                } else {
                    throw new SQLException("Soldador não encontrado ou não é do cargo 'soldador'.");
                }
                stmt.close();
            }

            // 2️⃣ Insere equipamento
            String sqlInsert = "INSERT INTO equipamentos (codigo, modelo, marca, condicao) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, eq.getCodigo());
            stmt.setString(2, eq.getModelo());
            stmt.setString(3, eq.getMarca());
            stmt.setString(4, eq.getCondicao());
            stmt.executeUpdate();

            // pega o id gerado
            rs = stmt.getGeneratedKeys();
            int idEquipamento = 0;
            if (rs.next()) {
                idEquipamento = rs.getInt(1);
            }
            stmt.close();

            // 3️⃣ Se houver soldador → cria registro de empréstimo
            if (idSoldador != null) {
                String sqlEmprestimo = "INSERT INTO emprestimos (fk_equipamento, fk_soldador) VALUES (?, ?)";
                stmt = conn.prepareStatement(sqlEmprestimo);
                stmt.setInt(1, idEquipamento);
                stmt.setInt(2, idSoldador);
                stmt.executeUpdate();
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar equipamento: " + ex.getMessage());
            return false;
        } finally {
            Conexao.fecharConexao(conn, stmt, rs);
        }
    }

    public List<Equipamentos> listarTodos() {
        List<Equipamentos> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql
                = "SELECT e.id, e.codigo, e.modelo, e.marca, e.condicao, u.nome AS soldador "
                + "FROM equipamentos e "
                + "LEFT JOIN emprestimos emp ON emp.fk_equipamento = e.id AND emp.devolucao IS NULL "
                + "LEFT JOIN usuarios u ON u.id = emp.fk_soldador "
                + "ORDER BY e.id ASC";

        try {
            conn = Conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String codigo = rs.getString("codigo");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                String soldador = rs.getString("soldador");
                String condicao = rs.getString("condicao");

                if (soldador == null) {
                    soldador = "";
                }

                Equipamentos eq = new Equipamentos(id, codigo, modelo, marca, soldador, condicao);
                lista.add(eq);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar equipamentos: " + ex.getMessage());
        } finally {
            Conexao.fecharConexao(conn, stmt, rs);
        }

        return lista;
    }

    public boolean excluirEquipamento(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean sucesso = false;

        String sql = "DELETE FROM equipamentos WHERE id = ?";

        try {
            conn = Conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            sucesso = linhas > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar equipamento: " + ex.getMessage());
        } finally {
            Conexao.fecharConexao(conn, stmt);
        }

        return sucesso;
    }

    // 🔹 Atualizar equipamento existente
    public void atualizar(Equipamentos equipamento) throws SQLException {
        try (Connection con = Conexao.getConexao()) {

            // Atualiza dados principais
            String sqlEquip = "UPDATE equipamentos SET codigo=?, modelo=?, marca=?, condicao=? WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sqlEquip)) {
                ps.setString(1, equipamento.getCodigo());
                ps.setString(2, equipamento.getModelo());
                ps.setString(3, equipamento.getMarca());
                ps.setString(4, equipamento.getCondicao());
                ps.setInt(5, equipamento.getId());
                ps.executeUpdate();
            }

            // === Controle de empréstimo ===
            if (equipamento.getSoldador() == null || equipamento.getSoldador().isEmpty()) {
                // devolve o equipamento, se houver empréstimo ativo
                String sqlDevolver = "UPDATE emprestimos SET devolucao = CURRENT_DATE "
                                   + "WHERE fk_equipamento = ? AND devolucao IS NULL";
                try (PreparedStatement ps = con.prepareStatement(sqlDevolver)) {
                    ps.setInt(1, equipamento.getId());
                    ps.executeUpdate();
                }

                // Garante que a condição fique como "estoque"
                String sqlCond = "UPDATE equipamentos SET condicao = 'estoque' WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlCond)) {
                    ps.setInt(1, equipamento.getId());
                    ps.executeUpdate();
                }
            } else {
                // busca o ID do soldador pelo nome
                String sqlBuscaSoldador = "SELECT id FROM usuarios WHERE nome = ?";
                Integer idSoldador = null;

                try (PreparedStatement ps = con.prepareStatement(sqlBuscaSoldador)) {
                    ps.setString(1, equipamento.getSoldador());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            idSoldador = rs.getInt("id");
                        }
                    }
                }

                if (idSoldador != null) {
                    // fecha qualquer empréstimo anterior aberto
                    String sqlFecharAnterior = "UPDATE emprestimos SET devolucao = CURRENT_DATE "
                                             + "WHERE fk_equipamento = ? AND devolucao IS NULL";
                    try (PreparedStatement ps = con.prepareStatement(sqlFecharAnterior)) {
                        ps.setInt(1, equipamento.getId());
                        ps.executeUpdate();
                    }

                    // cria novo empréstimo
                    String sqlNovoEmprestimo = "INSERT INTO emprestimos (fk_equipamento, fk_soldador, emprestimo) "
                                             + "VALUES (?, ?, CURRENT_DATE)";
                    try (PreparedStatement ps = con.prepareStatement(sqlNovoEmprestimo)) {
                        ps.setInt(1, equipamento.getId());
                        ps.setInt(2, idSoldador);
                        ps.executeUpdate();
                    }

                    // Atualiza a condição
                    String sqlCond = "UPDATE equipamentos SET condicao = 'emprestado' WHERE id = ?";
                    try (PreparedStatement ps = con.prepareStatement(sqlCond)) {
                        ps.setInt(1, equipamento.getId());
                        ps.executeUpdate();
                    }
                }
            }
        }
    }
}
