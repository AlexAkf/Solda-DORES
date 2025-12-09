package tela_projetos;

import dao.ProjetosDAO;
import java.awt.Color;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import models.Projetos;
import util.Fonte;

/**
 *
 * @author hugos
 */
public class FichaEdicao extends javax.swing.JFrame {

    private final TelaProjetos telaProjetos;
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private int idSelecionado = -1;

    public FichaEdicao(TelaProjetos telaProjetos) {
        initComponents();
        this.telaProjetos = telaProjetos;
        setBackground(new java.awt.Color(0, 0, 0, 0));

        configurarMascaras();
        configurarDescricao();

        ProjetosDAO dao = new ProjetosDAO();
        aplicarAutoComplete(txtEmpresa, termo -> dao.buscarEmpresas(termo));
        aplicarAutoComplete(txtSupervisor, termo -> dao.buscarSupervisores(termo));
    }

    private void configurarMascaras() {
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');

            txtInicial.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(dataMask));
            txtPrazo.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(dataMask));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void configurarDescricao() {
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setRows(5);
    }

    // ==============================
    //   FUNÇÃO QUE PREENCHE CAMPOS
    // ==============================
    public void preencherCampos(Projetos p) {

        idSelecionado = p.getId();

        txtProjeto.setText(p.getNome());
        txtEmpresa.setText(p.getEmpresa());
        txtSupervisor.setText(p.getSupervisor());

        txtInicial.setText(p.getInicio() != null ? p.getInicio().format(FORMATO_DATA) : "");
        txtPrazo.setText(p.getPrazo() != null ? p.getPrazo().format(FORMATO_DATA) : "");

        txtDescricao.setText(p.getDescricao());
        comboStatus.setSelectedItem(p.getCondicao());
    }

    // Autocomplete
    public void aplicarAutoComplete(JTextField campo, Function<String, List<String>> busca) {

        JPopupMenu popup = new JPopupMenu();
        popup.setFocusable(false);
        popup.setBorder(BorderFactory.createLineBorder(new Color(30, 58, 138), 2));
        popup.setBackground(Color.WHITE);

        final int[] selecionado = {-1};

        campo.putClientProperty("popup", popup);

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

                // Busca no DAO
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

                SwingUtilities.invokeLater(() -> {
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
                        if (popup.isVisible()
                                && selecionado[0] >= 0 && selecionado[0] < count) {

                            JMenuItem item = (JMenuItem) popup.getComponent(selecionado[0]);
                            campo.setText(item.getText());
                            popup.setVisible(false);

                            evt.consume();
                        }
                    }
                }
            }
        });
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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtProjeto = new javax.swing.JTextField();
        txtEmpresa = new javax.swing.JTextField();
        txtSupervisor = new javax.swing.JTextField();
        txtInicial = new javax.swing.JFormattedTextField();
        txtPrazo = new javax.swing.JFormattedTextField();
        comboStatus = new javax.swing.JComboBox<>();
        descricao = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        botaoCadastro = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JLabel();
        ficha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        txtProjeto.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtProjeto);
        txtProjeto.setBounds(460, 370, 450, 50);

        txtEmpresa.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtEmpresa);
        txtEmpresa.setBounds(460, 478, 450, 50);

        txtSupervisor.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtSupervisor);
        txtSupervisor.setBounds(460, 586, 450, 50);

        txtInicial.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtInicial);
        txtInicial.setBounds(460, 694, 450, 50);

        txtPrazo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtPrazo);
        txtPrazo.setBounds(1000, 694, 450, 50);

        comboStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ativo", "avaliacao", "finalizado", "cancelado" }));
        getContentPane().add(comboStatus);
        comboStatus.setBounds(1000, 586, 450, 50);

        txtDescricao.setColumns(20);
        txtDescricao.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtDescricao.setRows(5);
        descricao.setViewportView(txtDescricao);

        getContentPane().add(descricao);
        descricao.setBounds(1000, 370, 450, 156);

        botaoCadastro.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoCadastro.setForeground(new java.awt.Color(255, 255, 255));
        botaoCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoCadastro.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoCadastro.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoCadastro.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoCadastroMouseClicked(evt);
            }
        });
        getContentPane().add(botaoCadastro);
        botaoCadastro.setBounds(766, 773, 390, 70);

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
        botaoCancelar.setBounds(1451, 228, 50, 50);

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaProjetos.png"))); // NOI18N
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCadastroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCadastroMouseClicked
        if (!botaoCadastro.isEnabled()) {
            JOptionPane.showMessageDialog(this,
                    "Edição bloqueada. O projeto está finalizado ou cancelado.");
            return;
        }

        try {
            Projetos p = new Projetos();

            p.setId(idSelecionado);
            p.setNome(txtProjeto.getText());
            p.setEmpresa(txtEmpresa.getText());
            p.setSupervisor(txtSupervisor.getText());
            p.setDescricao(txtDescricao.getText());
            p.setCondicao(comboStatus.getSelectedItem().toString());

            // Data início
            if (!txtInicial.getText().trim().equals("__/__/____")) {
                p.setInicio(LocalDate.parse(txtInicial.getText(), FORMATO_DATA));
            }

            // Data prazo
            if (!txtPrazo.getText().trim().equals("__/__/____")) {
                p.setPrazo(LocalDate.parse(txtPrazo.getText(), FORMATO_DATA));
            }

            // Salvar no banco
            ProjetosDAO dao = new ProjetosDAO();
            dao.atualizar(p);

            JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!");
            telaProjetos.carregarTabela();
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botaoCadastroMouseClicked

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoCadastro;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JScrollPane descricao;
    private javax.swing.JLabel ficha;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JFormattedTextField txtInicial;
    private javax.swing.JFormattedTextField txtPrazo;
    private javax.swing.JTextField txtProjeto;
    private javax.swing.JTextField txtSupervisor;
    // End of variables declaration//GEN-END:variables
}
