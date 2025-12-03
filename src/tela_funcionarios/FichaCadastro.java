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
import util.Hash;

/**
 *
 * @author Alex
 */
public class FichaCadastro extends javax.swing.JFrame {

    private final TelaFuncionarios TELA;

    public FichaCadastro(TelaFuncionarios tela) {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        this.TELA = tela;
        data();
        formatarCPF();

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

        // ENTER aciona o cadastrarUsuario()
        txtNome.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtCpf.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtEmail.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtSinete.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtValidade.addActionListener(e -> botaoCadastrarMouseClicked(null));
        txtSupervisor.addActionListener(e -> botaoCadastrarMouseClicked(null));

        txtCargo.addKeyListener(new java.awt.event.KeyAdapter() {
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
    }

    private void cadastrarUsuario() {
        try {
            var dao = new UsuariosDAO();
            var usuario = new Usuarios();

            // Preenchendo os dados do usuário
            usuario.setCpf(txtCpf.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setCargo(txtCargo.getSelectedItem().toString().trim());

            switch (usuario.getCargo().toLowerCase()) {
                case "gestor" ->
                    usuario.setPerfil("adm");
                case "supervisor" ->
                    usuario.setPerfil("restrito");
                default ->
                    usuario.setPerfil("comum");
            }

            String nome = txtNome.getText().trim();
            if (!validaNome(nome)) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, insira um nome e sobrenome válido.");
                return;
            }
            usuario.setNome(formatarNome(txtNome.getText()));

            String cpf = txtCpf.getText().trim();
            if (!validaCPF(cpf)) {
                JOptionPane.showMessageDialog(this, "CPF inválido! Verifique o número e tente novamente.");
                return;
            }

            String email = txtEmail.getText().trim();
            if (!validaEmail(email)) {
                JOptionPane.showMessageDialog(this, "E-mail inválido! Verifique o endereço e tente novamente.");
                return;
            }

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

                if (!txtValidade.getText().trim().isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate validade = LocalDate.parse(txtValidade.getText().trim(), formatter);

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
            }

            usuario.setLogin(gerarLogin(usuario.getNome(), usuario.getCargo()));
            txtLogin.setText(usuario.getLogin());

            String senha_padrao = "inicial";
            String senha_hash = Hash.gerarHash(senha_padrao);
            usuario.setSenha(senha_hash);

            var usuarioExistente = dao.buscarPorCpf(usuario.getCpf()); // Verifica se o cpf já existe
            if (usuarioExistente != null) { // Se existir reativa a conta

                // Atualiza os dados do usuário existente
                usuarioExistente.setNome(usuario.getNome());
                usuarioExistente.setEmail(usuario.getEmail());
                usuarioExistente.setCargo(usuario.getCargo());
                usuarioExistente.setPerfil(usuario.getPerfil());
                usuarioExistente.setLogin(gerarLogin(usuario.getNome(), usuario.getCargo()));
                usuarioExistente.setSenha(usuario.getSenha());
                usuarioExistente.setAtivo(true);

                // Campos exclusivos de soldador
                usuarioExistente.setSinete(usuario.getSinete());
                usuarioExistente.setSupervisor(usuario.getSupervisor());
                usuarioExistente.setValidade(usuario.getValidade());
                usuarioExistente.setSolda(usuario.getSolda());

                // Reativa e atualiza
                dao.atualizar(usuarioExistente);
                if (TELA != null) {
                    TELA.carregarTabela();
                }
                this.dispose();
                return;
            }

            dao.inserir(usuario);

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");

            if (TELA != null) {
                TELA.carregarTabela();
            }

            this.dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário: " + e.getMessage());
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + e.getMessage());
        }
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
        botaoCancelar = new javax.swing.JLabel();
        botaoCadastrar = new javax.swing.JLabel();
        ficha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        txtNome.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtNome.setText("Fulano de Tal");
        getContentPane().add(txtNome);
        txtNome.setBounds(260, 250, 450, 50);

        txtCpf.setText("123.456.789-10");
        txtCpf.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtCpf);
        txtCpf.setBounds(260, 383, 450, 50);

        txtEmail.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtEmail.setText("exemplo@email.com");
        getContentPane().add(txtEmail);
        txtEmail.setBounds(260, 516, 450, 50);

        txtLogin.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtLogin.setText("fulano.tal@cargo");
        txtLogin.setEnabled(false);
        getContentPane().add(txtLogin);
        txtLogin.setBounds(260, 782, 450, 50);

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
        txtSinete.setText("COD");
        txtSinete.setEnabled(false);
        getContentPane().add(txtSinete);
        txtSinete.setBounds(765, 383, 450, 50);

        txtValidade.setText("DD/MM/AAAA");
        txtValidade.setEnabled(false);
        txtValidade.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtValidade);
        txtValidade.setBounds(765, 649, 450, 50);

        txtSupervisor.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtSupervisor.setText("Ciclano Silva");
        txtSupervisor.setEnabled(false);
        getContentPane().add(txtSupervisor);
        txtSupervisor.setBounds(765, 516, 450, 50);

        txtStatus.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtStatus.setText("Ativo");
        txtStatus.setEnabled(false);
        getContentPane().add(txtStatus);
        txtStatus.setBounds(765, 782, 450, 50);

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
        botaoCadastrar.setBounds(740, 870, 442, 106);

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaFuncionarios.png"))); // NOI18N
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);
        ficha.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCargoActionPerformed
        boolean isSoldador = "Soldador".equalsIgnoreCase(txtCargo.getSelectedItem().toString());
        txtSinete.setEnabled(isSoldador);
        txtValidade.setEnabled(isSoldador);
        txtSupervisor.setEnabled(isSoldador);

        if (!isSoldador) {
            txtSinete.setText("");
            txtValidade.setText("");
            txtSupervisor.setText("");
        }
    }//GEN-LAST:event_txtCargoActionPerformed

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_botaoCancelarMouseClicked

    private void botaoCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCadastrarMouseClicked
        cadastrarUsuario();
    }//GEN-LAST:event_botaoCadastrarMouseClicked

    private void data() {
        try {
            javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            txtValidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mf));
        } catch (java.text.ParseException ex) {
        }
    }

    private void formatarCPF() {
        try {
            javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter("###.###.###-##");
            mf.setPlaceholderCharacter('_');
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mf));
        } catch (java.text.ParseException ex) {
        }
    }

    private boolean validaCPF(String cpf) {
        // Remove pontos e traço
        cpf = cpf.replace(".", "").replace("-", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int[] numeros = new int[11];
            for (int i = 0; i < 11; i++) {
                numeros[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }

            // Calcula o primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += numeros[i] * (10 - i);
            }
            int resto = 11 - (soma % 11);
            int dig1 = (resto == 10 || resto == 11) ? 0 : resto;

            // Calcula o segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += numeros[i] * (11 - i);
            }
            resto = 11 - (soma % 11);
            int dig2 = (resto == 10 || resto == 11) ? 0 : resto;

            return numeros[9] == dig1 && numeros[10] == dig2;
        } catch (NumberFormatException e) {
            return false;
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
        /* Aqui tem uma expressão regular para validar email, eu não entendi muito bem como funciona.
           Mas ela vai conferir caracteres, permitir ponto, traço, obriga a ter arroba e o domínio */
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoCadastrar;
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
