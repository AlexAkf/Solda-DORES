package dao;

import controllers.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Empresas;

/**
 *
 * @author Rafhael Muzzi
 */
public class EmpresasDAO {

    
    // ! =============== CREATE ===============
    public void inserirempresa(Empresas empresa) {
        String sql = "INSERT INTO empresas (nome, cnpj, email, condicao, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setString(3, empresa.getEmail());
            stmt.setBoolean(4, empresa.isCondicao());
            stmt.setString(5, empresa.getTelefone());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    
    
    // ! =============== READ ===============
        public List<Empresas> listarTodasempresas() {
        String sql = "SELECT * FROM empresas ORDER BY id ASC";
        List<Empresas> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empresas empresa = new Empresas(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getBoolean("condicao"),
                        rs.getString("telefone"));
                lista.add(empresa);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return lista;
    }
    

    // ! =============== UPDATE ===============
    public void atualizarempresa(Empresas empresa) {
        String sql = "UPDATE empresas SET nome=?, cnpj=?, email=?, condicao=?, telefone=? WHERE id=?";

        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setString(3, empresa.getEmail());
            stmt.setBoolean(4, empresa.isCondicao());
            stmt.setString(5, empresa.getTelefone());
            stmt.setInt(6, empresa.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    
    
    // ! =============== DELETE ===============
    public void deletarempresa(int id) {
        String sql = "DELETE FROM empresas WHERE id=?";
        String sqlInative = "UPDATE empresas SET condicao = false WHERE id=?";
        
        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Se bater na exceção, é porque a conta possui registro em tabelas importantes
            try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sqlInative)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();   // Como não é possível deletar, a conta é inativada
            } catch (SQLException erro) {
                // Bloco de catch vazio para não ter duplo "spam" para o usuário
            }
        }       
    }
}