package tela_relatorios;

import util.Fonte;

/**
 *
 * @author Hugos
 */

public class CadastroRelatorio extends javax.swing.JFrame {
    public CadastroRelatorio() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(770, 570));
        setMinimumSize(new java.awt.Dimension(770, 570));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(770, 570));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 570));
        jPanel1.setMinimumSize(new java.awt.Dimension(770, 570));
        jPanel1.setPreferredSize(new java.awt.Dimension(770, 570));
        jPanel1.setLayout(null);

        jLabel1.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 40f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GERAR RELATÓRIO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 50);

        jLabel13.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("CANCELAR");
        jLabel13.setToolTipText("");
        jLabel13.setMaximumSize(new java.awt.Dimension(260, 83));
        jLabel13.setMinimumSize(new java.awt.Dimension(260, 83));
        jLabel13.setPreferredSize(new java.awt.Dimension(260, 83));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel13);
        jLabel13.setBounds(40, 460, 260, 83);

        jLabel7.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("GERAR");
        jLabel7.setMaximumSize(new java.awt.Dimension(260, 83));
        jLabel7.setMinimumSize(new java.awt.Dimension(260, 83));
        jLabel7.setPreferredSize(new java.awt.Dimension(260, 83));
        jPanel1.add(jLabel7);
        jLabel7.setBounds(470, 460, 260, 83);

        jLabel2.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 460, 260, 83);

        jLabel3.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 460, 260, 83);

        jLabel6.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel6.setText("Cliente");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 100, 90, 30);

        jTextField4.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(jTextField4);
        jTextField4.setBounds(300, 100, 450, 30);

        jLabel8.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel8.setText("Serviços");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 320, 80, 30);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(300, 200, 450, 90);

        jTextField5.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(jTextField5);
        jTextField5.setBounds(300, 150, 450, 30);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(300, 320, 450, 90);

        jLabel9.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel9.setText("Empresa");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 150, 90, 30);

        jLabel10.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel10.setText("Equipamento Inspecionado");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(20, 200, 190, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 770, 570);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroRelatorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}