package tela_juntas;

import dao.JuntasDAO;
import java.awt.Color;
import java.util.List;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Juntas;
import util.Fonte;

/**
 *
 * @author hugos
 */
public class AtualizarJuntas extends javax.swing.JFrame {

    private TelaJuntas telaJuntas;

    public AtualizarJuntas(TelaJuntas telaJuntas) {
        initComponents();
        this.telaJuntas = telaJuntas;

        // ENTER realiza a transação de empresitmo
        txtProjeto.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtCodigo.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtComprimento.addActionListener(e -> botaoCadastrarMouseClicked(null));
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

        aplicarAutoComplete(txtProjeto, termo -> new JuntasDAO().buscarProjetosPorNome(termo));
    }

    public void aplicarAutoComplete(JTextField campo, Function<String, List<String>> busca) {

        JPopupMenu popup = new JPopupMenu();
        popup.setFocusable(false);
        popup.setBorder(BorderFactory.createLineBorder(new Color(30, 58, 138), 2));
        popup.setBackground(Color.WHITE);

        final int[] selecionado = {-1};

        campo.getDocument().addDocumentListener(new DocumentListener() {

            private void mostrarSugestoes() {

                if (!campo.isShowing()) {
                    return;
                }

                String texto = campo.getText().trim();

                popup.setVisible(false);
                popup.removeAll();
                selecionado[0] = -1;

                if (texto.length() < 1) {
                    return;
                }

                List<String> resultados = busca.apply(texto);

                if (resultados.isEmpty()) {
                    return;
                }

                for (String item : resultados) {
                    JMenuItem option = new JMenuItem(item);

                    option.setFocusable(false);
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
                        if (popup.isVisible() && selecionado[0] >= 0) {
                            JMenuItem item = (JMenuItem) popup.getComponent(selecionado[0]);
                            campo.setText(item.getText());
                            popup.setVisible(false);
                        }
                        evt.consume();
                    }
                }
            }
        });

        campo.putClientProperty("popup", popup);
    }

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

    // Puxa os dados atuais e preenche os campos.
    public void preencherCampos(Juntas j) {
        txtProjeto.setText(j.getProjeto());
        txtCodigo.setText(j.getCodigo());
        txtComprimento.setText(String.valueOf(j.getComprimento()));

        String status = j.getStatus();
        String exibicao = switch (status.toLowerCase()) {
            case "nao_realizado" -> "Não realizado";
            case "em_andamento" -> "Em andamento";
            case "concluido" -> "Concluído";
            case "refazer" -> "A refazer";
            default -> status;
        };

        comboStatus.setSelectedItem(exibicao);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboStatus = new javax.swing.JComboBox<>();
        txtCodigo = new javax.swing.JTextField();
        txtProjeto = new javax.swing.JTextField();
        botaoCancelar = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        botaoCadastrar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtComprimento = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(770, 430));
        setMinimumSize(new java.awt.Dimension(770, 430));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 430));
        jPanel1.setMinimumSize(new java.awt.Dimension(770, 430));
        jPanel1.setLayout(null);

        comboStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Não realizado", "Em andamento", "Concluído", "A refazer" }));
        jPanel1.add(comboStatus);
        comboStatus.setBounds(300, 250, 450, 30);

        txtCodigo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtCodigo.setToolTipText("");
        jPanel1.add(txtCodigo);
        txtCodigo.setBounds(300, 150, 450, 30);

        txtProjeto.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtProjeto);
        txtProjeto.setBounds(300, 100, 450, 30);

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
        jLabel6.setText("Código da Junta");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 150, 210, 30);

        jLabel5.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel5.setText("Comprimento (mm)");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 200, 220, 30);

        jLabel4.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel4.setText("Projeto");
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
        jLabel1.setText("ATUALIZAR JUNTA");
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

        txtComprimento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtComprimento);
        txtComprimento.setBounds(300, 200, 450, 30);

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
        this.dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    private void botaoCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCadastrarMouseClicked
        // Recebendo os valores cadastrados.
        String projeto = txtProjeto.getText();
        String codigo = txtCodigo.getText();
        String comprimentoString = txtComprimento.getText();
        String selecionado = comboStatus.getSelectedItem().toString();

        String status;
        double comprimento;

        status = switch (selecionado) {
            case "Não realizado" ->
                "nao_realizado";
            case "Em andamento" ->
                "em_andamento";
            case "Concluído" ->
                "concluido";
            case "A refazer" ->
                "refazer";
            default ->
                "nao_realizado";
        };

        try {
            // Converter vírgula para ponto, se o usuário digitou assim
            comprimentoString = comprimentoString.replace(",", ".");
            comprimento = Double.parseDouble(comprimentoString);

            if (comprimento <= 0) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "O comprimento deve ser maior que zero!",
                        "Atenção", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Comprimento inválido! Digite apenas números.",
                    "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Enviando o cadastro.
        Juntas j = new Juntas();
        j.setProjeto(projeto);
        j.setCodigo(codigo);
        j.setComprimento(comprimento);
        j.setStatus(status);

        // Utilizando o método 'inserirJunta' da classe DAO.
        JuntasDAO dao = new JuntasDAO();

        // CORREÇÃO: Captura o resultado da operação
        boolean sucesso = dao.atualizarJunta(j);

        // CORREÇÃO: Só fecha a tela se a operação foi um sucesso (validou e inseriu)
        if (sucesso) {
            // Atualiza a tabela da tela principal
            if (telaJuntas != null) {
                telaJuntas.carregarTabela();
            }

            this.dispose(); // Fecha a tela SOMENTE se foi sucesso
        }
    }//GEN-LAST:event_botaoCadastrarMouseClicked

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
    private javax.swing.JFormattedTextField txtComprimento;
    private javax.swing.JTextField txtProjeto;
    // End of variables declaration//GEN-END:variables
}
