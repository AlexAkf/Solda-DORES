package tela_conta;

import dao.UsuariosDAO;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import util.Fonte;
import util.Hash;

/**
 * Classe que cuida da conta do usuário, aqui ele consegue fazer a leitura do
 * manual de uso do sistema e a troca da senha dele
 *
 * @author Alex
 */
public class TelaConta extends javax.swing.JPanel {

    public TelaConta() {
        initComponents();
        String nome = Sessao.usuarioLogado.getNome();
        nomeUser.setText(nome);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nomeUser = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        manualUsuario = new javax.swing.JLabel();
        trocarSenha = new javax.swing.JLabel();
        btnDocumentacao = new javax.swing.JLabel();
        botaoBackup = new javax.swing.JLabel();
        botaoRestaurar = new javax.swing.JLabel();

        setBackground(new java.awt.Color(228, 228, 228));
        setMaximumSize(new java.awt.Dimension(1810, 1014));
        setMinimumSize(new java.awt.Dimension(1810, 1014));
        setPreferredSize(new java.awt.Dimension(1810, 1014));
        setLayout(null);

        nomeUser.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 46f));
        nomeUser.setForeground(new java.awt.Color(112, 112, 112));
        nomeUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(nomeUser);
        nomeUser.setBounds(1200, 546, 550, 100);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile_card.png"))); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(1190, 30, 580, 960);

        manualUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/manual.png"))); // NOI18N
        manualUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manualUsuarioMouseClicked(evt);
            }
        });
        add(manualUsuario);
        manualUsuario.setBounds(110, 380, 950, 160);

        trocarSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/senha.png"))); // NOI18N
        trocarSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trocarSenhaMouseClicked(evt);
            }
        });
        add(trocarSenha);
        trocarSenha.setBounds(110, 40, 950, 160);

        btnDocumentacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/documentacao.png"))); // NOI18N
        btnDocumentacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDocumentacaoMouseClicked(evt);
            }
        });
        add(btnDocumentacao);
        btnDocumentacao.setBounds(110, 210, 950, 160);

        botaoBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/backup.png"))); // NOI18N
        botaoBackup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoBackupMouseClicked(evt);
            }
        });
        add(botaoBackup);
        botaoBackup.setBounds(110, 720, 950, 160);

        botaoRestaurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/restaurar.png"))); // NOI18N
        botaoRestaurar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoRestaurarMouseClicked(evt);
            }
        });
        add(botaoRestaurar);
        botaoRestaurar.setBounds(110, 550, 950, 160);
    }// </editor-fold>//GEN-END:initComponents

    private void manualUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manualUsuarioMouseClicked
        try {
            var pdf = new File("docs/Manual.pdf");  // caminho do pdf fora do projeto (src)

            if (!pdf.exists()) {
                JOptionPane.showMessageDialog(this, "Não foi possível encontrar o manual.\nErro: " + pdf.getAbsolutePath());
                return;
            }

            Desktop.getDesktop().open(pdf);
        } catch (IOException erro) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir arquivo: " + erro.getMessage());
        }
    }//GEN-LAST:event_manualUsuarioMouseClicked

    private void trocarSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trocarSenhaMouseClicked
        var usuario = Sessao.usuarioLogado; // Pega o usuário logado
        String novaSenha = JOptionPane.showInputDialog(this, "Digite a nova senha:");   // Solicita nova senha

        if (novaSenha == null) {
            return; // Usuário desistiu
        }
        String confirmarSenha = JOptionPane.showInputDialog(this, "Confirme a nova senha:");
        if (confirmarSenha == null) {
            return; // Usuário desistiu
        }
        if (!novaSenha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            return;
        }

        try {
            var dao = new UsuariosDAO();
            String senhaHash = Hash.gerarHash(novaSenha); // Gera hash da senha
            dao.atualizarSenha(usuario.getId(), senhaHash); // Atualiza senha no banco
            usuario.setSenha(senhaHash);                    // Atualiza sessão
            JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!");
        } catch (HeadlessException | SQLException erro) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar senha: " + erro.getMessage());
        }
    }//GEN-LAST:event_trocarSenhaMouseClicked

    private void btnDocumentacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDocumentacaoMouseClicked
        try {
            var pdf = new File("docs/Documentação.pdf");  // caminho do pdf fora do projeto (src)

            if (!pdf.exists()) {
                JOptionPane.showMessageDialog(this, "Não foi possível encontrar o manual.\nErro: " + pdf.getAbsolutePath());
                return;
            }

            Desktop.getDesktop().open(pdf);
        } catch (IOException erro) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir arquivo: " + erro.getMessage());
        }
    }//GEN-LAST:event_btnDocumentacaoMouseClicked

    private void botaoBackupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoBackupMouseClicked
        util.BackupUtil.gerarBackup();
    }//GEN-LAST:event_botaoBackupMouseClicked

    private void botaoRestaurarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoRestaurarMouseClicked
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Tem certeza que deseja restaurar o sistema?\nIsso substitui TODOS os dados atuais!",
            "Confirmar restauração",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            util.RestaurarBackup.restaurar();
        }
    }//GEN-LAST:event_botaoRestaurarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoBackup;
    private javax.swing.JLabel botaoRestaurar;
    private javax.swing.JLabel btnDocumentacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel manualUsuario;
    private javax.swing.JLabel nomeUser;
    private javax.swing.JLabel trocarSenha;
    // End of variables declaration//GEN-END:variables
}
