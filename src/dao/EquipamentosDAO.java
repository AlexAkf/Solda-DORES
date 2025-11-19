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

    // ========= CREATE, INSERIR EQUIPAMENTO =========
    // No cadastro do equipamento, o usuário poderá cadastrar com um soldador.
    // Caso cadastre com um soldador, o sistema irá atualizar a tabela de
    // empréstimos.
    // Colocamos o modelo "Equipamentos" e "idSoldador" como parâmetro para receber
    // os dados, no cadastro, que serão inseridos no BD.
    public void inserirEquipamento(Equipamentos eq) {
        // Campos que serão preenchidos.
        String sqlEquip = "INSERT INTO equipamentos (codigo, modelo, marca, condicao) VALUES (?, ?, ?, ?)";
        String sqlEmprest = "INSERT INTO emprestimos (fk_equipamento, fk_soldador) VALUES (?, ?)";

        try {
            // Envio automático desativado.
            // Desativo, pois o método precisa atualizar duas tabelas ao mesmo tempo com
            // dados relacionados.
            conn.setAutoCommit(false);

            // Se a condição for 'emprestado', é obrigatório ter um soldador.
            if ("emprestado".equalsIgnoreCase(eq.getCondicao())) {
                if (eq.getSoldador() == null || eq.getSoldador().isBlank() || eq.getSoldador().equals("—")) {
                    JOptionPane.showMessageDialog(null,
                            "Erro: Para cadastrar um equipamento como 'emprestado', é necessário informar um soldador.",
                            "Validação", JOptionPane.WARNING_MESSAGE);
                    conn.rollback();
                    return;
                }
            }

            // Buscar ID do soldador pelo nome.
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
            // e tiver um soldador válido.
            if ("emprestado".equalsIgnoreCase(eq.getCondicao())
                    && idSoldador > 0) {
                PreparedStatement stmtEmprest = conn.prepareStatement(sqlEmprest);
                stmtEmprest.setInt(1, idEquipamento);
                stmtEmprest.setInt(2, idSoldador);
                stmtEmprest.executeUpdate();
                stmtEmprest.close();
            } else {
                System.out.println(">> Nenhum empréstimo criado (condição = " + eq.getCondicao() + ")");
            }

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

    // ========= UPDATE, ATUALIZAR EQUIPAMENTO =========
    // Método para atualizar equipamento com as condições do banco.
    // Se o equipamento estiver com um soldador cadastrado e quer repassar para
    // outro, é necessário devolver ao estoque primeiro.
    public void atualizarEquipamento(Equipamentos eq) {
        // Campos que serão preenchidos.
        String sqlEquip = "UPDATE equipamentos SET codigo=?, modelo=?, marca=?, condicao=? WHERE id=?";
        String sqlUpdateEmprest = "UPDATE emprestimos SET fk_soldador=? WHERE fk_equipamento=? AND devolucao IS NULL";

        try {
            // Envio automático desativado.
            // Desativo, pois o método precisa atualizar duas tabelas ao mesmo tempo com
            // dados relacionados.
            conn.setAutoCommit(false);

            // Se for 'emprestado', precisa ter soldador
            if ("emprestado".equalsIgnoreCase(eq.getCondicao())) {
                if (eq.getSoldador() == null || eq.getSoldador().isBlank() || eq.getSoldador().equals("—")) {
                    JOptionPane.showMessageDialog(null,
                            "Erro: Para marcar como 'emprestado', é necessário informar um soldador.",
                            "Validação", JOptionPane.WARNING_MESSAGE);
                    conn.rollback();
                    return;
                }
            }

            // Buscar ID do soldador pelo nome.
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

            // Consulta a condição atual do equipamento.
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

            // Bloqueia troca direta de soldador sem devolver antes.
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
            stmtEquip.executeUpdate();
            stmtEquip.close();

            // ==================== LÓGICA DE CONDIÇÕES ====================
            // Se a nova condição for "estoque", o equipamento está sendo devolvido.
            if ("estoque".equalsIgnoreCase(eq.getCondicao())) {
                String sqlDevolucao = "UPDATE emprestimos SET devolucao = NOW() WHERE fk_equipamento = ? AND devolucao IS NULL";
                PreparedStatement stmtDev = conn.prepareStatement(sqlDevolucao);
                stmtDev.setInt(1, eq.getId());
                int linhasDev = stmtDev.executeUpdate();
                stmtDev.close();
            }

            // Se o equipamento está em estoque e agora está sendo emprestado, cria novo
            // empréstimo.
            if ("estoque".equalsIgnoreCase(condicaoAtual)
                    && "emprestado".equalsIgnoreCase(eq.getCondicao())
                    && idSoldador != 0) {

                // Finaliza empréstimo anterior. Se houver.
                String sqlFinalizarEmprestimo = "UPDATE emprestimos SET devolucao = NOW() WHERE fk_equipamento = ? AND devolucao IS NULL";
                PreparedStatement stmtFinaliza = conn.prepareStatement(sqlFinalizarEmprestimo);
                stmtFinaliza.setInt(1, eq.getId());
                int devolvidos = stmtFinaliza.executeUpdate();
                stmtFinaliza.close();

                // Cria novo empréstimo.
                String sqlInsertEmprest = "INSERT INTO emprestimos (fk_equipamento, fk_soldador) VALUES (?, ?)";
                PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertEmprest);
                stmtInsert.setInt(1, eq.getId());
                stmtInsert.setInt(2, idSoldador);
                int linhas = stmtInsert.executeUpdate();
                stmtInsert.close();
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

    // ========= READ, LER DADOS DO EQUIPAMENTO =========
    // Aqui temos um método para listar todos os dados do banco em uma tabela.
    // Só os ativos!
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
                WHERE e.situacao = 'ativo'
                ORDER BY e.id;
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipamentos eq = new Equipamentos();
                eq.setId(rs.getInt("id"));
                eq.setCodigo(rs.getString("codigo"));
                eq.setModelo(rs.getString("modelo"));
                eq.setMarca(rs.getString("marca"));
                eq.setSoldador(rs.getString("soldador") != null ? rs.getString("soldador") : "—");
                eq.setCondicao(rs.getString("condicao"));
                lista.add(eq);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar equipamentos: " + e.getMessage());
        }

        return lista;
    }

    // ========= DELETE, EXCLUIR EQUIPAMENTO =========
    // Não deleta do banco, torna o status do equipamento como 'inativo' e não será
    // exibido na tabela.
    public void excluirEquipamento(int idEquipamento) {
        String sqlCheck = "SELECT condicao FROM equipamentos WHERE id = ?";
        String sqlDevolver = "UPDATE equipamentos SET condicao = 'estoque' WHERE id = ?";
        String sqlDelete = "UPDATE equipamentos SET situacao = 'inativo' WHERE id = ?";
        // 🔹 Exclusão lógica agora (status = inativo)

        try {
            // 1️⃣ Verifica a condição atual do equipamento
            PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
            stmtCheck.setInt(1, idEquipamento);
            ResultSet rs = stmtCheck.executeQuery();

            String condicao = null;
            if (rs.next()) {
                condicao = rs.getString("condicao");
            }
            rs.close();
            stmtCheck.close();

            if (condicao == null) {
                JOptionPane.showMessageDialog(null,
                        "Equipamento não encontrado.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2️⃣ Se estiver emprestado, devolve antes de excluir
            if ("emprestado".equalsIgnoreCase(condicao)) {
                int confirmar = JOptionPane.showConfirmDialog(null,
                        "O equipamento está emprestado.\nDeseja devolvê-lo automaticamente para o estoque antes de excluir?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null,
                            "Ação cancelada. O equipamento não foi excluído.",
                            "Cancelado",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                PreparedStatement stmtDevolver = conn.prepareStatement(sqlDevolver);
                stmtDevolver.setInt(1, idEquipamento);
                stmtDevolver.executeUpdate();
                stmtDevolver.close();

                JOptionPane.showMessageDialog(null,
                        "Equipamento devolvido ao estoque com sucesso.");
            }

            // 3️⃣ Marca o equipamento como inativo (exclusão lógica)
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setInt(1, idEquipamento);
            int linhasAfetadas = stmtDelete.executeUpdate();
            stmtDelete.close();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Equipamento excluído (marcado como inativo) com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Nenhum equipamento encontrado com o ID informado.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir equipamento: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Erro ao excluir equipamento:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}