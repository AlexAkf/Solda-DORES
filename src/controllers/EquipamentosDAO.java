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
        
        // 1. A Conexão é a primeira a ser aberta e fechada automaticamente.
        try (Connection conn = Conexao.getConexao()) { 

            // 1️⃣ Verifica se soldador existe e tem cargo "soldador"
            Integer idSoldador = null;

            if (eq.getSoldador() != null && !eq.getSoldador().trim().isEmpty()) {
                String sqlVerifica = "SELECT id FROM usuarios WHERE LOWER(nome) = LOWER(?) AND cargo = 'soldador'";
                
                // Os blocos internos de try-with-resources garantem que os PreparedStatement 
                // e ResultSet sejam fechados imediatamente após o uso, mesmo que a conexão continue aberta.
                try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerifica)) {
                    stmtVerifica.setString(1, eq.getSoldador().trim());
                    
                    try (ResultSet rsVerifica = stmtVerifica.executeQuery()) {
                        if (rsVerifica.next()) {
                            idSoldador = rsVerifica.getInt("id");
                        } else {
                            JOptionPane.showMessageDialog(null, "Soldador não encontrado ou não é do cargo 'soldador'.");
                            return false; 
                        }
                    } // rsVerifica é fechado automaticamente
                } // stmtVerifica é fechado automaticamente
            }

            // 2️⃣ Insere equipamento
            int idEquipamento = 0;
            String sqlInsert = "INSERT INTO equipamentos (codigo, modelo, marca, condicao) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, eq.getCodigo());
                stmtInsert.setString(2, eq.getModelo());
                stmtInsert.setString(3, eq.getMarca());
                stmtInsert.setString(4, eq.getCondicao());
                stmtInsert.executeUpdate();

                // pega o id gerado
                try (ResultSet rsGenerated = stmtInsert.getGeneratedKeys()) {
                    if (rsGenerated.next()) {
                        idEquipamento = rsGenerated.getInt(1);
                    }
                } // rsGenerated é fechado automaticamente
            } // stmtInsert é fechado automaticamente

            // 3️⃣ Se houver soldador → cria registro de empréstimo
            if (idSoldador != null) {
                String sqlEmprestimo = "INSERT INTO emprestimos (fk_equipamento, fk_soldador) VALUES (?, ?)";
                try (PreparedStatement stmtEmprestimo = conn.prepareStatement(sqlEmprestimo)) {
                    stmtEmprestimo.setInt(1, idEquipamento);
                    stmtEmprestimo.setInt(2, idSoldador);
                    stmtEmprestimo.executeUpdate();
                } // stmtEmprestimo é fechado automaticamente
            }

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace(); 
            JOptionPane.showMessageDialog(null,
                    "Erro ao cadastrar equipamento:\n" + ex.getMessage());
            return false;
        } 
        // O bloco 'finally' não é mais necessário para fechar recursos
    }

    public List<Equipamentos> listarTodos() {
        List<Equipamentos> lista = new ArrayList<>();

        String sql
                = "SELECT e.id, e.codigo, e.modelo, e.marca, e.condicao, u.nome AS soldador "
                + "FROM equipamentos e "
                + "LEFT JOIN emprestimos emp ON emp.fk_equipamento = e.id AND emp.devolucao IS NULL "
                + "LEFT JOIN usuarios u ON u.id = emp.fk_soldador "
                + "ORDER BY e.id ASC";

        // Todos os recursos (Conexão, Statement e ResultSet) são abertos na TWR
        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
        } 

        return lista;
    }

    public boolean excluirEquipamento(int id) {
        boolean sucesso = false;
        String sql = "DELETE FROM equipamentos WHERE id = ?";

        // Conexão e Statement abertos e fechados automaticamente
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            sucesso = linhas > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar equipamento: " + ex.getMessage());
        } 

        return sucesso;
    }

    // 🔹 Atualizar equipamento existente (Já estava no modelo TWR e foi mantido)
    public void atualizar(Equipamentos equipamento) throws SQLException {
        try (Connection con = Conexao.getConexao()) {

            // 1️⃣ Atualiza os dados principais
            String sqlEquip = "UPDATE equipamentos SET codigo=?, modelo=?, marca=?, condicao=? WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sqlEquip)) {
                ps.setString(1, equipamento.getCodigo());
                ps.setString(2, equipamento.getModelo());
                ps.setString(3, equipamento.getMarca());
                ps.setString(4, equipamento.getCondicao());
                ps.setInt(5, equipamento.getId());
                ps.executeUpdate();
            }

            // === CONTROLE DE EMPRÉSTIMO ===
            if (equipamento.getSoldador() == null || equipamento.getSoldador().isEmpty()) {
                // 🔸 Nenhum soldador: devolver equipamento (se estiver emprestado)
                String sqlDevolver = """
                UPDATE emprestimos 
                SET devolucao = CURRENT_DATE 
                WHERE fk_equipamento = ? AND devolucao IS NULL
                """;
                try (PreparedStatement ps = con.prepareStatement(sqlDevolver)) {
                    ps.setInt(1, equipamento.getId());
                    ps.executeUpdate();
                }

            } else {
                // 🔹 Tem soldador → buscar ID
                String sqlBuscaSoldador = "SELECT id FROM usuarios WHERE nome = ? AND cargo = 'soldador'";
                Integer idSoldador = null;

                try (PreparedStatement ps = con.prepareStatement(sqlBuscaSoldador)) {
                    ps.setString(1, equipamento.getSoldador());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            idSoldador = rs.getInt("id");
                        } else {
                            throw new SQLException("Soldador não encontrado ou inválido.");
                        }
                    }
                }

                // 🔸 Verifica se já há empréstimo ativo
                String sqlCheck = "SELECT devolucao FROM emprestimos WHERE fk_equipamento = ? AND devolucao IS NULL";
                boolean temEmprestimoAtivo = false;

                try (PreparedStatement ps = con.prepareStatement(sqlCheck)) {
                    ps.setInt(1, equipamento.getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            temEmprestimoAtivo = true;
                        }
                    }
                }

                if (temEmprestimoAtivo) {
                    // Se já estiver emprestado, devolve antes
                    String sqlDevolver = """
                    UPDATE emprestimos 
                    SET devolucao = CURRENT_DATE 
                    WHERE fk_equipamento = ? AND devolucao IS NULL
                    """;
                    try (PreparedStatement ps = con.prepareStatement(sqlDevolver)) {
                        ps.setInt(1, equipamento.getId());
                        ps.executeUpdate();
                    }
                }

                // 🔸 Agora cria novo empréstimo (trigger ajusta condição)
                String sqlEmprestimo = """
                INSERT INTO emprestimos (fk_equipamento, fk_soldador, emprestimo)
                VALUES (?, ?, CURRENT_DATE)
                """;
                try (PreparedStatement ps = con.prepareStatement(sqlEmprestimo)) {
                    ps.setInt(1, equipamento.getId());
                    ps.setInt(2, idSoldador);
                    ps.executeUpdate();
                }
            }
        }
    }
}