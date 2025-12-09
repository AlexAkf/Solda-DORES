package tela_dashboard;

import controllers.Conexao;
import dao.UsuariosDAO;
import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;
import static tela_conta.Sessao.usuarioLogado;
import util.Hash;
import util.Fonte;

/**
 *
 * @author Hugo
 * @author Alex
 */

public class TelaDashboard extends javax.swing.JPanel {
    public TelaDashboard() throws SQLException {
        initComponents();
        int usuarioId = usuarioLogado.getId(); // id do usuário logado
        var dao = new UsuariosDAO();
        try {
            if (dao.isSenhaPadrao(usuarioId)) {
                String novaSenha = JOptionPane.showInputDialog(null, "Você precisa atualizar sua senha:");

                if (novaSenha != null && !novaSenha.isEmpty()) {
                    String senhaHash = Hash.gerarHash(novaSenha); 
                    dao.atualizarSenha(usuarioId, senhaHash);
                    JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Você precisa definir uma nova senha para continuar.");
                    System.exit(0); // fecha o sistema se não atualizar
                }
            }
            
            carregarDashboard();
            
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar ou atualizar senha: " + ex.getMessage());
        }
    }

    // ============================================================
    //   MÉTODO PARA EXECUTAR TODOS OS SQL E SETAR AS LABELS
    // ============================================================
    private void carregarDashboard() throws SQLException {

        try (Connection conn = Conexao.getConexao()) {

            // --- PROJETOS ATIVOS ---
            ativos.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM projetos WHERE condicao = 'ativo'"));

            // --- PROJETOS EM AVALIAÇÃO ---
            avaliacao.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM projetos WHERE condicao = 'avaliacao'"));

            // --- PROJETOS FINALIZADOS ---
            finalizados.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM projetos WHERE condicao = 'finalizado'"));

            // --- PROJETOS CANCELADOS ---
            cancelados.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM projetos WHERE condicao = 'cancelado'"));

            // --- TOTAL DE PROJETOS ---
            projetos.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM projetos"));

            // --- TOTAL DE USUÁRIOS ---
            usuarios.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM usuarios"));

            // --- TOTAL DE EMPRESAS ---
            empresas.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM empresas"));

            // --- TOTAL DE EQUIPAMENTOS ---
            equipamentos.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM equipamentos"));

            // --- TOTAL DE JUNTAS ---
            juntas.setText(consultarNumero(conn,
                    "SELECT COUNT(*) FROM juntas"));
        }
    }


    // ============================================================
    //        FUNÇÃO UTIL PARA CONSULTAR UM NÚMERO  
    // ============================================================
    private String consultarNumero(Connection conn, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return String.valueOf(rs.getInt(1));
            }
        }
        return "0";
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ativos = new javax.swing.JLabel();
        avaliacao = new javax.swing.JLabel();
        finalizados = new javax.swing.JLabel();
        empresas = new javax.swing.JLabel();
        projetos = new javax.swing.JLabel();
        cancelados = new javax.swing.JLabel();
        equipamentos = new javax.swing.JLabel();
        usuarios = new javax.swing.JLabel();
        juntas = new javax.swing.JLabel();
        tela = new javax.swing.JLabel();

        setBackground(new java.awt.Color(228, 228, 228));
        setMaximumSize(new java.awt.Dimension(1810, 1014));
        setMinimumSize(new java.awt.Dimension(1810, 1014));
        setPreferredSize(new java.awt.Dimension(1810, 1014));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ativos.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        ativos.setForeground(new java.awt.Color(255, 255, 255));
        ativos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ativos.setText("11111");
        add(ativos, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 90, 530, 210));

        avaliacao.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        avaliacao.setForeground(new java.awt.Color(255, 255, 255));
        avaliacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avaliacao.setText("11111");
        add(avaliacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 530, 210));

        finalizados.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        finalizados.setForeground(new java.awt.Color(255, 255, 255));
        finalizados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        finalizados.setText("11111");
        add(finalizados, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 530, 210));

        empresas.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        empresas.setForeground(new java.awt.Color(102, 102, 102));
        empresas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        empresas.setText("11111");
        add(empresas, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 710, 530, 250));

        projetos.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        projetos.setForeground(new java.awt.Color(102, 102, 102));
        projetos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        projetos.setText("11111");
        add(projetos, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 380, 530, 250));

        cancelados.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        cancelados.setForeground(new java.awt.Color(102, 102, 102));
        cancelados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cancelados.setText("11111");
        add(cancelados, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 720, 530, 250));

        equipamentos.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        equipamentos.setForeground(new java.awt.Color(102, 102, 102));
        equipamentos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        equipamentos.setText("11111");
        add(equipamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 710, 530, 250));

        usuarios.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        usuarios.setForeground(new java.awt.Color(102, 102, 102));
        usuarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usuarios.setText("11111");
        add(usuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 530, 250));

        juntas.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 56f));
        juntas.setForeground(new java.awt.Color(102, 102, 102));
        juntas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        juntas.setText("11111");
        add(juntas, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 380, 530, 250));

        tela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/telaDashboard.png"))); // NOI18N
        add(tela, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1810, 1014));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ativos;
    private javax.swing.JLabel avaliacao;
    private javax.swing.JLabel cancelados;
    private javax.swing.JLabel empresas;
    private javax.swing.JLabel equipamentos;
    private javax.swing.JLabel finalizados;
    private javax.swing.JLabel juntas;
    private javax.swing.JLabel projetos;
    private javax.swing.JLabel tela;
    private javax.swing.JLabel usuarios;
    // End of variables declaration//GEN-END:variables

}