package tela_funcionarios;

import dao.UsuariosDAO;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Usuarios;
import util.Fonte;

/**
 *
 * @author Alex
 */

public class FichaEdicao extends javax.swing.JFrame {

    private final TelaFuncionarios TELA;
    private int selecionado;    // Guarda o id do usuário que foi selecionado
    private String loginOriginal;
    private String nomeOriginal;
    private String cargoOriginal;

    public FichaEdicao(TelaFuncionarios telaFuncionarios) {
        initComponents();
        // Frame invisivel
        setBackground(new java.awt.Color(0, 0, 0, 0));
        this.TELA = telaFuncionarios;
        data();

        aplicarAutoComplete(txtSupervisor, termo -> {
            try {
                UsuariosDAO dao = new UsuariosDAO();

                // Buscar todos usuários que sejam supervisores e combinem com o termo
                return dao.listar().stream()
                        .filter(u -> "supervisor".equalsIgnoreCase(u.getCargo()))
                        .filter(u -> u.getNome().toLowerCase().contains(termo.toLowerCase()))
                        .map(Usuarios::getNome)
                        .toList();

            } catch (SQLException e) {
                return java.util.Collections.emptyList();
            }
        });

        txtNome.addActionListener(e -> botaoAtualizarMouseClicked(null));
        txtEmail.addActionListener(e -> botaoAtualizarMouseClicked(null));
        txtSinete.addActionListener(e -> botaoAtualizarMouseClicked(null));
        txtValidade.addActionListener(e -> botaoAtualizarMouseClicked(null));
        txtSupervisor.addActionListener(e -> botaoAtualizarMouseClicked(null));

        txtCargo.addKeyListener(new java.awt.event.KeyAdapter() {
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
                boolean popupFechado = false;

                // Fechar popup de txtSoldador se estiver visível
                if (txtSupervisor.isFocusOwner() && txtSupervisor.getClientProperty("popup") instanceof JPopupMenu popup1 && popup1.isVisible()) {
                    popup1.setVisible(false);
                    popupFechado = true;
                }

                // Se nenhum popup estava aberto, fecha a janela
                if (!popupFechado) {
                    botaoCancelarMouseClicked(null);
                }
            }
        });

