package tela_empresas;

import dao.EmpresasDAO;
import java.awt.Color;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import models.Empresas;
import util.Fonte;
import util.TabelaAcaoEditor;
import util.TabelaAcaoEvento;
import util.TabelaAcaoRender;
        
/**
 *
 * @author Rafhael Muzzi
 */

public class TelaEmpresas extends javax.swing.JPanel {

    private static TelaEmpresas instancia;
    
    // Criando uma instância dessa tela para poder utilizar a barra de pesquisa.
    public TelaEmpresas() {
        initComponents();
        carregarTabela();

        instancia = this;

        // ============= PERSONALIZAÇÃO =============
        //Ocultando a coluna de ID
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        // Centralizar dados:
        DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
        centralizar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizar);
        }

        // Cabeçalho:
        tabela.getTableHeader().setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        tabela.getTableHeader().setBackground(new Color(30, 58, 138));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().setResizingAllowed(false);

        // Altura, largura e cor:
        tabela.setBackground(Color.WHITE);
        tabela.setRowHeight(60);
        tabela.getColumnModel().getColumn(0).setMaxWidth(50);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane1.setOpaque(true);
        jScrollPane1.getViewport().setBackground(new Color(228, 228, 228));
        tabela.setGridColor(new Color(30, 58, 138));
        tabela.setBorder(BorderFactory.createLineBorder(new Color(30, 58, 138), 1, true));
        tabela.getTableHeader().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(30, 58, 138)));

        // ========== BOTÕES DE AÇÃO DA TABELA ==========
        TabelaAcaoEvento evento = new TabelaAcaoEvento() {
            @Override
            public void editando(int linha) {
                // Pega os dados da linha selecionada
                int id = (int) tabela.getValueAt(linha, 0);
                String empresa = (String) tabela.getValueAt(linha, 1);
                String cnpj = (String) tabela.getValueAt(linha, 2);
                String telefone = (String) tabela.getValueAt(linha, 3);
                String email = (String) tabela.getValueAt(linha, 4);
                

                Empresas emp = new Empresas();
                emp.setId(id);
                emp.setNome(empresa);
                emp.setCnpj(cnpj);
                emp.setTelefone(telefone);
                emp.setEmail(email);
                
                FichaEdicao atualizar = new FichaEdicao(TelaEmpresas.this);
                atualizar.setIdEquipamentoSelecionado(id);
                atualizar.preencherCampos(emp);
                atualizar.setVisible(true);
                carregarTabela();
            }

            @Override
            public void excluindo(int linha) {
                int idEmpresa = (int) tabela.getValueAt(linha, 0);

                int opcao = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente excluir a empresa ID " + idEmpresa + "?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    EmpresasDAO dao = new EmpresasDAO();
                    dao.deletarempresa(idEmpresa);
                    carregarTabela(); // atualiza a tabela após exclusão
                }
            }
        };
        tabela.getColumnModel().getColumn(5).setCellRenderer(new TabelaAcaoRender());
        tabela.getColumnModel().getColumn(5).setCellEditor(new TabelaAcaoEditor(evento));
    }
    
    public void carregarTabela() {
        EmpresasDAO dao = new EmpresasDAO();
        List<Empresas> lista = dao.listarTodasempresas();

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0); // limpa a tabela

        for (Empresas emp : lista) {
            modelo.addRow(new Object[]{
                emp.getId(),
                emp.getNome(),
                emp.getCnpj(),
                emp.getTelefone(),
                emp.getEmail(),
                null
            });
        }
    }

    // Método para utilizar a instância da tela na barra de pesquisa.
    public static TelaEmpresas getInstancia() {
        if (instancia == null) {
            instancia = new TelaEmpresas();
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
                "ID", "Empresa", "CNPJ", "Celular", "E-mail", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
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
        FichaCadastro cadastro = new FichaCadastro(this);
        cadastro.setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}