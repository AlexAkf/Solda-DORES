package dao;

import controllers.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Equipamentos;

/**
 * Classe DAO (Data Acess Object). Responsável por conectar os dados da tabela
 * correspondente e realizar as operações CRUD.
 *
 * @author Hugo
 */
public class EquipamentosDAO {

    private final Connection conn;

    // O construtor sempre será utilizado ao criar uma nova instancia dessa classe.
    // EquipamentosDAO dao = new EquipamentosDAO();
    public EquipamentosDAO() {
        this.conn = Conexao.getConexao();
    }

    // Busca o status atual (condicao) de um equipamento pelo ID
    public String buscarStatusAtual(int idEquipamento) {
        String sql = "SELECT condicao FROM equipamentos WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEquipamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("condicao");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar status: " + e.getMessage());
        }
        return null;
    }

    // ========= CREATE, INSERIR EQUIPAMENTO =========
    public boolean inserirEquipamento(Equipamentos eq) {
        String sql = "INSERT INTO equipamentos (codigo, modelo, marca, condicao, situacao) "
                + "VALUES (?, ?, ?, ?, ?)";

        // Garante que os campos estejam preenchidos.
        if (eq.getCodigo() == null || eq.getCodigo().isBlank()
                || eq.getModelo() == null || eq.getModelo().isBlank()
                || eq.getMarca() == null || eq.getMarca().isBlank()
                || eq.getStatus() == null || eq.getStatus().isBlank()) {

            JOptionPane.showMessageDialog(null,
                    "Preencha todos os campos obrigatórios!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);

            // CORREÇÃO: Retorna false se a validação falhar, sem fechar a tela.
            return false;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, eq.getCodigo());
            stmt.setString(2, eq.getModelo());
            stmt.setString(3, eq.getMarca());
            stmt.setString(4, eq.getStatus());  // estoque / estragado

            // Se estragado → já salva INATIVO
            if ("estragado".equalsIgnoreCase(eq.getStatus())) {
                stmt.setBoolean(5, false);
            } else {
                stmt.setBoolean(5, true);
            }

            int linhas = stmt.executeUpdate();
            stmt.close();

            // CORREÇÃO: Retorna true se a execução foi bem-sucedida.
            if (linhas > 0) {
                JOptionPane.showMessageDialog(null, "Equipamento cadastrado com sucesso!");
            }
            return linhas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao inserir equipamento:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            // Retorna false em caso de erro no banco.
            return false;
        }
    }

    // ========= READ, LISTAR EQUIPAMENTOS =========
    public List<Equipamentos> listarEquipamentos() {
        List<Equipamentos> lista = new ArrayList<>();

        String sql = """
        SELECT e.id, e.codigo, e.modelo AS equipamento, e.marca, e.condicao, e.situacao, u.nome AS soldador
        FROM equipamentos e LEFT JOIN emprestimos em ON e.id = em.fk_equipamento AND em.devolucao IS NULL
        LEFT JOIN usuarios u ON em.fk_soldador = u.id
        ORDER BY e.id;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipamentos eq = new Equipamentos();
                eq.setId(rs.getInt("id"));
                eq.setCodigo(rs.getString("codigo"));
                eq.setModelo(rs.getString("equipamento"));  // nome da coluna
                eq.setMarca(rs.getString("marca"));
                eq.setStatus(rs.getString("condicao"));

                // SOLDADOR (se null → "—")
                String sold = rs.getString("soldador");
                eq.setSoldador(sold != null ? sold : "—");

                // SITUAÇÃO (boolean)
                boolean situacao = rs.getBoolean("situacao");
                eq.setCondicao(situacao ? "Ativo" : "Inativo");

                lista.add(eq);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar equipamentos: " + e.getMessage());
        }

        return lista;
    }

    // ========= UPDATE, ATUALIZAR EQUIPAMENTO =========
    // Método para atualizar equipamento com as condições do banco.
    public boolean atualizarEquipamento(Equipamentos eq) {
        String sql = "UPDATE equipamentos SET codigo=?, modelo=?, marca=?, condicao=?, situacao=? WHERE id=?";

        if (eq.getCodigo() == null || eq.getCodigo().isBlank()
                || eq.getModelo() == null || eq.getModelo().isBlank()
                || eq.getMarca() == null || eq.getMarca().isBlank()
                || eq.getStatus() == null || eq.getStatus().isBlank()) {

            JOptionPane.showMessageDialog(null,
                    "Preencha todos os campos obrigatórios!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 1. Lógica para definir o valor da 'situacao' (boolean) baseado no 'status' (condicao)
        boolean situacao;
        if ("estragado".equalsIgnoreCase(eq.getStatus())) {
            situacao = false; // Se estragado -> INATIVO
        } else {
            situacao = true;  // Caso contrário (estoque/emprestado) -> ATIVO
        }

        // Você pode adicionar uma verificação de campos vazios aqui, se desejar.
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eq.getCodigo());
            stmt.setString(2, eq.getModelo());
            stmt.setString(3, eq.getMarca());
            stmt.setString(4, eq.getStatus());
            stmt.setBoolean(5, situacao); // USA boolean para a coluna 'situacao'
            stmt.setInt(6, eq.getId());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                JOptionPane.showMessageDialog(null, "Equipamento atualizado com sucesso!");
            }

            return linhas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ========= DELETE, INATIVAR EQUIPAMENTO =========
    public void inativarEquipamento(int idEquipamento) {
        String sql = "UPDATE equipamentos SET situacao = false WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEquipamento);
            int linhas = stmt.executeUpdate();
            stmt.close();

            if (linhas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Equipamento marcado como INATIVO.");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Equipamento não encontrado.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao inativar equipamento:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ======== Metódos de pesquisa autocomplete. ========
    public List<String> buscarSoldadoresPorNome(String termo) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nome FROM usuarios WHERE cargo='soldador' AND condicao = TRUE AND nome LIKE ? ORDER BY nome";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, termo + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar soldadores: " + e.getMessage());
        }

        return lista;
    }

    public List<String> buscarEquipamentosPorNome(String termo) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT modelo FROM equipamentos WHERE situacao = TRUE AND modelo LIKE ? ORDER BY modelo";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, termo + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("modelo"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar equipamentos: " + e.getMessage());
        }

        return lista;
    }
}