        // Marca que o usuário alterou a data quando ele sair do campo
        txtValidade.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Só marca se tiver algum valor digitado
                String texto = txtValidade.getText().trim();
                if (!texto.isEmpty() && !texto.equals("__/__/____")) {
                }
            }
        });
    }

    // Preenche os campos da tela com os dados do usuário
    public void preencherCampos(Usuarios usuario) {
        this.selecionado = usuario.getId();

        txtNome.setText(usuario.getNome());
        txtCpf.setText(usuario.getCpf());
        txtEmail.setText(usuario.getEmail());
        txtLogin.setText(usuario.getLogin());
        nomeOriginal = usuario.getNome();
        cargoOriginal = usuario.getCargo();
        loginOriginal = usuario.getLogin();

        // Garantir que o combo box selecione corretamente o cargo do usuário
        String cargoUsuario = usuario.getCargo();
        if (cargoUsuario != null) {
            switch (cargoUsuario.trim().toLowerCase()) {
                case "gestor" ->
                    txtCargo.setSelectedItem("Gestor");
                case "supervisor" ->
                    txtCargo.setSelectedItem("Supervisor");
                case "soldador" ->
                    txtCargo.setSelectedItem("Soldador");
                default ->
                    txtCargo.setSelectedItem("---"); // valor padrão se não encontrado
            }
        } else {
            txtCargo.setSelectedItem("---");
        }

        boolean isSoldador = "soldador".equalsIgnoreCase(usuario.getCargo());
        txtSinete.setEnabled(isSoldador);
        txtValidade.setEnabled(isSoldador);
        txtSupervisor.setEnabled(isSoldador);

        txtSinete.setText(
                usuario.getSinete() != null && !usuario.getSinete().equals("—") ? usuario.getSinete() : ""
        );
        txtSupervisor.setText(usuario.getSupervisor() != null ? usuario.getSupervisor().getNome() : "");

        if (usuario.getSolda() != null) {
            txtValidade.setText(usuario.getSolda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    public void usuarioSelecionado(Usuarios usuario) {
        this.selecionado = usuario.getId();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNome = new javax.swing.JTextField();
        txtCpf = new javax.swing.JFormattedTextField();
        txtEmail = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        txtCargo = new javax.swing.JComboBox<>();
        txtSinete = new javax.swing.JTextField();
        txtValidade = new javax.swing.JFormattedTextField();
        txtSupervisor = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        botaoAtualizar = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JLabel();
        ficha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
        getContentPane().setLayout(null);

        txtNome.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtNome);
        txtNome.setBounds(260, 250, 450, 50);

        txtCpf.setEnabled(false);
        txtCpf.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtCpf);
        txtCpf.setBounds(260, 383, 450, 50);

        txtEmail.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtEmail);
        txtEmail.setBounds(260, 516, 450, 50);

        txtLogin.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtLogin.setEnabled(false);
        getContentPane().add(txtLogin);
        txtLogin.setBounds(260, 783, 450, 50);

        txtCargo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Gestor", "Supervisor", "Soldador" }));
        txtCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCargoActionPerformed(evt);
            }
        });
        getContentPane().add(txtCargo);
        txtCargo.setBounds(260, 649, 450, 50);

        txtSinete.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtSinete.setEnabled(false);
        getContentPane().add(txtSinete);
        txtSinete.setBounds(765, 516, 450, 50);

        txtValidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##/##/####"))));
        txtValidade.setEnabled(false);
        txtValidade.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtValidade);
        txtValidade.setBounds(765, 649, 450, 50);

        txtSupervisor.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtSupervisor.setEnabled(false);
        getContentPane().add(txtSupervisor);
        txtSupervisor.setBounds(765, 383, 450, 50);

        txtStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtStatus.setText("COD");
        txtStatus.setEnabled(false);
        getContentPane().add(txtStatus);
        txtStatus.setBounds(765, 782, 450, 50);

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
        botaoAtualizar.setBounds(740, 870, 442, 106);

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
        botaoCancelar.setBounds(1640, 103, 50, 50);

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaFuncionarios.png"))); // NOI18N
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCargoActionPerformed
        String cargoSelecionado = txtCargo.getSelectedItem().toString();
        boolean isSoldador = cargoSelecionado.equalsIgnoreCase("Soldador");

        txtSinete.setEnabled(isSoldador);
        txtValidade.setEnabled(isSoldador);
        txtSupervisor.setEnabled(isSoldador);
    }//GEN-LAST:event_txtCargoActionPerformed

    private void botaoAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoAtualizarMouseClicked
        atualizarUsuario();
    }//GEN-LAST:event_botaoAtualizarMouseClicked

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    private void atualizarUsuario() {
        try {
            var dao = new UsuariosDAO();

            // Busca o usuário existente pelo id
            Usuarios usuario = dao.buscar_id(selecionado);
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado no banco.");
                return;
            }

            String nome = txtNome.getText().trim();
            if (!validaNome(nome)) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, insira um nome e sobrenome válido.");
                return;
            }
            usuario.setNome(formatarNome(nome));

            String email = txtEmail.getText().trim();
            if (!validaEmail(email)) {
                JOptionPane.showMessageDialog(this, "E-mail inválido! Verifique o endereço e tente novamente.");
                return;
            }
            usuario.setEmail(email);

            String novoNome = formatarNome(nome);
            String novoCargo = txtCargo.getSelectedItem().toString().trim();

            // Atualiza login somente se nome ou cargo mudarem
            if (!novoNome.equals(nomeOriginal) || !novoCargo.equalsIgnoreCase(cargoOriginal)) {
                usuario.setLogin(gerarLogin(novoNome, novoCargo));
                txtLogin.setText(usuario.getLogin());
            } else {
                usuario.setLogin(loginOriginal); // mantém o login original
            }

            String cargo = txtCargo.getSelectedItem().toString().trim();
            usuario.setCargo(cargo);

            if (!cargo.equalsIgnoreCase("Soldador")) {

                // limpar do banco
                usuario.setSinete(null);
                usuario.setSupervisor(null);
                usuario.setSolda(null);
                usuario.setValidade(null);

                // limpar da tela
                txtSinete.setText("");
                txtSupervisor.setText("");
                txtValidade.setText("");

            } else {
                // Só processa dados de soldador se for soldador
                if ("soldador".equalsIgnoreCase(usuario.getCargo())) {
                    usuario.setSinete(txtSinete.getText().toUpperCase().trim());

                    String supervisorNome = txtSupervisor.getText().trim();
                    Usuarios supervisor = null;
                    if (!supervisorNome.isEmpty()) {
                        supervisor = dao.listar().stream()
                                .filter(u -> u.getNome().equalsIgnoreCase(supervisorNome))
                                .findFirst()
                                .orElse(null);

                        if (supervisor == null) {
                            JOptionPane.showMessageDialog(this, "Supervisor não encontrado!");
                            return;
                        }
                    }
                    usuario.setSupervisor(supervisor);

                    // Só atualiza se o usuário digitou uma data válida
                    String textoValidade = txtValidade.getText().trim();
                    if (!textoValidade.isEmpty() && !textoValidade.equals("__/__/____")) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate validade = LocalDate.parse(textoValidade, formatter);

                            LocalDate hoje = LocalDate.now();
                            if (validade.isBefore(hoje)) {
                                JOptionPane.showMessageDialog(this, "A validade do certificado já expirou!");
                                return;
                            }

                            usuario.setValidade(validade);
                            usuario.setSolda(validade.minusDays(30));

                        } catch (HeadlessException e) {
                            JOptionPane.showMessageDialog(this, "Data inválida! Use o formato dd/MM/yyyy.");
                            return;
                        }
                    }
                    // Caso o campo esteja vazio ou igual ao placeholder, mantém a data antiga do banco
                }
            }
            // Atualiza no banco
            dao.atualizar(usuario);

            // Atualiza a tabela da tela principal
            if (TELA != null) {
                TELA.carregarTabela();
            }

            this.dispose();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário: " + erro.getMessage());
        }
    }

    private boolean validaNome(String nome) {
        nome = nome.trim();

        if (nome.isEmpty()) {
            return false;
        }

        // Apenas letras e espaços
        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$")) {
            return false;
        }

        // Precisa ter pelo menos duas palavras
        String[] partes = nome.split("\\s+");
        if (partes.length < 2) {
            return false;
        }

        // Cada parte deve ter no mínimo 2 letras
        for (String p : partes) {
            if (p.length() < 2) {
                return false;
            }
        }

        return true;
    }

    private String formatarNome(String nome) {
        StringBuilder sb = new StringBuilder();
        for (String p : nome.trim().toLowerCase().split("\\s+")) {
            sb.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1))
                    .append(" ");
        }
        return sb.toString().trim();
    }

    private void data() {
        try {
            javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            txtValidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mf));
        } catch (java.text.ParseException ex) {
        }
    }

    private String sigla(String cargo) {
        return switch (cargo.toLowerCase()) {
            case "gestor" ->
                "adm";
            case "supervisor" ->
                "res";
            default ->
                "com";
        };
    }

    private String gerarLogin(String nomeCompleto, String cargo) throws SQLException {
        String[] partes = nomeCompleto.trim().split("\\s+");    // Picota o nome identificando o espaço entre strings

        String primeiroNome = partes[0].toLowerCase();
        String ultimoNome = partes[partes.length - 1].toLowerCase();

        String baseLogin = primeiroNome + "." + ultimoNome + "@" + sigla(cargo); // Login padronizado, ex: Alex Silva -> alex.silva@adm
        String login = baseLogin;
        int contador = 1;

        UsuariosDAO dao = new UsuariosDAO();
        // Verifica se já existe no banco, se existir adicional o contador
        while (dao.buscar_login(login) != null) {
            login = primeiroNome + "." + ultimoNome + contador + "@" + sigla(cargo);
            contador++;
        }

        return login;
    }

    private boolean validaEmail(String email) {
        String formato = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(formato);
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
                            // NÃO chama botaoCadastrar aqui
                        } else {
                            // Se não houver popup visível, dispara o cadastro
                            botaoAtualizarMouseClicked(null);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoAtualizar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JLabel ficha;
    private javax.swing.JComboBox<String> txtCargo;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSinete;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtSupervisor;
    private javax.swing.JFormattedTextField txtValidade;
    // End of variables declaration//GEN-END:variables
}
