package tela_equipamentos;

import dao.EquipamentosDAO;
import models.Equipamentos;
import util.Fonte;

/**
 *
 * @author Hugo
 */
public class FichaCadastro extends javax.swing.JFrame {

    private final TelaEquipamentos telaEquipamentos;

    public FichaCadastro(TelaEquipamentos tela) {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        this.telaEquipamentos = tela;

        // ENTER realiza a transação de empresitmo
        txtEquipamento.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtCodigo.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtMarca.addActionListener(e -> botaoCadastrarMouseClicked(null));
        comboStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    botaoCadastrarMouseClicked(null);
                }
            }
        });

        // Mapeia a tecla esc para fechar a janela
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "fechar");

        getRootPane().getActionMap().put("fechar", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                botaoCancelarMouseClicked(null);
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboStatus = new javax.swing.JComboBox<>();
        txtMarca = new javax.swing.JTextField();
        txtEquipamento = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        botaoCadastrar = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        comboStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "estoque", "estragado" }));
        getContentPane().add(comboStatus);
        comboStatus.setBounds(730, 645, 450, 50);

        txtMarca.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtMarca);
        txtMarca.setBounds(730, 390, 450, 50);

        txtEquipamento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtEquipamento);
        txtEquipamento.setBounds(730, 265, 450, 50);

        txtCodigo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtCodigo);
        txtCodigo.setBounds(730, 520, 450, 50);

        botaoCadastrar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        botaoCadastrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoCadastrar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoCadastrar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoCadastrar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoCadastrarMouseClicked(evt);
            }
        });
        getContentPane().add(botaoCadastrar);
        botaoCadastrar.setBounds(762, 838, 390, 100);

        botaoCancelar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botaoCancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoCancelar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoCancelar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoCancelar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoCancelarMouseClicked(evt);
            }
        });
        getContentPane().add(botaoCancelar);
        botaoCancelar.setBounds(1201, 105, 50, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaEquipamentos.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1920, 1080);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCadastrarMouseClicked
        // Recebendo os valores cadastrados.
        String codigo = txtCodigo.getText();
        String equipamento = txtEquipamento.getText();
        String marca = txtMarca.getText();
        String status = comboStatus.getSelectedItem().toString();

        // Enviando o cadastro.
        Equipamentos eq = new Equipamentos();
        eq.setCodigo(codigo);
        eq.setModelo(equipamento);
        eq.setMarca(marca);
        eq.setStatus(status);

        // Utilizando o método 'inserirEquipamento' da classe DAO.
        EquipamentosDAO dao = new EquipamentosDAO();

        boolean sucesso = dao.inserirEquipamento(eq);

        if (sucesso) {
            // Atualiza a tabela da tela principal
            if (telaEquipamentos != null) {
                telaEquipamentos.carregarTabela();
            }

            this.dispose(); // Fecha a tela
        }
    }//GEN-LAST:event_botaoCadastrarMouseClicked

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoCadastrar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtMarca;
    // End of variables declaration//GEN-END:variables
}
