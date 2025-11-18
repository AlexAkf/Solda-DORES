package tela_principal;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import tela_equipamentos.TelaEquipamentos;

/**
 *
 * @author Hugo
 * @author Alex
 */
public class BarraPesquisar extends javax.swing.JFrame {

    private TelaPrincipal tp;

    // Referência a tela principal
    public BarraPesquisar(TelaPrincipal tp) {
        initComponents();

        this.tp = tp;

        setAlwaysOnTop(true);
        setLocation(100, 14);
        setBackground(new java.awt.Color(0, 0, 0, 0));

        // Aplica a pesquisa das tabelas.
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = campo.getText().trim();
                TelaEquipamentos.getInstancia().filtrarEquipamentos(texto);
            }
        });

        // --- FOCUS LOST NÃO FECHA MAIS ---
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Não fecha.
            }
        });

        // --- BOTÃO X (FECHA E RESTAURA) ---
        botaoFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                // Limpa campo
                campo.setText("");

                // Restaura tabela completa
                TelaEquipamentos tela = TelaEquipamentos.getInstancia();
                if (tela != null) {
                    tela.filtrarEquipamentos("");
                }

                // Fecha barra
                dispose();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campo = new javax.swing.JTextField();
        botaoFechar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 65));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        campo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        campo.setBorder(null);
        getContentPane().add(campo);
        campo.setBounds(20, 12, 520, 40);

        botaoFechar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar_pesquisar.png"))); // NOI18N
        getContentPane().add(botaoFechar);
        botaoFechar.setBounds(550, 12, 28, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/barra_pesquisar.png"))); // NOI18N
        jLabel1.setFocusable(false);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 600, 65);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoFechar;
    private javax.swing.JTextField campo;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}