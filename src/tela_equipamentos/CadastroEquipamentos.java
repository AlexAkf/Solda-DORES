package tela_equipamentos;

import dao.EquipamentosDAO;
import models.Equipamentos;
import util.Fonte;

/**
 *
 * @author Hugo
 */
public class CadastroEquipamentos extends javax.swing.JFrame {

    private final TelaEquipamentos telaEquipamentos;

    // Passando a referência da tela.
    public CadastroEquipamentos(TelaEquipamentos tela) {
        initComponents();
        this.telaEquipamentos = tela;
        
        // ENTER realiza a transação de empresitmo
        txtEquipamento.addActionListener(e ->  botaoCadastrarMouseClicked(null));
        txtCodigo.addActionListener(e ->  botaoCadastrarMouseClicked(null));
        txtMarca.addActionListener(e ->  botaoCadastrarMouseClicked(null));
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

        jPanel1 = new javax.swing.JPanel();
        comboStatus = new javax.swing.JComboBox<>();
        txtMarca = new javax.swing.JTextField();
        txtEquipamento = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        botaoCancelar = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        botaoCadastrar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(770, 430));
        setMinimumSize(new java.awt.Dimension(770, 430));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 430));
        jPanel1.setMinimumSize(new java.awt.Dimension(770, 430));
        jPanel1.setPreferredSize(new java.awt.Dimension(770, 430));
        jPanel1.setLayout(null);

        comboStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "estoque", "estragado" }));
        jPanel1.add(comboStatus);
        comboStatus.setBounds(300, 250, 450, 30);

        txtMarca.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtMarca);
        txtMarca.setBounds(300, 200, 450, 30);

        txtEquipamento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtEquipamento.setToolTipText("");
        jPanel1.add(txtEquipamento);
        txtEquipamento.setBounds(300, 150, 450, 30);

        txtCodigo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtCodigo);
        txtCodigo.setBounds(300, 100, 450, 30);

        botaoCancelar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botaoCancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoCancelar.setText("CANCELAR");
        botaoCancelar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoCancelar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoCancelar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoCancelarMouseClicked(evt);
            }
        });
        jPanel1.add(botaoCancelar);
        botaoCancelar.setBounds(40, 320, 260, 83);

        jLabel7.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel7.setText("Status");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(20, 250, 160, 30);

        jLabel6.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel6.setText("Equipamento");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 150, 130, 30);

        jLabel5.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel5.setText("Marca");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 200, 140, 30);

        jLabel4.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel4.setText("Código de Série");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 100, 150, 30);

        botaoCadastrar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        botaoCadastrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoCadastrar.setText("CADASTRAR");
        botaoCadastrar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoCadastrar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoCadastrar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoCadastrarMouseClicked(evt);
            }
        });
        jPanel1.add(botaoCadastrar);
        botaoCadastrar.setBounds(470, 320, 260, 83);

        jLabel1.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 40f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CADASTRAR EQUIPAMENTO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 50);

        jLabel3.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 320, 260, 83);

        jLabel2.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 320, 260, 83);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCadastrarMouseClicked(java.awt.event.MouseEvent evt) {
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

        // CORREÇÃO: Captura o resultado da operação
        boolean sucesso = dao.inserirEquipamento(eq);

        // CORREÇÃO: Só fecha a tela se a operação foi um sucesso (validou e inseriu)
        if (sucesso) {
            // Atualiza a tabela da tela principal
            if (telaEquipamentos != null) {
                telaEquipamentos.carregarTabela();
            }

            this.dispose(); // Fecha a tela SOMENTE se foi sucesso
        }
    }

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoCadastrar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtMarca;
    // End of variables declaration//GEN-END:variables
}
