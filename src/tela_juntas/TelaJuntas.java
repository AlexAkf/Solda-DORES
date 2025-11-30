package tela_juntas;

import dao.JuntasDAO;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import models.Juntas;
import util.Fonte;
import util.StatusRenderer;
import util.TabelaAcaoEditor;
import util.TabelaAcaoEvento;
import util.TabelaAcaoRender;

/**
 *
 * @author hugos
 */
public class TelaJuntas extends javax.swing.JPanel {

    private static TelaJuntas instancia;
    private TabelaAcaoEvento evento;   // Guardar evento para reutilizar após recarregar

    public TelaJuntas() {
        initComponents();
        iniciarEvento();    // Cria o evento apenas uma vez
        instancia = this;
        carregarTabela();

        // ============= PERSONALIZAÇÃO =============
        // Centralizar dados:
        DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
        centralizar.setHorizontalAlignment(SwingConstants.CENTER);
        // Centraliza todas as colunas, exceto a coluna Status (índice 4)
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            if (i != 4) {  // pula a coluna do Status
                tabela.getColumnModel().getColumn(i).setCellRenderer(centralizar);
            }
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

        // Deixar dados de status visualmente agradável.
        tabela.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());

        renderizar();   // Recarrega os botões
    }

    // ========== BOTÕES DE AÇÃO DA TABELA ==========
    // Cria o evento de ação dos botões apenas uma vez para evitar o bug.
    private void iniciarEvento() {
        evento = new TabelaAcaoEvento() {
            @Override
            public void editando(int linha) {
                // Converte o índice da view para o índice real do model
                int model = tabela.convertRowIndexToModel(linha);

                // Pega os dados da linha selecionada
                int id = (int) tabela.getModel().getValueAt(model, 0);
                String projeto = (String) tabela.getModel().getValueAt(model, 1);
                String codigo = (String) tabela.getModel().getValueAt(model, 2);
                double comprimento = (double) tabela.getModel().getValueAt(model, 3);
                String status = (String) tabela.getModel().getValueAt(model, 4);

                // Cria objeto Juntas e preenche
                var j = new Juntas();
                j.setId(id);
                j.setProjeto(projeto);
                j.setCodigo(codigo);
                j.setComprimento(comprimento);
                j.setStatus(status);

                // Abre a tela Atualizar
                var atualizar = new AtualizarJuntas(TelaJuntas.this);
                atualizar.preencherCampos(j);
                atualizar.setVisible(true);
            }

            @Override
            public void excluindo(int linha) {
                int model = tabela.convertRowIndexToModel(linha);

                int idJunta = (int) tabela.getModel().getValueAt(model, 0);

                int opcao = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente excluir a junta ID " + idJunta + "?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {

                    JuntasDAO dao = new JuntasDAO();
                    boolean apagou = dao.deletarJunta(idJunta);

                    if (apagou) {
                        JOptionPane.showMessageDialog(null,
                                "Junta excluída com sucesso!",
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao excluir a junta.",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    carregarTabela();
                    renderizar();
                }
            }
        };
    }

    private void renderizar() {
        // Faz o famoso desliga e liga pra recarregar os botões da coluna Ações
        tabela.getColumnModel().getColumn(5).setCellRenderer(new TabelaAcaoRender());
        tabela.getColumnModel().getColumn(5).setCellEditor(new TabelaAcaoEditor(evento));

        // Também ajudam no processo de recarregar
        tabela.revalidate();
        tabela.repaint();
    }

    public void carregarTabela() {
        JuntasDAO dao = new JuntasDAO();
        List<Juntas> lista = dao.listarJuntas();

        // Caso algum botão tenha sido clicado, encerra edição
        if (tabela.isEditing() && tabela.getCellEditor() != null) {
            tabela.getCellEditor().stopCellEditing();
        }

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0); // Limpa a tabela.

        for (Juntas junta : lista) {
            modelo.addRow(new Object[]{
                junta.getId(),
                junta.getProjeto(),
                junta.getCodigo(),
                junta.getComprimento(),
                junta.getStatus(),});
        }
        tabela.clearSelection();
    }

    // Método para utilizar a instância da tela na barra de pesquisa.
    public static TelaJuntas getInstancia() {
        if (instancia == null) {
            instancia = new TelaJuntas();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        botaoCadastro = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(228, 228, 228));
        setMaximumSize(new java.awt.Dimension(1810, 1014));
        setMinimumSize(new java.awt.Dimension(1810, 1014));
        setLayout(null);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 15f));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Interno", "Projeto", "Código da Junta", "Comprimento (mm)", "Status", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
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
        tabela.setGridColor(new java.awt.Color(30, 58, 138));
        jScrollPane1.setViewportView(tabela);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 130, 1770, 870);

        botaoCadastro.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 25f));
        botaoCadastro.setForeground(new java.awt.Color(255, 255, 255));
        botaoCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoCadastro.setText("+ NOVO CADASTRO");
        botaoCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoCadastroMouseClicked(evt);
            }
        });
        add(botaoCadastro);
        botaoCadastro.setBounds(1530, 20, 260, 90);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(1530, 20, 260, 90);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCadastroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoCadastroMouseClicked
        CadastroJuntas cadastro = new CadastroJuntas(this);
        cadastro.setVisible(true);
    }//GEN-LAST:event_botaoCadastroMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoCadastro;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
