package tela_equipamentos;

import dao.EmprestimosDAO;
import dao.EquipamentosDAO;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import models.Equipamentos;
import util.Fonte;

/**
 *
 * @author Hugo
 */
public class FichaEdicao extends javax.swing.JFrame {

    private final TelaEquipamentos telaEquipamentos;

    // Passando a referência da tela.
    public FichaEdicao(TelaEquipamentos tela) {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        this.telaEquipamentos = tela;

        // ENTER realiza a transação de empresitmo
        txtEquipamento.addActionListener(e -> botaoAtualizarMouseClicked(null));
        txtCodigo.addActionListener(e -> botaoAtualizarMouseClicked(null));
        txtMarca.addActionListener(e -> botaoAtualizarMouseClicked(null));
        comboStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    botaoAtualizarMouseClicked(null);
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

        txtEquipamento = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        comboStatus = new javax.swing.JComboBox<>();
        botaoAtualizar = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JLabel();
        ficha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        txtEquipamento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtEquipamento);
        txtEquipamento.setBounds(730, 265, 450, 50);

        txtMarca.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtMarca);
        txtMarca.setBounds(730, 390, 450, 50);

        txtCodigo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtCodigo);
        txtCodigo.setBounds(730, 520, 450, 50);

        comboStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "estoque", "emprestado", "estragado" }));
        getContentPane().add(comboStatus);
        comboStatus.setBounds(730, 645, 450, 50);

        botaoAtualizar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoAtualizar.setForeground(new java.awt.Color(255, 255, 255));
        botaoAtualizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoAtualizar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoAtualizarMouseClicked(evt);
            }
        });
        getContentPane().add(botaoAtualizar);
        botaoAtualizar.setBounds(762, 838, 390, 100);

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

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaEquipamentos.png"))); // NOI18N
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoAtualizarMouseClicked
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
        if ("estragado".equalsIgnoreCase(statusAtual) && !"estragado".equalsIgnoreCase(novoStatus)) {
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
    }//GEN-LAST:event_botaoAtualizarMouseClicked

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoAtualizar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JLabel ficha;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtMarca;
    // End of variables declaration//GEN-END:variables
}
