package tela_equipamentos;

import controllers.EquipamentosDAO;
import models.Equipamentos;

/**
 *
 * @author Hugo
 */
public class AtualizarEquipamentos extends javax.swing.JFrame {

    private TelaEquipamentos telaEquipamentos;
    // Passando a referência da tela.
    public AtualizarEquipamentos(TelaEquipamentos tela) {
        initComponents();
        this.telaEquipamentos = tela;
    }

    public void preencherCampos(Equipamentos eq) {
        txtCodigo.setText(eq.getCodigo());
        txtModelo.setText(eq.getModelo());
        txtMarca.setText(eq.getMarca());
        txtSoldador.setText(eq.getSoldador().equals("—") ? "" : eq.getSoldador());
        combo.setSelectedItem(eq.getCondicao().substring(0, 1).toUpperCase() + eq.getCondicao().substring(1));
    }
    
    private int idEquipamentoSelecionado;

    public void setIdEquipamentoSelecionado(int id) {
        this.idEquipamentoSelecionado = id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        combo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        botaoCancelar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        botaoAtualizar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtSoldador = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(770, 480));
        setMinimumSize(new java.awt.Dimension(770, 480));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 480));
        jPanel1.setMinimumSize(new java.awt.Dimension(770, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(770, 480));
        jPanel1.setLayout(null);

        combo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estoque", "Emprestado", "Estragado" }));
        jPanel1.add(combo);
        combo.setBounds(300, 300, 450, 30);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("Estado de Uso");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 300, 160, 32);

        txtModelo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(txtModelo);
        txtModelo.setBounds(300, 150, 450, 30);

        botaoCancelar.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
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
        botaoCancelar.setBounds(40, 370, 260, 83);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 370, 260, 83);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setText("Soldador");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(20, 250, 140, 32);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Modelo");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 150, 90, 32);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("Marca");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 200, 65, 32);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setText("Código");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 100, 150, 32);

        botaoAtualizar.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        botaoAtualizar.setForeground(new java.awt.Color(255, 255, 255));
        botaoAtualizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoAtualizar.setText("CADASTRAR");
        botaoAtualizar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoAtualizarMouseClicked(evt);
            }
        });
        jPanel1.add(botaoAtualizar);
        botaoAtualizar.setBounds(470, 370, 260, 83);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ATUALIZAR EQUIPAMENTO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 48);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 370, 260, 83);

        txtMarca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(txtMarca);
        txtMarca.setBounds(300, 200, 450, 30);

        txtSoldador.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoldador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoldadorKeyReleased(evt);
            }
        });
        jPanel1.add(txtSoldador);
        txtSoldador.setBounds(300, 250, 450, 30);

        txtCodigo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(txtCodigo);
        txtCodigo.setBounds(300, 100, 450, 30);

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

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    private void botaoAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoAtualizarMouseClicked
        // Recebendo os valores cadastrados.
        String codigo = txtCodigo.getText();
        String modelo = txtModelo.getText();
        String marca = txtMarca.getText();
        String soldador = txtSoldador.getText();
        String condicao = combo.getSelectedItem().toString().toLowerCase();

        // Enviando o cadastro.
        Equipamentos eq = new Equipamentos();
        eq.setId(idEquipamentoSelecionado);
        eq.setCodigo(codigo);
        eq.setModelo(modelo);
        eq.setMarca(marca);
        eq.setSoldador(soldador);
        eq.setCondicao(condicao);

        // Utilizando o método 'atualizarEquipamento' da classe DAO.
        EquipamentosDAO dao = new EquipamentosDAO();
        dao.atualizarEquipamento(eq);

        // Atualiza a tabela da tela principal
        if (telaEquipamentos != null) {
            telaEquipamentos.carregarTabela();
        }

        this.dispose(); // fecha a tela
    }//GEN-LAST:event_botaoAtualizarMouseClicked

    private void txtSoldadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoldadorKeyReleased
        String soldador = txtSoldador.getText().trim();

        if (!soldador.isEmpty()) {
            combo.setSelectedItem("Emprestado");
            combo.setEnabled(false);
        } else {
            combo.setSelectedItem("Estoque");
            combo.setEnabled(true);
        }
    }//GEN-LAST:event_txtSoldadorKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoAtualizar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtSoldador;
    // End of variables declaration//GEN-END:variables
}