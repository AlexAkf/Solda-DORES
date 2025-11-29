package tela_equipamentos;

import dao.EmprestimosDAO;
import dao.EquipamentosDAO;
import javax.swing.JOptionPane;
import models.Equipamentos;
import util.Fonte;
import java.sql.*;

/**
 *
 * @author Hugo
 */
public class AtualizarEquipamentos extends javax.swing.JFrame {

    private final TelaEquipamentos telaEquipamentos;

    // Passando a referência da tela.
    public AtualizarEquipamentos(TelaEquipamentos tela) {
        initComponents();
        this.telaEquipamentos = tela;

    }

    public void preencherCampos(Equipamentos eq) {
        txtCodigo.setText(eq.getCodigo());
        txtEquipamento.setText(eq.getModelo());
        txtMarca.setText(eq.getMarca());
        comboStatus.setSelectedItem(eq.getStatus());
    }

    // Pega o ID do equipamento.
    private int idEquipamentoSelecionado;

    public void setIdEquipamentoSelecionado(int id) {
        this.idEquipamentoSelecionado = id;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtEquipamento = new javax.swing.JTextField();
        botaoCancelar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        botaoAtualizar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        comboStatus = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jLabel9.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel9.setText("Status");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 250, 160, 30);

        txtEquipamento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtEquipamento);
        txtEquipamento.setBounds(300, 150, 450, 30);

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

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 320, 260, 83);

        jLabel6.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel6.setText("Equipamento");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 150, 170, 30);

        jLabel5.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel5.setText("Marca");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 200, 60, 30);

        jLabel4.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel4.setText("Código de Série");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 100, 150, 30);

        botaoAtualizar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
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
        botaoAtualizar.setBounds(470, 320, 260, 83);

        jLabel1.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 40f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ATUALIZAR EQUIPAMENTO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 60);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 320, 260, 83);

        txtMarca.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtMarca);
        txtMarca.setBounds(300, 200, 450, 30);

        txtCodigo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtCodigo);
        txtCodigo.setBounds(300, 100, 450, 30);

        comboStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "estoque", "emprestado", "estragado" }));
        jPanel1.add(comboStatus);
        comboStatus.setBounds(300, 250, 450, 30);

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

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }

    private void botaoAtualizarMouseClicked(java.awt.event.MouseEvent evt) {
        // Recebendo os valores cadastrados.
        String codigo = txtCodigo.getText();
        String modelo = txtEquipamento.getText();
        String marca = txtMarca.getText();
        
        Object selectedStatus = comboStatus.getSelectedItem();
        if (selectedStatus == null) {
            JOptionPane.showMessageDialog(null, "Selecione um status para o equipamento!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String novoStatus = selectedStatus.toString();
        
        EquipamentosDAO eqDAO = new EquipamentosDAO();
        EmprestimosDAO empDAO = new EmprestimosDAO();
        String statusAtual = eqDAO.buscarStatusAtual(idEquipamentoSelecionado);

        // ======================= REGRAS DE BLOQUEIO E AÇÃO =======================
        
        // 1. BLOQUEIO: Estoque para Emprestado (Regra: Não pode emprestar pela tela de Edição)
        if ("estoque".equalsIgnoreCase(statusAtual) && "emprestado".equalsIgnoreCase(novoStatus)) {
            JOptionPane.showMessageDialog(null, 
                    "Para emprestar um equipamento, utilize o botão de empréstimos.", 
                    "Bloqueio", JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a operação
        }
        
        // 2. AÇÃO: Emprestado para Estoque (Regra: Tira da mão do soldador atual)
        if ("emprestado".equalsIgnoreCase(statusAtual) && "estoque".equalsIgnoreCase(novoStatus)) {
            try {
                empDAO.devolverEquipamento(idEquipamentoSelecionado);
                // A atualização do status ('condicao' para 'estoque') e 'situacao' será feita abaixo.
            } catch (SQLException e) {
                 JOptionPane.showMessageDialog(null, "Erro na devolução (Empréstimo): " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                 return;
            }
        }
        
        // 3. BLOQUEIO ADICIONAL: Não pode reativar de 'estragado' para 'estoque'/'emprestado'
        // Se precisar de reativação, deve ser um processo separado.
        if ("estragado".equalsIgnoreCase(statusAtual) && ! "estragado".equalsIgnoreCase(novoStatus)) {
             JOptionPane.showMessageDialog(null, 
                    "Não é possível reativar um equipamento 'estragado'.", 
                    "Bloqueio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ======================= ATUALIZAÇÃO DO EQUIPAMENTO =======================

        // Enviando o cadastro.
        Equipamentos eq = new Equipamentos();
        eq.setId(idEquipamentoSelecionado);
        eq.setCodigo(codigo);
        eq.setModelo(modelo);
        eq.setMarca(marca);
        eq.setStatus(novoStatus); // O método atualizarEquipamento na DAO cuidará da 'situacao' (true/false)

        // Utilizando o método 'atualizarEquipamento' da classe DAO.
        boolean sucesso = eqDAO.atualizarEquipamento(eq); // Este método já foi ajustado para setar a 'situacao'

        if (sucesso) {
            // Atualiza a tabela da tela principal apenas se a operação for um sucesso
            if (telaEquipamentos != null) {
                telaEquipamentos.carregarTabela();
            }

            this.dispose(); // fecha a tela
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoAtualizar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtMarca;
    // End of variables declaration//GEN-END:variables
}
