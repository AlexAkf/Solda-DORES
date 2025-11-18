package dao;

import controllers.Conexao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import models.Projetos;
import javax.swing.JOptionPane;
import java.time.LocalDate;

/**
 *
 * @author Rafael Silva
 */

/*
 * public class ProjetosDAO {
 * private Connection conn;
 * 
 * public ProjetosDAO() throws SQLException {
 * conn = Conexao.getConexao();
 * }
 * 
 * public Projetos buscarProjeto(String nomeProjeto) throws SQLException {
 * String sql = "SELECT * FROM projetos WHERE nome=?";
 * 
 * try (PreparedStatement stmt = conn.prepareStatement(sql)) {
 * stmt.setString(1,nomeProjeto);
 * try (ResultSet rs = stmt.executeQuery()) {
 * if(rs.next()){
 * Projetos projetos = new Projetos();
 * projetos.setId(rs.getInt("id"));
 * projetos.setNome(rs.getString("nome"));
 * projetos.setFk_empresa(rs.getInt("fk_empresa"));
 * projetos.setFk_supervisor(rs.getInt("fk_supervisor"));
 * projetos.setDescricao(rs.getString("descricao"));
 * projetos.setCondicao(rs.getString("condicao"));
 * 
 * Date dInicio = rs.getDate("inicio");
 * if (dInicio != null) {
 * projetos.setInicio(dInicio.toLocalDate().toString());
 * } else {
 * projetos.setInicio(null);
 * }
 * 
 * Date dPrazo = rs.getDate("prazo");
 * if (dPrazo != null) {
 * projetos.setPrazo(dPrazo.toLocalDate().toString());
 * } else {
 * projetos.setInicio(null);
 * }
 * return projetos;
 * }
 * }
 * }catch (SQLException ex) {
 * JOptionPane.showMessageDialog(null, "Erro ao buscar projetos.\nErro:" +
 * ex.getMessage());
 * throw ex;
 * }
 * return null;
 * }
 * //=================================== CREATE
 * ====================================
 * public void inserir(Projetos projetos) throws SQLException {
 * 
 * String sql ="""
 * INSERT INTO projetos
 * (nome, fk_empresa, fk_supervisor, inicio, prazo, descricao, condicao)
 * VALUES(?, ?, ?, ?, ?, ?, ?, ?)
 * """;
 * 
 * try(PreparedStatement stmt = conn.prepareStatement(sql)){
 * stmt.setString(1, projetos.getnome());
 * stmt.setInt(2, projetos.getfk_empresa());
 * stmt.setInt(3, projetos.getfk_supervisor());
 * stmt.setString(4, projetos.getdescricao());
 * stmt.setString(5, projetos.getcondicao());
 * 
 * if(projetos.getinicio() != null)
 * stmt.setDate(6, java.sql.Date.valueOf(projetos.getinicio()));
 * else
 * stmt.setNull(6, java.sql.Types.DATE);
 * 
 * if(projetos.getprazo() != null)
 * stmt.setDate(7, java.sql.Date.valueOf(projetos.getprazo()));
 * else
 * stmt.setNull(7, java.sql.Types.DATE);
 * stmt.executeUpdate();
 * 
 * }catch (SQLException ex){
 * JOptionPane.showMessageDialog(null, "Erro ao inserir o projeto.\nErro: " +
 * ex.getMessage());
 * throw ex;
 * }
 * 
 * }
 * // ==================================== READ
 * ====================================
 * public List<Projetos> listarTodos() throws SQLException {
 * List<Projetos> lista = new ArrayList<>();
 * 
 * String sql = """
 * SELECT u.*,
 * e.id AS empresa_id, e.nome AS empresa_nome,
 * s.id AS supervisor_id, s.nome AS supervisor_nome
 * FROM projetos u
 * LEFT JOIN empresas e ON u.fk_empresa = e.id
 * LEFT JOIN usuarios s ON u.id_supervisor = s.id
 * """;
 * 
 * try (Statement st = conn.createStatement();
 * ResultSet rs = st.executeQuery(sql)) {
 * 
 * while (rs.next()) {
 * Projetos u = new Projetos();
 * 
 * u.setId(rs.getInt("id"));
 * u.setNome(rs.getString("nome"));
 * u.setFk_empresa(rs.getInt("fk_empresa"));
 * u.setFk_supervisor(rs.getInt("fk_supervisor"));
 * u.setDescricao(rs.getString("descricao"));
 * u.setCondicao(rs.getString("condicao"));
 * 
 * java.sql.Date sqlInicio = rs.getDate("inicio");
 * if (sqlInicio != null) {
 * u.setInicio(LocalDate());
 * } else {
 * u.setInicio(null);
 * }
 * 
 * java.sql.Date sqlPrazo = rs.getDate("prazo");
 * if (sqlPrazo != null) {
 * u.setPrazo(LocalDate());
 * } else {
 * u.setPrazo(null);
 * }
 * lista.add(u);
 * }
 * } catch (SQLException ex) {
 * JOptionPane.showMessageDialog(null,"Erro ao listar projetos.\n" +
 * ex.getMessage());
 * throw ex;
 * }
 * return lista;
 * }
 * //=================================== ATUALIZAR
 * ====================================
 * public void atualizar(Projetos projetos) throws SQLException {
 * String sql = """
 * UPDATE projetos SET
 * nome=?, fk_empresa=?, fk_supervisor=?, descricao=?,
 * condicao=?, inicio=?, prazo=?
 * WHEREid =?
 * """;
 * 
 * try (PreparedStatement ps = conn.prepareStatement(sql)){
 * ps.setString(1, projetos.getnome());
 * ps.setInt(2, projetos.getfk_empresa());
 * ps.setInt(3, projetos.getfk_supervisor());
 * ps.setString(4, projetos.getdescricao());
 * ps.setString(5, projetos.getdescricao());
 * 
 * if(projetos.getinicio() != null)
 * ps.setDate(6, Date.valueOf(projetos.getinicio()));
 * else
 * ps.setNull(6, Types.DATE);
 * 
 * if (projetos.getprazo() != null)
 * ps.setDate(7, Date.valueOf(projetos.getprazo()));
 * else
 * ps.setNull(7, Types.DATE);
 * ps.executeUpdate();
 * }catch (SQLException ex) {
 * JOptionPane.showMessageDialog(null, "Erro ao atualizar projetos.\n" +
 * ex.getMessage());
 * throw ex;
 * }
 * }
 * //=================================== DELETAR
 * ====================================
 * public void deletar(int id) throws SQLException {
 * String sql = "DELETE FROM projetos WHERE id=?";
 * 
 * try (PreparedStatement ps = conn.prepareStatement(sql)) {
 * ps.setInt(1, id);
 * ps.executeUpdate();
 * } catch (SQLException ex) {
 * JOptionPane.showMessageDialog(null,"Erro ao deletar projetos.\n" +
 * ex.getMessage());
 * }
 * }
 * }
 */