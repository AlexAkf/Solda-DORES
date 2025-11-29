package tela_equipamentos;

import dao.EmprestimosDAO;
import dao.EquipamentosDAO;
import java.awt.Color;
import java.util.List;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import util.Fonte;

/**
 *
 * @author hugos
 */
public class TelaEmprestimos extends javax.swing.JFrame {

    private final TelaEquipamentos telaEquipamentos;
    // Passando a referência da tela.
    public TelaEmprestimos(TelaEquipamentos telaEquipamentos) {
        initComponents();
        this.telaEquipamentos = telaEquipamentos;
        aplicarAutoComplete(txtSoldador, termo -> new EquipamentosDAO().buscarSoldadoresPorNome(termo));
        aplicarAutoComplete(txtEquipamento, termo -> new EquipamentosDAO().buscarEquipamentosPorNome(termo));
    }

    public void aplicarAutoComplete(JTextField campo, Function<String, List<String>> busca) {

        JPopupMenu popup = new JPopupMenu();
        popup.setFocusable(false);
        popup.setBorder(BorderFactory.createLineBorder(new Color(30, 58, 138), 2));
        popup.setBackground(Color.WHITE);

        campo.getDocument().addDocumentListener(new DocumentListener() {

            private void mostrarSugestoes() {
                String texto = campo.getText().trim();

                popup.setVisible(false);
                popup.removeAll();

                if (texto.length() < 1) {
                    return;
                }

                // Executa busca no DAO.
                List<String> resultados = busca.apply(texto);

                if (resultados.isEmpty()) {
                    return;
                }

                for (String item : resultados) {
                    JMenuItem option = new JMenuItem(item);
                    option.setFocusable(false);
                    
                    // Personalização:
                    option.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 20f));
                    option.setForeground(new Color(255, 255, 255)); 
                    option.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
                    option.setBackground(new Color(30, 58, 138)); 
                    
                    option.addActionListener(ev -> {
                        campo.setText(item);
                        popup.setVisible(false);
                    });
                    popup.add(option);
                }

                popup.show(campo, 0, campo.getHeight());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                mostrarSugestoes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mostrarSugestoes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                mostrarSugestoes();
            }
        });

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEquipamento = new javax.swing.JTextField();
        txtSoldador = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JLabel();
        botaoCadastrar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(770, 330));
        setMinimumSize(new java.awt.Dimension(770, 330));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 330));
        jPanel1.setMinimumSize(new java.awt.Dimension(770, 330));
        jPanel1.setPreferredSize(new java.awt.Dimension(770, 330));
        jPanel1.setLayout(null);

        jLabel1.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 40f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REALIZAR EMPRÉSTIMO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 50);

        txtEquipamento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtEquipamento.setToolTipText("");
        jPanel1.add(txtEquipamento);
        txtEquipamento.setBounds(300, 150, 450, 30);

        txtSoldador.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtSoldador);
        txtSoldador.setBounds(300, 100, 450, 30);

        jLabel6.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel6.setText("Equipamento");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 150, 130, 30);

        jLabel4.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel4.setText("Soldador");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 100, 150, 30);

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
        botaoCancelar.setBounds(40, 220, 260, 83);

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
        botaoCadastrar.setBounds(470, 220, 260, 83);

        jLabel3.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 220, 260, 83);

        jLabel2.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 220, 260, 83);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
        dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    private void botaoCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCadastrarMouseClicked
        String nomeSoldador = txtSoldador.getText().trim();
        String nomeEquip = txtEquipamento.getText().trim();

        EmprestimosDAO dao = new EmprestimosDAO();

        // Buscar soldador
        Integer idSoldador = dao.buscarSoldadorPorNomeExato(nomeSoldador);
        if (idSoldador == null) {
            JOptionPane.showMessageDialog(this,
                    "Soldador não encontrado ou inativo!");
            return;
        }

        // Buscar equipamento
        EmprestimosDAO.EquipamentoBusca eq = dao.buscarEquipamentoPorNomeExato(nomeEquip);
        if (eq == null) {
            JOptionPane.showMessageDialog(this,
                    "Equipamento não encontrado!");
            return;
        }

        // BLOQUEIOS
        if (eq.situacao == false) {
            JOptionPane.showMessageDialog(this,
                    "Equipamento inativo. Não é possível emprestar.");
            return;
        }

        if (eq.condicao.equalsIgnoreCase("estragado")) {
            JOptionPane.showMessageDialog(this,
                    "Equipamento estragado. Não é possível emprestar.");
            return;
        }

        if (eq.condicao.equalsIgnoreCase("emprestado")) {
            JOptionPane.showMessageDialog(this,
                    "O equipamento já está emprestado!");
            return;
        }

        // Tudo OK → emprestar
        if (dao.realizarEmprestimo(eq.id, idSoldador)) {
            // Atualiza a tabela da tela principal
            if (telaEquipamentos != null) {
                telaEquipamentos.carregarTabela();
            }
            dispose();
        }
    }//GEN-LAST:event_botaoCadastrarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoCadastrar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtSoldador;
    // End of variables declaration//GEN-END:variables
}
