package tela_relatorios;

import dao.RelatoriosDAO;
import java.awt.Color;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import models.Relatorios;
import util.Fonte;

/**
 *
 * @author Rafael Moreira
 */



public class TelaRelatorios extends javax.swing.JPanel {

    private static TelaRelatorios instancia;
    // Criando uma instância dessa tela para poder utilizar a barra de pesquisa.
    public TelaRelatorios() {
        initComponents();
        instancia = this;
        
        // 🔹 Iniciar backup automático ao abrir esta tela
        util.BackupAutomatico.iniciarBackupAutomatico();
        
        // ============= PERSONALIZAÇÃO =============
        // Centraliza os dados
        DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
        centralizar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizar);
        }

        // Cabeçalho
        tabela.getTableHeader().setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        tabela.getTableHeader().setBackground(new Color(30, 58, 138));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().setResizingAllowed(false);

        // Altura, largura e cor das tabelas -> GERAL
        tabela.setBackground(Color.WHITE);
        tabela.setRowHeight(60);      
        
    } 
    
    // Método para utilizar a instância da tela na barra de pesquisa.
    public static TelaRelatorios getInstancia() {
        if (instancia == null) {
            instancia = new TelaRelatorios();
        }
        return instancia;
    }

    // Método de filtragem da tabela.
    // Pesquisa geral.
    public void filtrar(String texto) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto)); // Busca em todas as colunas
        }   
    }
    public void carregarTabela() {
    RelatoriosDAO dao = new RelatoriosDAO();
    List<Relatorios> lista = dao.listarRelatoriosParaTela();
    DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

    // Limpa a tabela antes de preencher novamente
    modelo.setRowCount(0);

    for (Relatorios r : lista) {
        modelo.addRow(new Object[]{
            r.getNome(),
            r.getDescricao(),
            r.getCaminho(),
            r.isCondicao() ? "Automático" : "Manual",
            r.getCriadoEm() != null ? new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                    .format(r.getCriadoEm()) : ""
        });
    }
}
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        novoRelatorio = new javax.swing.JLabel();
        botaoBackup = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(228, 228, 228));
        setMaximumSize(new java.awt.Dimension(1810, 1014));
        setMinimumSize(new java.awt.Dimension(1810, 1014));
        setPreferredSize(new java.awt.Dimension(1810, 1014));
        setLayout(null);

        jScrollPane1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        tabela.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 15f));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Descrição", "Caminho", "Backup", "Hora"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabela);

        add(jScrollPane1);
        jScrollPane1.setBounds(40, 130, 1770, 870);

        novoRelatorio.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 25f));
        novoRelatorio.setForeground(new java.awt.Color(255, 255, 255));
        novoRelatorio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        novoRelatorio.setText("+ NOVO RELATÓRIO");
        novoRelatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                novoRelatorioMouseClicked(evt);
            }
        });
        add(novoRelatorio);
        novoRelatorio.setBounds(1530, 20, 260, 90);

        botaoBackup.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 25f));
        botaoBackup.setForeground(new java.awt.Color(255, 255, 255));
        botaoBackup.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoBackup.setText("BACKUP");
        botaoBackup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoBackupMouseClicked(evt);
            }
        });
        add(botaoBackup);
        botaoBackup.setBounds(1240, 20, 260, 90);
        botaoBackup.getAccessibleContext().setAccessibleName("");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(1240, 20, 260, 90);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(1530, 20, 260, 90);
    }// </editor-fold>//GEN-END:initComponents

    private void novoRelatorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_novoRelatorioMouseClicked
        CadastroRelatorio cadastro = new CadastroRelatorio();
        cadastro.setVisible(true);
    }//GEN-LAST:event_novoRelatorioMouseClicked

    private void botaoBackupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoBackupMouseClicked
        // TODO add your handling code here:
        util.BackupUtil.gerarBackup();
    }//GEN-LAST:event_botaoBackupMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoBackup;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel novoRelatorio;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}