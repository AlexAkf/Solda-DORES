package tela_funcionarios;

import dao.UsuariosDAO;
import java.sql.SQLException;
import models.Usuarios;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import util.Fonte;

/**
 *
 * @author Alex
 */

public class AtualizarFuncionarios extends javax.swing.JFrame {

    private final TelaFuncionarios TELA;
    private int selecionado;    // Guarda o id do usuário que foi selecionado
    private String loginOriginal;
    private String nomeOriginal;
    private String cargoOriginal;

    public AtualizarFuncionarios(TelaFuncionarios telaFuncionarios) {
        initComponents();
        this.TELA = telaFuncionarios;
        data();
        
         // Marca que o usuário alterou a data quando ele sair do campo
        txtSolda.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Só marca se tiver algum valor digitado
                String texto = txtSolda.getText().trim();
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
        txtSolda.setEnabled(isSoldador);
        txtSupervisor.setEnabled(isSoldador);

        txtSinete.setText(
                usuario.getSinete() != null && !usuario.getSinete().equals("—") ? usuario.getSinete() : ""
        );
        txtSupervisor.setText(usuario.getSupervisor() != null ? usuario.getSupervisor().getNome() : "");

        if (usuario.getSolda() != null) {
            txtSolda.setText(usuario.getSolda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }
    
    public void usuarioSelecionado(Usuarios usuario) {
        this.selecionado = usuario.getId();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JLabel();
        botaoAtualizar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCargo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtSinete = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtSolda = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSupervisor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(770, 730));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setLayout(null);

        jLabel1.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 40f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ATUALIZAR USUÁRIO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 50);

        botaoCancelar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botaoCancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoCancelar.setText("CANCELAR");
        botaoCancelar.setToolTipText("");
        botaoCancelar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoCancelar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoCancelar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoCancelarMouseClicked(evt);
            }
        });
        jPanel1.add(botaoCancelar);
        botaoCancelar.setBounds(40, 620, 260, 83);

        botaoAtualizar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoAtualizar.setForeground(new java.awt.Color(255, 255, 255));
        botaoAtualizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoAtualizar.setText("ATUALIZAR");
        botaoAtualizar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoAtualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoAtualizarMouseClicked(evt);
            }
        });
        jPanel1.add(botaoAtualizar);
        botaoAtualizar.setBounds(470, 620, 260, 83);

        jLabel2.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 620, 260, 83);

        jLabel3.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 620, 260, 83);

        jLabel5.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel5.setText("CPF");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 150, 70, 30);

        jLabel6.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel6.setText("Nome");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 100, 90, 30);

        txtNome.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtNome);
        txtNome.setBounds(300, 100, 450, 30);

        jLabel9.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel9.setText("Login");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 250, 100, 30);

        txtCpf.setEnabled(false);
        txtCpf.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtCpf);
        txtCpf.setBounds(300, 150, 450, 30);

        jLabel10.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel10.setText("E-mail");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(20, 200, 100, 30);

        txtEmail.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtEmail);
        txtEmail.setBounds(300, 200, 450, 30);

        txtLogin.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtLogin.setEnabled(false);
        jPanel1.add(txtLogin);
        txtLogin.setBounds(300, 250, 450, 30);

        jLabel12.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel12.setText("Cargo");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 300, 250, 30);

        txtCargo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Gestor", "Supervisor", "Soldador" }));
        txtCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCargoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCargo);
        txtCargo.setBounds(300, 300, 450, 30);

        jLabel4.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Campos para Soldador");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(0, 380, 770, 40);

        txtSinete.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtSinete.setEnabled(false);
        jPanel1.add(txtSinete);
        txtSinete.setBounds(300, 450, 450, 30);

        jLabel15.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel15.setText("Supervisor");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(20, 550, 250, 30);

        jLabel16.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel16.setText("Cod. Sinete");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(20, 450, 250, 30);

        txtSolda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##/##/####"))));
        txtSolda.setEnabled(false);
        txtSolda.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtSolda);
        txtSolda.setBounds(300, 500, 450, 30);

        jLabel17.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel17.setText("Última Solda");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(20, 500, 250, 30);

        txtSupervisor.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        txtSupervisor.setEnabled(false);
        jPanel1.add(txtSupervisor);
        txtSupervisor.setBounds(300, 550, 450, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 770, 730);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCargoActionPerformed
        String cargoSelecionado = txtCargo.getSelectedItem().toString();
        boolean isSoldador = cargoSelecionado.equalsIgnoreCase("Soldador");

        txtSinete.setEnabled(isSoldador);
        txtSolda.setEnabled(isSoldador);
        txtSupervisor.setEnabled(isSoldador);
    }//GEN-LAST:event_txtCargoActionPerformed

    private void botaoAtualizarMouseClicked(java.awt.event.MouseEvent evt) {
        atualizarUsuario();
    }

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }
    
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
                txtSolda.setText("");

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

                // Atualiza a última solda
                if (!txtSolda.getText().trim().isEmpty() && !txtSolda.getText().trim().equals("__/__/____")) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate ultimaSolda = LocalDate.parse(txtSolda.getText().trim(), formatter);

                        // Validação da data
                        LocalDate hoje = LocalDate.now();
                        if (ultimaSolda.isAfter(hoje)) {
                            JOptionPane.showMessageDialog(this, "A data da última solda não pode ser no futuro!");
                            return;
                        }

                        usuario.setSolda(ultimaSolda);
                        usuario.setValidade(ultimaSolda.plusDays(30));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Data inválida! Use o formato dd/MM/yyyy.");
                        return;
                    }
                }
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

        if (nome.isEmpty()) return false;

        // Apenas letras e espaços
        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$")) return false;

        // Precisa ter pelo menos duas palavras
        String[] partes = nome.split("\\s+");
        if (partes.length < 2) return false;

        // Cada parte deve ter no mínimo 2 letras
        for (String p : partes) {
            if (p.length() < 2) return false;
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
            txtSolda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mf));
        } catch (java.text.ParseException ex) {
        }
    }
    
    private String sigla(String cargo) {
        return switch (cargo.toLowerCase()) {
            case "gestor" -> "adm";
            case "supervisor" -> "res";
            default -> "com";
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoAtualizar;
    private javax.swing.JLabel botaoCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> txtCargo;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSinete;
    private javax.swing.JFormattedTextField txtSolda;
    private javax.swing.JTextField txtSupervisor;
    // End of variables declaration//GEN-END:variables
}