package tela_principal;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tela_empresas.TelaEmpresas;
import tela_equipamentos.TelaEquipamentos;
import tela_funcionarios.TelaFuncionarios;
import tela_juntas.TelaJuntas;
import tela_projetos.TelaProjetos;
import tela_relatorios.TelaRelatorios;

/**
 *
 * @author Hugo
 * @author Alex
 */
public class BarraPesquisar extends javax.swing.JFrame {

    private TelaPrincipal tp;

    // Referencia a tela principal
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

                TelaEquipamentos.getInstancia().filtrar(texto);

                try {
                    TelaFuncionarios.getInstancia().filtrar(texto);
                } catch (SQLException ex) {
                    Logger.getLogger(BarraPesquisar.class.getName()).log(Level.SEVERE, null, ex);
                }

                TelaEmpresas.getInstancia().filtrar(texto);

                TelaRelatorios.getInstancia().filtrar(texto);

                try {
                    TelaProjetos.getInstancia().filtrar(texto);
                } catch (SQLException ex) {
                    Logger.getLogger(BarraPesquisar.class.getName()).log(Level.SEVERE, null, ex);
                }

                TelaJuntas.getInstancia().filtrar(texto);
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
                TelaEquipamentos tela1 = TelaEquipamentos.getInstancia();
                if (tela1 != null) {
                    tela1.filtrar("");
                }

                TelaFuncionarios tela2;
                try {
                    tela2 = TelaFuncionarios.getInstancia();
                    if (tela2 != null) {
                        tela2.filtrar("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BarraPesquisar.class.getName()).log(Level.SEVERE, null, ex);
                }

                TelaEmpresas tela3 = TelaEmpresas.getInstancia();
                if (tela3 != null) {
                    tela3.filtrar("");
                }

                TelaRelatorios tela4 = TelaRelatorios.getInstancia();
                if (tela4 != null) {
                    tela4.filtrar("");
                }

                TelaProjetos tela5;
                try {
                    tela5 = TelaProjetos.getInstancia();
                    if (tela5 != null) {
                        tela5.filtrar("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BarraPesquisar.class.getName()).log(Level.SEVERE, null, ex);
                }

                TelaJuntas tela6 = TelaJuntas.getInstancia();
                if (tela6 != null) {
                    tela6.filtrar("");
                }
                
                // Fecha barra
                dispose();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
