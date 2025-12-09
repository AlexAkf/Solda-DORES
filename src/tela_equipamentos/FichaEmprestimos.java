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
public class FichaEmprestimos extends javax.swing.JFrame {

    private final TelaEquipamentos telaEquipamentos;

    public FichaEmprestimos(TelaEquipamentos telaEquipamentos) {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        this.telaEquipamentos = telaEquipamentos;
        aplicarAutoComplete(txtSoldador, termo -> new EquipamentosDAO().buscarSoldadoresPorNome(termo));
        aplicarAutoComplete(txtEquipamento, termo -> new EquipamentosDAO().buscarEquipamentosPorNome(termo));

        // ENTER realiza a transação de empresitmo
        txtEquipamento.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtSoldador.addActionListener(e -> botaoCadastrarMouseClicked(null));

        // Mapeia a tecla esc para fechar a janela
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "fechar");

        getRootPane().getActionMap().put("fechar", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                boolean popupFechado = false;

                // Fechar popup de txtSoldador se estiver visível
                if (txtSoldador.isFocusOwner() && txtSoldador.getClientProperty("popup") instanceof JPopupMenu popup1 && popup1.isVisible()) {
                    popup1.setVisible(false);
                    popupFechado = true;
                }

                // Fechar popup de txtEquipamento se estiver visível
                if (txtEquipamento.isFocusOwner() && txtEquipamento.getClientProperty("popup") instanceof JPopupMenu popup2 && popup2.isVisible()) {
                    popup2.setVisible(false);
                    popupFechado = true;
                }

                // Se nenhum popup estava aberto, fecha a janela
                if (!popupFechado) {
                    botaoCancelarMouseClicked(null);
                }
            }
        });
    }

    public void aplicarAutoComplete(JTextField campo, Function<String, List<String>> busca) {

        JPopupMenu popup = new JPopupMenu();
        popup.setFocusable(false);
        popup.setBorder(BorderFactory.createLineBorder(new Color(30, 58, 138), 2));
        popup.setBackground(Color.WHITE);

        // índice do item selecionado
        final int[] selecionado = {-1};

        campo.getDocument().addDocumentListener(new DocumentListener() {

            private void mostrarSugestoes() {
                if (!campo.isShowing()) {
                    return; // evita crash
                }

                String texto = campo.getText().trim();

                popup.setVisible(false);
                popup.removeAll();
                selecionado[0] = -1;

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
                    option.setForeground(Color.WHITE);
                    option.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    option.setBackground(new Color(30, 58, 138));

                    option.addActionListener(ev -> {
                        campo.setText(item);
                        javax.swing.SwingUtilities.invokeLater(() -> popup.setVisible(false));
                    });

                    popup.add(option);
                }

                // Mostra o popup no próximo ciclo de eventos para garantir que o componente esteja pronto
                javax.swing.SwingUtilities.invokeLater(() -> {
                    if (campo.isShowing()) {
                        popup.show(campo, 0, campo.getHeight());
                    }
                });
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

        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                int count = popup.getComponentCount();
                if (count == 0) {
                    return;
                }

                switch (evt.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_DOWN -> {
                        selecionado[0] = Math.min(selecionado[0] + 1, count - 1);
                        atualizarSelecao(popup, selecionado[0]);
                    }

                    case java.awt.event.KeyEvent.VK_UP -> {
                        selecionado[0] = Math.max(selecionado[0] - 1, 0);
                        atualizarSelecao(popup, selecionado[0]);
                    }

                    case java.awt.event.KeyEvent.VK_ENTER -> {
                        if (popup.isVisible() && selecionado[0] >= 0 && selecionado[0] < count) {
                            // Só seleciona o item do popup
                            JMenuItem item = (JMenuItem) popup.getComponent(selecionado[0]);
                            campo.setText(item.getText());
                            popup.setVisible(false);
                        } else {
                            // Se não houver popup visível, dispara o cadastro
                            botaoCadastrarMouseClicked(null);
                        }
                        evt.consume();
                    }
                }
            }
        });
        campo.putClientProperty("popup", popup);
    }

    // Atualiza a cor de fundo para indicar seleção
    private void atualizarSelecao(JPopupMenu popup, int index) {
        for (int i = 0; i < popup.getComponentCount(); i++) {
            JMenuItem item = (JMenuItem) popup.getComponent(i);
            if (i == index) {
                item.setBackground(new Color(50, 100, 200));
            } else {
                item.setBackground(new Color(30, 58, 138));
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSoldador = new javax.swing.JTextField();
        txtEquipamento = new javax.swing.JTextField();
        botaoCancelar = new javax.swing.JLabel();
        botaoCadastrar = new javax.swing.JLabel();
        ficha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        txtSoldador.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtSoldador);
        txtSoldador.setBounds(730, 450, 450, 50);

        txtEquipamento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtEquipamento);
        txtEquipamento.setBounds(730, 580, 450, 50);

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
        botaoCancelar.setBounds(1214, 305, 50, 50);

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
        botaoCadastrar.setBounds(773, 681, 390, 70);

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaEmprestimo.png"))); // NOI18N
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);

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

        // Realiza o emprestimo.
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
    private javax.swing.JLabel ficha;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtSoldador;
    // End of variables declaration//GEN-END:variables
}
