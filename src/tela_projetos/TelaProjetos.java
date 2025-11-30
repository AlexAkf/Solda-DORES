package tela_projetos;

import dao.ProjetosDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import models.Projetos;
import util.Fonte;
import util.TabelaAcaoEditor;
import util.TabelaAcaoEvento;
import util.TabelaAcaoRender;

/**
 *
 * @author Rafael Silva
 */
public final class TelaProjetos extends javax.swing.JPanel {

    private static TelaProjetos instancia;
    private TabelaAcaoEvento evento;   // Guardar evento para reutilizar após recarregar

    // Criando uma instância dessa tela para poder utilizar a barra de pesquisa.
    public TelaProjetos() throws SQLException {
        initComponents();
        iniciarEvento();    // Cria o evento apenas uma vez
        instancia = this;
        carregarTabela();

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
        
        // Ocultando a coluna de ID 
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Altura, largura e cor das tabelas -> GERAL
        tabela.setBackground(Color.WHITE);
        tabela.setRowHeight(60);

        renderizar();
    }

    // ========== BOTÕES DE AÇÃO DA TABELA ==========
    // Cria o evento de ação dos botões apenas uma vez para evitar o bug */
    private void iniciarEvento() {
        evento = new TabelaAcaoEvento() {
            @Override
            public void editando(int linha) {
                // Converte o índice da view para model
                int model = tabela.convertRowIndexToModel(linha);

                int id = (int) tabela.getModel().getValueAt(model, 0);
                String nome = (String) tabela.getModel().getValueAt(model, 1);
                int fk_empresa = (int) tabela.getModel().getValueAt(model, 2);
                int fk_supervisor = (int) tabela.getModel().getValueAt(model, 3);
                
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
                String inicioStr = tabela.getModel().getValueAt(model, 4).toString();
                String prazoStr = tabela.getModel().getValueAt(model, 5).toString();
                
                LocalDate inicio = LocalDate.parse(inicioStr, formato);
                LocalDate prazo = LocalDate.parse(prazoStr, formato);
                
                String descricao = (String) tabela.getModel().getValueAt(model, 6);
                String condicao = (String) tabela.getModel().getValueAt(model, 7);

                Projetos projeto = new Projetos();
                projeto.setId(id);
                projeto.setNome(nome);
                projeto.setFk_empresa(fk_empresa);
                projeto.setFk_supervisor(fk_supervisor);
                projeto.setInicio(inicio);
                projeto.setPrazo(prazo);
                projeto.setDescricao(descricao);
                projeto.setCondicao(condicao);

                var atualizar = new AtualizarProjetos(TelaProjetos.this);
                atualizar.preencherCampos(projeto);
                atualizar.setVisible(true);
            }

            @Override
            public void excluindo(int linha) {
                try {
                    ProjetosDAO dao = new ProjetosDAO();

                    int id = (int) tabela.getValueAt(linha, 0);
                    dao.deletar(id);
                    carregarTabela();
                    JOptionPane.showMessageDialog(null, "Projeto deletado com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar: " + ex.getMessage());
                }
            }
        };
    }

    private void renderizar() {
        // Faz o famoso desliga e liga pra recarregar os botões da coluna Ações
        tabela.getColumnModel().getColumn(8).setCellRenderer(new TabelaAcaoRender());
        tabela.getColumnModel().getColumn(8).setCellEditor(new TabelaAcaoEditor(evento));

        // Também ajudam no processo de recarregar
        tabela.revalidate();
        tabela.repaint();
    }

    // Método para utilizar a instância da tela na barra de pesquisa.
    public static TelaProjetos getInstancia() throws SQLException {
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

    public void carregarTabela() throws SQLException {

        ProjetosDAO dao = new ProjetosDAO();
        List<Projetos> lista = dao.listarTodos();

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Projetos projeto : lista) {
            String dataInicio = projeto.getinicio() != null
                    ? projeto.getinicio().format(formato)
                    : "";
            String dataPrazo = projeto.getprazo() != null
                    ? projeto.getprazo().format(formato)
                    : "";

            modelo.addRow(new Object[]{
                projeto.getid(),
                projeto.getnome(),
                projeto.getfk_empresa(),
                projeto.getfk_supervisor(),
                dataInicio,
                dataPrazo,
                projeto.getdescricao(),
                projeto.getcondicao(),
                null
            });
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
                "ID", "Projeto", "Empresa", "Supervisor", "Data Inicial", "Prazo Final", "Descrição", "Condição", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
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
        CadastroProjetos cadastro = new CadastroProjetos(this);
        cadastro.setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
