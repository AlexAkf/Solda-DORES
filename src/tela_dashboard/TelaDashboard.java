package tela_dashboard;

import dao.UsuariosDAO;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static tela_conta.Sessao.usuarioLogado;
import util.Hash;

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
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar ou atualizar senha: " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(228, 228, 228));
        setMaximumSize(new java.awt.Dimension(1810, 1014));
        setMinimumSize(new java.awt.Dimension(1810, 1014));
        setPreferredSize(new java.awt.Dimension(1810, 1014));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dahs1.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 550, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dash2.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 550, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dash3.png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 40, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dash4.png"))); // NOI18N
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dash5.png"))); // NOI18N
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 330, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dash9.png"))); // NOI18N
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 330, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dash7.png"))); // NOI18N
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 670, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dash8.png"))); // NOI18N
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 670, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables

}