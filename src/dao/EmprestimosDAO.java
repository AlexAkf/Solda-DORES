package dao;

import controllers.Conexao;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author hugos
 */
public class EmprestimosDAO {

    private Connection conn;

    public EmprestimosDAO() {
        conn = Conexao.getConexao();
    }
    
    // ============================
    //  BUSCAR SOLDADOR POR NOME EXATO
    // ============================
    public Integer buscarSoldadorPorNomeExato(String nomeSoldador) {
        String sql = "SELECT id FROM usuarios WHERE nome = ? AND cargo = 'soldador' AND condicao = TRUE";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeSoldador);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao buscar soldador:\n" + e.getMessage());
        }
        return null; // não encontrado
    }

    // ============================
    //  BUSCAR EQUIPAMENTO POR NOME EXATO
    // ============================
    public EquipamentoBusca buscarEquipamentoPorNomeExato(String nomeEquip) {

        String sql = """
            SELECT id, condicao, situacao
            FROM equipamentos
            WHERE modelo = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEquip);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                EquipamentoBusca eq = new EquipamentoBusca();
                eq.id = rs.getInt("id");
                eq.condicao = rs.getString("condicao");
                eq.situacao = rs.getBoolean("situacao");

                return eq;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao buscar equipamento:\n" + e.getMessage());
        }

        return null;
    }

    // Classe auxiliar para retornar múltiplas infos
    public static class EquipamentoBusca {
        public int id;
        public String condicao;  // estoque / emprestado / estragado
        public boolean situacao; // ativo / inativo
    }

    //  Realiza empréstimos.
    public boolean realizarEmprestimo(int idEquipamento, int idSoldador) {

        String sqlInsert = """
            INSERT INTO emprestimos (fk_equipamento, fk_soldador)
            VALUES (?, ?)
        """;

        // atualiza condicao do equipamento → emprestado
        String sqlAtualizaEq = """
            UPDATE equipamentos SET condicao = 'emprestado'
            WHERE id = ?
        """;

        try {
            conn.setAutoCommit(false);

            // Inserir empréstimo
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setInt(1, idEquipamento);
            stmt.setInt(2, idSoldador);
            stmt.executeUpdate();
            stmt.close();

            // Atualizar equipamento
            PreparedStatement stmt2 = conn.prepareStatement(sqlAtualizaEq);
            stmt2.setInt(1, idEquipamento);
            stmt2.executeUpdate();
            stmt2.close();

            conn.commit();
            return true;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (Exception ex) {}
            JOptionPane.showMessageDialog(null,
                "Erro ao realizar empréstimo:\n" + e.getMessage());
            return false;

        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) {}
        }
    }
    
    // Encerra o empréstimo.
    public void devolverEquipamento(int idEquipamento) throws SQLException {
        // SQL para atualizar a data de devolucao (fim do empréstimo ativo)
        String sqlEmprestimo = "UPDATE emprestimos SET devolucao = CURRENT_TIMESTAMP WHERE fk_equipamento = ? AND devolucao IS NULL";
        
        try (PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo)) {
            stmt.setInt(1, idEquipamento);
            
            int linhas = stmt.executeUpdate();
            
            if (linhas == 0) {
                 // Pode ser útil para depuração, caso tente devolver algo que não está emprestado
                 System.out.println("Aviso: Tentativa de devolver equipamento ID " + idEquipamento + " que não possui empréstimo ativo.");
            }
            
        } catch (SQLException e) {
            // Relança a exceção para ser tratada na tela (AtualizarEquipamentos)
            throw new SQLException("Erro ao registrar devolução do equipamento: " + e.getMessage());
        }
    }
}
