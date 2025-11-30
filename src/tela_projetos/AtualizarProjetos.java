package tela_projetos;

import dao.ProjetosDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import models.Projetos;
import util.Fonte;

/**
 *
 * @author Rafael Silva
 */
public class AtualizarProjetos extends javax.swing.JFrame {

    private final TelaProjetos telaProjetos;

    public AtualizarProjetos(TelaProjetos telaProjetos) {
        initComponents();
        this.telaProjetos = telaProjetos;
    }

    public void preencherCampos(Projetos projetos) {
        txtProjeto.setText(projetos.getnome());
        txtEmpresa.setText(String.valueOf(projetos.getfk_empresa()));
        txtSupervisor.setText(String.valueOf(projetos.getfk_supervisor()));
        
        txtInicial.setText(
                projetos.getinicio() != null ? projetos.getinicio().toString() : ""
        );

        txtPrazo.setText(
                projetos.getprazo() != null ? projetos.getprazo().toString() : ""
        );
        txtDescricao.setText(String.valueOf(projetos.getdescricao()));
        combo.setSelectedItem(projetos.getcondicao());
   
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSupervisor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtProjeto = new javax.swing.JTextField();
        txtInicial = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        txtPrazo = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        combo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(770, 590));
        setMinimumSize(new java.awt.Dimension(770, 590));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(770, 590));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setLayout(null);

        jLabel1.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 40f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CADASTRAR PROJETOS");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 50);

        jLabel13.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("CANCELAR");
        jLabel13.setMaximumSize(new java.awt.Dimension(260, 83));
        jLabel13.setMinimumSize(new java.awt.Dimension(260, 83));
        jLabel13.setPreferredSize(new java.awt.Dimension(260, 83));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel13);
        jLabel13.setBounds(40, 480, 260, 83);

        jLabel7.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CADASTRAR");
        jLabel7.setMaximumSize(new java.awt.Dimension(260, 83));
        jLabel7.setMinimumSize(new java.awt.Dimension(260, 83));
        jLabel7.setPreferredSize(new java.awt.Dimension(260, 83));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel7);
        jLabel7.setBounds(470, 480, 260, 83);

        jLabel2.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 480, 260, 83);

        jLabel3.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 480, 260, 83);

        jLabel5.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel5.setText("Empresa");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 150, 140, 30);

        jLabel6.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel6.setText("Projeto");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 100, 90, 30);

        txtSupervisor.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.add(txtSupervisor);
        txtSupervisor.setBounds(300, 200, 450, 30);

        jLabel8.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel8.setText("Supervisor");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 200, 120, 30);

        jLabel9.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel9.setText("Data Inicial");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 250, 120, 30);

        jLabel11.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel11.setText("Condição");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(20, 420, 120, 30);

        txtProjeto.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.add(txtProjeto);
        txtProjeto.setBounds(300, 100, 450, 30);
        jPanel1.add(txtInicial);
        txtInicial.setBounds(300, 250, 450, 30);

        jLabel12.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel12.setText("Prazo Final");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 300, 250, 30);

        txtEmpresa.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.add(txtEmpresa);
        txtEmpresa.setBounds(300, 150, 450, 30);

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane2.setViewportView(txtDescricao);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(300, 340, 450, 70);
        jPanel1.add(txtPrazo);
        txtPrazo.setBounds(300, 300, 450, 30);

        jLabel14.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel14.setText("Descrição");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(20, 350, 120, 30);

        combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ativo", "avaliacao", "finalizado", "cancelado" }));
        combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboActionPerformed(evt);
            }
        });
        jPanel1.add(combo);
        combo.setBounds(300, 420, 450, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 770, 590);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        try {
            // 1 - Pegar valores
            String nomeProjeto = txtProjeto.getText();
            String empresa = txtEmpresa.getText();
            String supervisor = txtSupervisor.getText();
            String dataInicioStr = txtInicial.getText();
            String prazoStr = txtPrazo.getText();
            String descricao = txtDescricao.getText();
            String condicao = combo.getSelectedItem().toString();

            // 2 - Validar
            if (nomeProjeto.isBlank() || empresa.isBlank() || supervisor.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
                return;
            }

            // 3 - Converter datas
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicio = LocalDate.parse(dataInicioStr, formato);
            LocalDate prazo = LocalDate.parse(prazoStr, formato);

            // 4 - Criar objeto Projeto
            Projetos projeto = new Projetos();
            projeto.setNome(nomeProjeto);
            projeto.setFk_empresa(Integer.parseInt(empresa));   // Ajuste se necessário
            projeto.setFk_supervisor(Integer.parseInt(supervisor)); // Ajuste se necessário
            projeto.setInicio(dataInicio);
            projeto.setPrazo(prazo);
            projeto.setDescricao(descricao);
            projeto.setCondicao(condicao);

            // 5 - Salvar no banco
            ProjetosDAO dao = new ProjetosDAO();
            dao.inserir(projeto);

            JOptionPane.showMessageDialog(this, "Projeto cadastrado com sucesso!");

            if (telaProjetos != null) {
                telaProjetos.carregarTabela();
            }
            this.dispose(); // fecha a tela

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage());
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboActionPerformed

    }//GEN-LAST:event_comboActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JFormattedTextField txtInicial;
    private javax.swing.JFormattedTextField txtPrazo;
    private javax.swing.JTextField txtProjeto;
    private javax.swing.JTextField txtSupervisor;
    // End of variables declaration//GEN-END:variables

}
