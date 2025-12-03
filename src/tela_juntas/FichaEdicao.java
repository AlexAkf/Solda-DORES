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
public class FichaEdicao extends javax.swing.JFrame {

    private TelaJuntas telaJuntas;
    private Juntas juntaOriginal;

    public FichaEdicao(TelaJuntas telaJuntas) {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
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
        this.juntaOriginal = j; // <-- AQUI ESTÁ FALTANDO!

        txtProjeto.setText(j.getProjeto());
        txtCodigo.setText(j.getCodigo());
        txtComprimento.setText(String.valueOf(j.getComprimento()));

        String status = j.getStatus();
        String exibicao = switch (status.toLowerCase()) {
            case "nao_realizado" ->
                "Não realizado";
            case "em_andamento" ->
                "Em andamento";
            case "concluido" ->
                "Concluído";
            case "refazer" ->
                "A refazer";
            default ->
                status;
        };

        comboStatus.setSelectedItem(exibicao);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtProjeto = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtComprimento = new javax.swing.JFormattedTextField();
        comboStatus = new javax.swing.JComboBox<>();
        botaoCancelar = new javax.swing.JLabel();
        botaoCadastrar = new javax.swing.JLabel();
        ficha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        txtProjeto.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtProjeto);
        txtProjeto.setBounds(730, 350, 450, 50);

        txtCodigo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtCodigo.setToolTipText("");
        getContentPane().add(txtCodigo);
        txtCodigo.setBounds(730, 480, 450, 50);

        txtComprimento.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtComprimento);
        txtComprimento.setBounds(730, 610, 450, 50);

        comboStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Não realizado", "Em andamento", "Concluído", "A refazer" }));
        getContentPane().add(comboStatus);
        comboStatus.setBounds(730, 730, 450, 50);

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
        botaoCancelar.setBounds(1201, 188, 50, 50);

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
        botaoCadastrar.setBounds(765, 824, 390, 70);

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaJuntas.png"))); // NOI18N
        ficha.setMaximumSize(new java.awt.Dimension(1920, 1080));
        ficha.setMinimumSize(new java.awt.Dimension(1920, 1080));
        ficha.setPreferredSize(new java.awt.Dimension(1920, 1080));
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);

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
        j.setId(juntaOriginal.getId());

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
    private javax.swing.JLabel ficha;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtComprimento;
    private javax.swing.JTextField txtProjeto;
    // End of variables declaration//GEN-END:variables
}
