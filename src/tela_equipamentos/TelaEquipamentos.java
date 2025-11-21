package tela_equipamentos;

import dao.EquipamentosDAO;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import models.Equipamentos;
import util.Fonte;
import util.TabelaAcaoEditor;
import util.TabelaAcaoEvento;
import util.TabelaAcaoRender;

/**
 * Tela do CRUD de EQUIPAMENTOS
 * 
 * @author Hugo
 */
public final class TelaEquipamentos extends javax.swing.JPanel {

    private static TelaEquipamentos instancia;

    // Criando uma instância dessa tela para poder utilizar a barra de pesquisa.
    public TelaEquipamentos() {
        initComponents();
        carregarTabela();

        instancia = this;

        // ============= PERSONALIZAÇÃO =============
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

        // ========== BOTÕES DE AÇÃO DA TABELA ==========
        TabelaAcaoEvento evento = new TabelaAcaoEvento() {
            @Override
            public void editando(int linha) {
                // Pega os dados da linha selecionada
                int id = (int) tabela.getValueAt(linha, 0);
                String codigo = (String) tabela.getValueAt(linha, 1);
                String modelo = (String) tabela.getValueAt(linha, 2);
                String marca = (String) tabela.getValueAt(linha, 3);
                String soldador = (String) tabela.getValueAt(linha, 4);
                String condicao = (String) tabela.getValueAt(linha, 5);

                Equipamentos eq = new Equipamentos();
                eq.setId(id);
                eq.setCodigo(codigo);
                eq.setModelo(modelo);
                eq.setMarca(marca);
                eq.setSoldador(soldador);
                eq.setCondicao(condicao.toLowerCase());

                AtualizarEquipamentos atualizar = new AtualizarEquipamentos(TelaEquipamentos.this);
                atualizar.setIdEquipamentoSelecionado(id);
                atualizar.preencherCampos(eq);
                atualizar.setVisible(true);
            }

            @Override
            public void excluindo(int linha) {
                int idEquipamento = (int) tabela.getValueAt(linha, 0);

                int opcao = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente excluir o equipamento ID " + idEquipamento + "?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    EquipamentosDAO dao = new EquipamentosDAO();
                    dao.excluirEquipamento(idEquipamento);
                    carregarTabela(); // atualiza a tabela após exclusão
                }
            }
        };
        tabela.getColumnModel().getColumn(6).setCellRenderer(new TabelaAcaoRender());
        tabela.getColumnModel().getColumn(6).setCellEditor(new TabelaAcaoEditor(evento));
    }

    public void carregarTabela() {
        EquipamentosDAO dao = new EquipamentosDAO();
        List<Equipamentos> lista = dao.listarTodos();

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0); // limpa a tabela

        for (Equipamentos eq : lista) {
            modelo.addRow(new Object[]{
                eq.getId(),
                eq.getCodigo(),
                eq.getModelo(),
                eq.getMarca(),
                eq.getSoldador(),
                eq.getCondicao()
            });
        }
    }

    // Método para utilizar a instância da tela na barra de pesquisa.
    public static TelaEquipamentos getInstancia() {
        if (instancia == null) {
            instancia = new TelaEquipamentos();
        }
        return instancia;
    }

    // Método de filtragem da tabela.
    // Pesquisa geral.
    public void filtrarEquipamentos(String texto) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto)); // Busca em todas as colunas
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
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

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 15f));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Modelo", "Marca", "Em posse do soldador", "Condição", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setGridColor(new java.awt.Color(30, 58, 138));
        jScrollPane1.setViewportView(tabela);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 130, 1770, 870);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel3MouseClicked
        CadastroEquipamentos cadastro = new CadastroEquipamentos(this);
        cadastro.setVisible(true);
    }// GEN-LAST:event_jLabel3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}