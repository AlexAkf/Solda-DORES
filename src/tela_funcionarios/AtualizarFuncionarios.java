package tela_funcionarios;

import dao.UsuariosDAO;
import java.sql.SQLException;
import models.Usuarios;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */

public class AtualizarFuncionarios extends javax.swing.JFrame {

    private final TelaFuncionarios telaFuncionarios;
    private int idUsuarioSelecionado;

    public AtualizarFuncionarios(TelaFuncionarios telaFuncionarios) {
        initComponents();
        this.telaFuncionarios = telaFuncionarios;
    }

    // Preenche os campos da tela com os dados do usuário
    public void preencherCampos(Usuarios usuario) {
        this.idUsuarioSelecionado = usuario.getId();

        txtNome.setText(usuario.getNome());
        txtCpf.setText(usuario.getCpf());
        txtEmail.setText(usuario.getEmail());
        txtLogin.setText(usuario.getLogin());
        txtCargo.setSelectedItem(usuario.getCargo());

        txtSinete.setText(usuario.getSinete() != null ? usuario.getSinete() : "");
        txtSupervisor.setText(usuario.getSupervisor() != null ? usuario.getSupervisor().getNome() : "");

        if (usuario.getSolda() != null) {
            txtSolda.setText(usuario.getSolda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    public void setIdUsuarioSelecionado(int id) {
        this.idUsuarioSelecionado = id;
    }

    public void setUsuarioSelecionado(Usuarios usuario) {
        this.idUsuarioSelecionado = usuario.getId();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
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
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ATUALIZAR USUÁRIO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 48);

        botaoCancelar.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
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

        botaoAtualizar.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
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
        botaoAtualizar.setBounds(470, 620, 260, 83);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 620, 260, 83);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 620, 260, 83);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("CPF");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 150, 40, 32);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Nome");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 100, 90, 32);

        txtNome.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.add(txtNome);
        txtNome.setBounds(300, 100, 450, 30);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("Login");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 250, 100, 32);
        jPanel1.add(txtCpf);
        txtCpf.setBounds(300, 150, 450, 30);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("E-mail");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(20, 200, 100, 32);

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.add(txtEmail);
        txtEmail.setBounds(300, 200, 450, 30);

        txtLogin.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.add(txtLogin);
        txtLogin.setBounds(300, 250, 450, 30);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel12.setText("Cargo");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 300, 250, 30);

        txtCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Gestor", "Supervisor", "Soldador" }));
        jPanel1.add(txtCargo);
        txtCargo.setBounds(300, 300, 450, 30);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Campos para Soldador");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(0, 380, 770, 40);

        txtSinete.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.add(txtSinete);
        txtSinete.setBounds(300, 450, 450, 30);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel15.setText("Supervisor");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(20, 550, 250, 30);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel16.setText("Cod. Sinete");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(20, 450, 250, 30);

        txtSolda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##/##/####"))));
        jPanel1.add(txtSolda);
        txtSolda.setBounds(300, 500, 450, 30);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel17.setText("Última Solda");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(20, 500, 250, 30);
        jPanel1.add(txtSupervisor);
        txtSupervisor.setBounds(300, 550, 450, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 770, 730);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAtualizarMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_botaoAtualizarMouseClicked
        atualizarUsuario();
    }// GEN-LAST:event_botaoAtualizarMouseClicked

    private void botaoCancelarMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_botaoCancelarMouseClicked
        this.dispose();
    }// GEN-LAST:event_botaoCancelarMouseClicked

    /**
     * @param args the command line arguments
     */
    private void atualizarUsuario() {
        try {
            Usuarios usuario = new Usuarios();
            usuario.setId(idUsuarioSelecionado);
            usuario.setNome(txtNome.getText());
            usuario.setCpf(txtCpf.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setLogin(txtLogin.getText());
            usuario.setCargo(txtCargo.getSelectedItem().toString());
            usuario.setSinete(txtSinete.getText());

            String supervisorNome = txtSupervisor.getText().trim();
            if (!supervisorNome.isEmpty()) {
                Usuarios supervisor = new Usuarios();
                supervisor.setNome(supervisorNome);
                usuario.setSupervisor(supervisor);
            }

            // Última solda
            String ultimaSoldaStr = txtSolda.getText().trim();
            if (!ultimaSoldaStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ultimaSolda = LocalDate.parse(ultimaSoldaStr, formatter);
                usuario.setSolda(ultimaSolda);
            }

            // Atualiza no banco
            UsuariosDAO dao = new UsuariosDAO();
            dao.atualizar(usuario);

            // Atualiza a tabela da tela principal
            if (telaFuncionarios != null) {
                telaFuncionarios.carregarTabela();
            }

            this.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário: " + e.getMessage());
        }
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