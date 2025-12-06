package tela_relatorios;

import com.itextpdf.text.Paragraph;
import dao.EquipamentosDAO;
import dao.RelatoriosDAO;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import models.Equipamentos;
import models.Relatorios;
import util.Fonte;
import java.sql.SQLException;

/**
 *
 * @author Rafael Moreira
 */

public class FichaRelatorio extends javax.swing.JFrame {

    public FichaRelatorio() {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        jComboBox1.setLightWeightPopupEnabled(false);
        descricao.setLineWrap(true);          // Quebra de linha automática
        descricao.setWrapStyleWord(true);     // Quebra apenas entre palavras
        descricao.setRows(5);                 // Altura inicial (em linhas)
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descricao = new javax.swing.JTextArea();
        jTextField5 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel7.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setMaximumSize(new java.awt.Dimension(260, 83));
        jLabel7.setMinimumSize(new java.awt.Dimension(260, 83));
        jLabel7.setPreferredSize(new java.awt.Dimension(260, 83));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7);
        jLabel7.setBounds(764, 790, 390, 70);

        descricao.setColumns(20);
        descricao.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 12f));
        descricao.setLineWrap(true);
        descricao.setRows(5);
        jScrollPane2.setViewportView(descricao);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(730, 610, 450, 110);

        jTextField5.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(jTextField5);
        jTextField5.setBounds(730, 490, 350, 50);

        jComboBox1.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 13f));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Projeto", "Estoque", "Funcionário", "Empresa" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(1080, 490, 100, 50);

        jTextField4.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(jTextField4);
        jTextField4.setBounds(730, 380, 450, 50);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3);
        jLabel3.setBounds(1207, 220, 50, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaRelatorios.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1920, 1080);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        try {

            // ------------- PEGAR DADOS DA TELA -------------
            String nomeclatura = jTextField4.getText().trim();
            String tipo = (String) jComboBox1.getSelectedItem();
            String descricaoTexto = descricao.getText().trim();

            if (nomeclatura.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite a nomeclatura!");
                return;
            }

            // ------------- ESCOLHER ONDE SALVAR PDF -------------
            JFileChooser fc = new JFileChooser();

            // Abre na Área de Trabalho
            fc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));

            fc.setDialogTitle("Salvar Relatório PDF");
            fc.setSelectedFile(new java.io.File("relatorio.pdf"));

            if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            String caminho = fc.getSelectedFile().getAbsolutePath();

            // FORÇAR EXTENSÃO .pdf
            if (!caminho.toLowerCase().endsWith(".pdf")) {
                caminho += ".pdf";
            }

            // ------------- CRIAR PDF COM iTEXT -------------
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(doc, new java.io.FileOutputStream(caminho));
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataHoraAtual = sdf.format(new java.util.Date());

            doc.open(); // ABRIR O DOCUMENTO

            // Elementos do Cabeçalho
            doc.add(new com.itextpdf.text.Paragraph("RELATÓRIO - " + tipo));
            doc.add(new com.itextpdf.text.Paragraph("Nomeclatura: " + nomeclatura));
            doc.add(new com.itextpdf.text.Paragraph("Gerado em: " + dataHoraAtual));

            // ADICIONA A DESCRIÇÃO IMEDIATAMENTE APÓS O CABEÇALHO, SEM ESPAÇO ANTES
            if (!descricaoTexto.isEmpty()) {
                doc.add(new com.itextpdf.text.Paragraph("Descrição: " + descricaoTexto));
            }

            // O ESPAÇAMENTO DEVE VIR APENAS NO FINAL DO CABEÇALHO
            doc.add(new com.itextpdf.text.Paragraph(" "));

            // ------------- PEGAR DADOS DO BANCO POR TIPO -------------
            switch (tipo) {

                case "Estoque":
                try {
                    EquipamentosDAO eqDao = new EquipamentosDAO();
                    List<Equipamentos> listaEq = eqDao.listarEquipamentos();

                    if (listaEq == null || listaEq.isEmpty()) {
                        doc.add(new Paragraph("NENHUM EQUIPAMENTO ENCONTRADO NO ESTOQUE."));
                        break;
                    }

                    doc.add(new Paragraph("LISTA DE EQUIPAMENTOS:"));
                    doc.add(new Paragraph("-------------------------------------------"));

                    for (Equipamentos e : listaEq) {

                        String linha =
                        " | Código: " + safe(e.getCodigo()) +
                        " | Modelo: " + safe(e.getModelo()) +
                        " | Marca: " + safe(e.getMarca()) +
                        " | Condição (Local/Uso): " + safe(e.getCondicao()) +
                        " | Situação (Adm): " + safe(e.getStatus()) +
                        " | Soldador: " + safe(e.getSoldador());

                        doc.add(new Paragraph(linha));
                        doc.add(new Paragraph("-"));
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        "Erro ao gerar PDF:\n" + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
                break;

                case "Projeto":
                try {
                    dao.ProjetosDAO pDao = new dao.ProjetosDAO();
                    List<models.Projetos> listaP = pDao.listar();

                    doc.add(new com.itextpdf.text.Paragraph("LISTA DE PROJETOS:"));
                    doc.add(new com.itextpdf.text.Paragraph("-------------------------------------------"));

                    // Itera sobre a lista de projetos e adiciona cada um como um Parágrafo
                    for (models.Projetos p : listaP) {

                        // Concatenação de todos os campos em uma única string
                        String linhaProjeto =
                        "Projeto: " + p.getNome() +
                        " | Empresa ID: " + p.getEmpresa() +
                        " | Condição: " + p.getCondicao() +
                        " | Início: " + p.getInicio() +
                        " | Prazo: " + p.getPrazo();

                        doc.add(new com.itextpdf.text.Paragraph(linhaProjeto));

                        // Adiciona um separador simples ou um espaço para melhor leitura
                        doc.add(new com.itextpdf.text.Paragraph("-------------------------------------------"));
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar Projetos no banco de dados.\nErro: " + e.getMessage());
                    throw e; // Lança a exceção para ser capturada pelo bloco catch principal
                }
                break;

                case "Funcionário":
                try {
                    //  Instanciar a DAO de Usuários/Funcionários
                    dao.UsuariosDAO uDao = new dao.UsuariosDAO();
                    //  Chamar o método listar() que retorna todos os usuários ativos
                    List<models.Usuarios> listaF = uDao.listar();

                    doc.add(new com.itextpdf.text.Paragraph("LISTA DE FUNCIONÁRIOS:"));
                    doc.add(new com.itextpdf.text.Paragraph("-------------------------------------------"));

                    //  Iterar sobre a lista e adicionar cada funcionário ao PDF
                    for (models.Usuarios f : listaF) {

                        // Formata a informação do supervisor, se existir
                        String supervisorInfo = (f.getSupervisor() != null && f.getSupervisor().getNome() != null)
                        ? " | Supervisor: " + f.getSupervisor().getNome()
                        : "";

                        doc.add(new com.itextpdf.text.Paragraph(
                            "Funcionário: " + f.getNome() +
                            " | CPF: " + f.getCpf() +
                            " | Cargo: " + f.getCargo() +
                            " | Perfil: " + f.getPerfil() +
                            supervisorInfo
                            // Você pode adicionar mais campos aqui (Sinete, Validade, etc.)
                        ));
                    }
                } catch (SQLException e) {
                    // Em caso de erro na conexão ou consulta
                    JOptionPane.showMessageDialog(null, "Erro ao buscar funcionários.\nErro: " + e.getMessage());
                }
                break;

                case "Empresa":

                dao.EmpresasDAO emDao = new dao.EmpresasDAO();
                List<models.Empresas> listaE = emDao.listarTodasempresas();

                doc.add(new com.itextpdf.text.Paragraph("LISTA DE EMPRESAS:"));
                doc.add(new com.itextpdf.text.Paragraph("-------------------------------------------"));

                for (models.Empresas em : listaE) {
                    doc.add(new com.itextpdf.text.Paragraph(
                        "Empresa: " + em.getNome() +
                        " | CNPJ: " + em.getCnpj()
                    ));
                }
                break;

            }

            doc.close();

            // SALVAR NO BANCO
            Relatorios rel = new Relatorios();
            rel.setFkGestor(1);
            rel.setNome("Relatório - " + nomeclatura);
            rel.setDescricao("Relatório automático: " + tipo);
            rel.setCaminho(caminho);
            rel.setCondicao(true);

            RelatoriosDAO rdao = new RelatoriosDAO();
            rdao.inserirRelatorio(rel);

            JOptionPane.showMessageDialog(this, "PDF gerado com sucesso!");
            TelaRelatorios.getInstancia().carregarTabela();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String selecionado = (String) jComboBox1.getSelectedItem();
        jTextField5.setText(selecionado);

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private String safe(String s) {
        return (s == null || s.isBlank()) ? "—" : s;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descricao;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
