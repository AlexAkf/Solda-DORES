package tela_projetos;

import dao.ProjetosDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
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
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane1.setOpaque(true);
        jScrollPane1.getViewport().setBackground(new Color(228, 228, 228));
        tabela.setGridColor(new Color(30, 58, 138));
        tabela.setBorder(BorderFactory.createLineBorder(new Color(30, 58, 138), 1, true));
        tabela.getTableHeader().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(30, 58, 138)));
        
        renderizar();
    }

    // ========== BOTÕES DE AÇÃO DA TABELA ==========
    // Cria o evento de ação dos botões apenas uma vez para evitar o bug */
    private void iniciarEvento() {
        evento = new TabelaAcaoEvento() {
            @Override
            public void editando(int linha) {
                // Converte para índice do modelo
                int model = tabela.convertRowIndexToModel(linha);

                // Lê a condição
                String condicao = String.valueOf(tabela.getModel().getValueAt(model, 7));

                // Bloqueio de edição
                if (condicao.equalsIgnoreCase("Finalizado")
                        || condicao.equalsIgnoreCase("Cancelado")) {

                    JOptionPane.showMessageDialog(null,
                            "Projetos FINALIZADOS ou CANCELADOS não podem ser editados.",
                            "Edição Bloqueada",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    // --- Leitura dos campos da tabela ---
                    int id = (int) tabela.getModel().getValueAt(model, 0);
                    String nome = String.valueOf(tabela.getModel().getValueAt(model, 1));
                    String empresa = String.valueOf(tabela.getModel().getValueAt(model, 2));
                    String supervisor = String.valueOf(tabela.getModel().getValueAt(model, 3));
                    String inicioStr = String.valueOf(tabela.getModel().getValueAt(model, 4));
                    String prazoStr = String.valueOf(tabela.getModel().getValueAt(model, 5));
                    String descricao = String.valueOf(tabela.getModel().getValueAt(model, 6));

                    // --- Conversão de datas ---
                    LocalDate inicio = null;
                    LocalDate prazo = null;

                    if (inicioStr != null && !inicioStr.isBlank()) {
                        inicio = LocalDate.parse(inicioStr, formato);
                    }

                    if (prazoStr != null && !prazoStr.isBlank()) {
                        prazo = LocalDate.parse(prazoStr, formato);
                    }

                    // --- Monta o objeto Projeto ---
                    Projetos projeto = new Projetos();
                    projeto.setId(id);
                    projeto.setNome(nome);
                    projeto.setEmpresa(empresa);
                    projeto.setSupervisor(supervisor);
                    projeto.setInicio(inicio);
                    projeto.setPrazo(prazo);
                    projeto.setDescricao(descricao);
                    projeto.setCondicao(condicao);

                    // --- Abre tela de edição ---
                    FichaEdicao editar = new FichaEdicao(TelaProjetos.this);
                    editar.preencherCampos(projeto);
                    editar.setVisible(true);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Erro ao carregar dados do projeto para edição:\n" + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void excluindo(int linha) {
                // Nenhuma linha selecionada
                if (linha < 0) {
                    JOptionPane.showMessageDialog(null,
                            "Selecione um projeto para deletar.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // Converte linha exibida para linha do modelo (importante com filtros)
                    int model = tabela.convertRowIndexToModel(linha);

                    // Lê ID do projeto
                    int id = (int) tabela.getModel().getValueAt(model, 0);
                    String nome = String.valueOf(tabela.getModel().getValueAt(model, 1));

                    // Confirmação antes de excluir
                    int opc = JOptionPane.showConfirmDialog(null,
                            "Tem certeza que deseja deletar o projeto:\n\"" + nome + "\"?",
                            "Confirmar exclusão",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if (opc != JOptionPane.YES_OPTION) {
                        return; // Usuário cancelou
                    }

                    // Executa remoção
                    ProjetosDAO dao = new ProjetosDAO();
                    boolean ok = dao.deletar(id);

                    if (ok) {
                        carregarTabela();
                        JOptionPane.showMessageDialog(null,
                                "Projeto deletado com sucesso!",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Erro inesperado:\n" + e.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
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

    public void carregarTabela() {
        ProjetosDAO dao = new ProjetosDAO();
        List<Projetos> lista = dao.listar();

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Projetos p : lista) {
            String dataInicio = (p.getInicio() != null)
                    ? p.getInicio().format(formato)
                    : "";

            String dataPrazo = (p.getPrazo() != null)
                    ? p.getPrazo().format(formato)
                    : "";

            modelo.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getEmpresa(),
                p.getSupervisor(),
                dataInicio,
                dataPrazo,
                p.getDescricao(),
                p.getCondicao(),
                null
            });
        }
        tabela.clearSelection();
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
