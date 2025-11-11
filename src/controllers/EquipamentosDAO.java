package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Equipamentos;

// @author Hugo
// Classe DAO (Data Acess Object). Responsável por conectar os dados da tabela
// correspondente e realizar as operações CRUD.
public class EquipamentosDAO {

    private Connection conn;

    // O construtor sempre será utilizado ao criar uma nova instancia dessa classe.
    // EquipamentosDAO dao = new EquipamentosDAO();
    public EquipamentosDAO() {
        this.conn = Conexao.getConexao();
    }

    // Antes dos métodos CRUD, faço um método para facilitar a buscar do soldador.
    private int buscarIdSoldadorPorNome(String nomeSoldador) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE nome = ? AND cargo = 'soldador'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nomeSoldador);

        ResultSet rs = stmt.executeQuery();

        int idSoldador = 0;
        if (rs.next()) {
            idSoldador = rs.getInt("id");
        }

        rs.close();
        stmt.close();
        return idSoldador;
    }

    //========= CREATE, INSERIR EQUIPAMENTO =========
    // No cadastro do equipamento, o usuário poderá cadastrar com um soldador. 
    // Caso cadastre com um soldador, o sistema irá atualizar a tabela de empréstimos.
    // Colocamos o modelo "Equipamentos" e "idSoldador" como parâmetro para receber os dados, no cadastro, que serão inseridos no BD.
    public void inserirEquipamento(Equipamentos eq) {
        // Campos que serão preenchidos.
        String sqlEquip = "INSERT INTO equipamentos (codigo, modelo, marca, condicao) VALUES (?, ?, ?, ?)";
        String sqlEmprest = "INSERT INTO emprestimos (fk_equipamento, fk_soldador) VALUES (?, ?)";

        try {
            // Envio automático desativado.
            // Desativo, pois o método precisa atualizar duas tabelas ao mesmo tempo com dados relacionados.
            conn.setAutoCommit(false);

            // Buscar ID do soldador pelo nome vindo do objeto
            int idSoldador = 0;
            if (eq.getSoldador() != null && !eq.getSoldador().equals("—") && !eq.getSoldador().isBlank()) {
                idSoldador = buscarIdSoldadorPorNome(eq.getSoldador());
                if (idSoldador == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Erro: Soldador '" + eq.getSoldador() + "' não encontrado.",
                            "Erro de atualização", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    return;
                }
            }

            // Prepara o comando SQL, atribui os parâmetros e executa a instrução na tabela 'equipamentos'.
            PreparedStatement stmtEquip = conn.prepareStatement(sqlEquip, Statement.RETURN_GENERATED_KEYS);
            stmtEquip.setString(1, eq.getCodigo());
            stmtEquip.setString(2, eq.getModelo());
            stmtEquip.setString(3, eq.getMarca());
            stmtEquip.setString(4, eq.getCondicao());
            stmtEquip.execute();

            // Recupera o ID do equipamento gerado para inserir no fk_equipamento.
            ResultSet rs = stmtEquip.getGeneratedKeys();
            int idEquipamento = 0;
            if (rs.next()) {
                idEquipamento = rs.getInt(1);
            }
            rs.close();
            stmtEquip.close();

            // Prepara o comando SQL, atribui os parâmetros e executa a instrução na tabela 'emprestimos'.
            PreparedStatement stmtEmprest = conn.prepareStatement(sqlEmprest);
            stmtEmprest.setInt(1, idEquipamento);
            stmtEmprest.setInt(2, idSoldador);
            stmtEmprest.executeUpdate();
            stmtEmprest.close();

            // Confirmar a inserção dos comandos no banco.
            conn.commit();

            System.out.println("Equipamento e empréstimo cadastrados com sucesso!");

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Erro ao realizar rollback: " + ex.getMessage());
            }
            System.out.println("Erro ao inserir equipamento/emprestimo: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Erro ao resetar auto-commit: " + ex.getMessage());
            }
        }
    }

    //========= UPDATE, ATUALIZAR EQUIPAMENTO =========
    // Método para atualizar equipamento com as condições do banco.
    // Se o equipamento estiver com um soldador cadastrado e quer repassar para outro, é necessário devolver ao estoque primeiro.   
    public void atualizarEquipamento(Equipamentos eq) {
        // Campos que serão preenchidos.
        String sqlEquip = "UPDATE equipamentos SET codigo=?, modelo=?, marca=?, condicao=? WHERE id=?";
        String sqlEmprest = "UPDATE emprestimos SET fk_soldador=? WHERE fk_equipamento=? AND devolucao IS NULL";

        try {
            // Envio automático desativado.
            // Desativo, pois o método precisa atualizar duas tabelas ao mesmo tempo com dados relacionados.
            conn.setAutoCommit(false);

            // Buscar ID do soldador pelo nome vindo do objeto
            int idSoldador = 0;
            if (eq.getSoldador() != null && !eq.getSoldador().equals("—") && !eq.getSoldador().isBlank()) {
                idSoldador = buscarIdSoldadorPorNome(eq.getSoldador());
                if (idSoldador == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Erro: Soldador '" + eq.getSoldador() + "' não encontrado.",
                            "Erro de atualização", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    return;
                }
            }

            // Consulta a condição atual do equipamento
            String sqlCheckCond = "SELECT condicao FROM equipamentos WHERE id = ?";
            PreparedStatement stmtCheck = conn.prepareStatement(sqlCheckCond);
            stmtCheck.setInt(1, eq.getId());
            ResultSet rsCheck = stmtCheck.executeQuery();

            String condicaoAtual = null;
            if (rsCheck.next()) {
                condicaoAtual = rsCheck.getString("condicao");
            }
            rsCheck.close();
            stmtCheck.close();

            // 🔹 Bloqueia troca direta de soldador sem devolver antes
            if ("emprestado".equalsIgnoreCase(condicaoAtual)
                    && !"estoque".equalsIgnoreCase(eq.getCondicao())
                    && eq.getSoldador() != null
                    && !eq.getSoldador().equals("—")) {
                JOptionPane.showMessageDialog(null,
                        "Este equipamento está emprestado. Devolva (coloque em 'estoque') antes de emprestar para outro soldador.",
                        "Ação não permitida",
                        JOptionPane.WARNING_MESSAGE);
                conn.rollback();
                return;
            }

            // Atualiza os dados do equipamento existente.
            PreparedStatement stmtEquip = conn.prepareStatement(sqlEquip);
            stmtEquip.setString(1, eq.getCodigo());
            stmtEquip.setString(2, eq.getModelo());
            stmtEquip.setString(3, eq.getMarca());
            stmtEquip.setString(4, eq.getCondicao());
            stmtEquip.setInt(5, eq.getId()); // usa o ID existente.
            stmtEquip.execute();
            stmtEquip.close();

            // Se o equipamento está sendo entregue a um soldador, atualiza o empréstimo
            if ("emprestado".equalsIgnoreCase(eq.getCondicao())) {
                PreparedStatement stmtEmprest = conn.prepareStatement(sqlEmprest);
                stmtEmprest.setInt(1, idSoldador);
                stmtEmprest.setInt(2, eq.getId());
                stmtEmprest.executeUpdate();
                stmtEmprest.close();
            }

            // Confirmar a inserção dos comandos no banco.
            conn.commit();
            JOptionPane.showMessageDialog(null, "Equipamento atualizado com sucesso!");

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Erro ao realizar rollback: " + ex.getMessage());
            }
            System.out.println("Erro ao atualizar equipamento/emprestimo: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Erro ao resetar auto-commit: " + ex.getMessage());
            }
        }
    }

    public List<Equipamentos> listarTodos() {
        List<Equipamentos> lista = new ArrayList<>();

        String sql = """
        SELECT 
            e.id,
            e.codigo,
            e.modelo,
            e.marca,
            e.condicao,
            u.nome AS soldador
        FROM equipamentos e
        LEFT JOIN emprestimos em 
            ON e.id = em.fk_equipamento AND em.devolucao IS NULL
        LEFT JOIN usuarios u 
            ON em.fk_soldador = u.id
        ORDER BY e.id;
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipamentos eq = new Equipamentos();
                eq.setId(rs.getInt("id"));
                eq.setCodigo(rs.getString("codigo"));
                eq.setModelo(rs.getString("modelo"));
                eq.setMarca(rs.getString("marca"));
                eq.setCondicao(rs.getString("condicao"));
                eq.setSoldador(rs.getString("soldador") != null ? rs.getString("soldador") : "—");
                lista.add(eq);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar equipamentos: " + e.getMessage());
        }

        return lista;
    }
}
