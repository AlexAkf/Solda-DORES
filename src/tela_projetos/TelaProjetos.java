package tela_projetos;

import java.awt.Color;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import util.Fonte;

/**
 *
 * @author Rafael Silva
 */

public class TelaProjetos extends javax.swing.JPanel {

    private static TelaProjetos instancia;
    // Criando uma instância dessa tela para poder utilizar a barra de pesquisa.
    public TelaProjetos() {
        initComponents();
        instancia = this;
        
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
    public static TelaProjetos getInstancia() {
        if (instancia == null) {
            instancia = new TelaProjetos();
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
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setBackground(new java.awt.Color(228, 228, 228));
        setMaximumSize(new java.awt.Dimension(1810, 1014));
        setMinimumSize(new java.awt.Dimension(1810, 1014));
        setPreferredSize(new java.awt.Dimension(1810, 1014));
        setLayout(null);

        jLabel3.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 25f));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("+ NOVO CADASTRO");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        add(jLabel3);
        jLabel3.setBounds(1530, 20, 260, 90);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(1530, 20, 260, 90);

        jScrollPane1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        tabela.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 15f));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Projeto", "Empresa", "Supervisor", "Data Inicial", "Prazo Final", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabela);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 130, 1770, 870);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        CadastroProjetos cadastro = new CadastroProjetos();
        cadastro.setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}