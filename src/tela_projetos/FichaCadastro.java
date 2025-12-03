package tela_projetos;

import dao.ProjetosDAO;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import models.Projetos;
import util.Fonte;

/**
 *
 * @author hugos
 */
public class FichaCadastro extends javax.swing.JFrame {

    private final TelaProjetos tela_projetos;

    public FichaCadastro(TelaProjetos tela_projetos) {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        this.tela_projetos = tela_projetos;

        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');

            txtInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(dataMask));
            txtPrazo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(dataMask));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtDescricao.setLineWrap(true);          // Quebra de linha automática
        txtDescricao.setWrapStyleWord(true);     // Quebra apenas entre palavras
        txtDescricao.setRows(5);                 // Altura inicial (em linhas)
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
        botaoAtualizar = new javax.swing.JLabel();
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
        txtInicial.setBounds(1000, 694, 450, 50);

        txtPrazo.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtPrazo);
        txtPrazo.setBounds(460, 694, 450, 50);

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
        botaoAtualizar.setBounds(1451, 228, 50, 50);

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaProjetos.png"))); // NOI18N
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCadastroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCadastroMouseClicked
        try {

            // -----------------------------
            // 1. CAPTURAR VALORES
            // -----------------------------
            String nomeProjeto = txtProjeto.getText().trim();
            String empresaStr = txtEmpresa.getText().trim();
            String supervisorStr = txtSupervisor.getText().trim();
            String inicioStr = txtInicial.getText().trim();
            String prazoStr = txtPrazo.getText().trim();
            String descricao = txtDescricao.getText().trim();
            String condicao = comboStatus.getSelectedItem().toString();

            // -----------------------------
            // 2. VALIDAR CAMPOS OBRIGATÓRIOS
            // -----------------------------
            if (nomeProjeto.isEmpty() || empresaStr.isEmpty() || supervisorStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Preencha: Projeto, Empresa e Supervisor.",
                        "Campos obrigatórios",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (inicioStr.contains("") || prazoStr.contains("")) {
                JOptionPane.showMessageDialog(this,
                        "Preencha as datas corretamente.",
                        "Datas inválidas",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // -----------------------------
            // 3. CONVERTER CAMPOS NUMÉRICOS
            // -----------------------------
            int empresa, supervisor;

            try {
                empresa = Integer.parseInt(empresaStr);
                supervisor = Integer.parseInt(supervisorStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Empresa e Supervisor devem ser números inteiros válidos (ID).",
                        "Erro de conversão",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // -----------------------------
            // 4. CONVERTER DATAS
            // -----------------------------
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inicio, prazo;

            try {
                inicio = LocalDate.parse(inicioStr, formato);
                prazo = LocalDate.parse(prazoStr, formato);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "As datas devem estar no formato DD/MM/AAAA.",
                        "Data inválida",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // -----------------------------
            // 5. CRIAR OBJETO PROJETO
            // -----------------------------
            Projetos projeto = new Projetos();
            projeto.setNome(nomeProjeto);
            projeto.setFk_empresa(empresa);
            projeto.setFk_supervisor(supervisor);
            projeto.setInicio(inicio);
            projeto.setPrazo(prazo);
            projeto.setDescricao(descricao);
            projeto.setCondicao(condicao);

            // IMPORTANTE:
            // Seu DAO já deve estar usando o banco correto:
            // USE soldadores;
            ProjetosDAO dao = new ProjetosDAO();

            // -----------------------------
            // 6. INSERIR NO BANCO
            // -----------------------------
            dao.inserir(projeto);

            JOptionPane.showMessageDialog(this,
                    "Projeto cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (tela_projetos != null) {
                tela_projetos.carregarTabela();
            }

            this.dispose(); // fecha a tela

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro inesperado:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_botaoCadastroMouseClicked

    private void botaoAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoAtualizarMouseClicked
        this.dispose();
    }//GEN-LAST:event_botaoAtualizarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoAtualizar;
    private javax.swing.JLabel botaoCadastro;
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
