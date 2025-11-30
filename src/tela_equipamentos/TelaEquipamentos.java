package tela_equipamentos;

import dao.EquipamentosDAO;
import java.awt.Color;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import models.Equipamentos;
import util.*;

/**
 * Tela do CRUD de EQUIPAMENTOS
 *
 * @author Hugo
 */

public final class TelaEquipamentos extends javax.swing.JPanel {

    private static TelaEquipamentos instancia;
    private TabelaAcaoEvento evento;   // Guardar evento para reutilizar após recarregar

    // Criando uma instância dessa tela para poder utilizar a barra de pesquisa.
    public TelaEquipamentos() {
        initComponents();
        iniciarEvento();    // Cria o evento apenas uma vez
        instancia = this;
        carregarTabela();

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

        renderizar();   // Recarrega os botões
    }

    // ========== BOTÕES DE AÇÃO DA TABELA ==========
    // Cria o evento de ação dos botões apenas uma vez para evitar o bug.
    private void iniciarEvento() {
        evento = new TabelaAcaoEvento() {
            @Override
            public void editando(int linha) {
                // Converte o índice da view para model
                int model = tabela.convertRowIndexToModel(linha);

                // Pega a Condição (Ativo/Inativo) - Coluna 6
                String status = (String) tabela.getModel().getValueAt(model, 6);
                
                // BLOQUEIO: Não pode editar se estiver Inativo
                if ("Inativo".equalsIgnoreCase(status)) {
                    JOptionPane.showMessageDialog(null, 
                            "Não é possível editar equipamentos inativos.", 
                            "Bloqueio", JOptionPane.WARNING_MESSAGE);
                    return; // Sai do método
                }
                
                // Pega os dados da linha selecionada
                int id = (int) tabela.getModel().getValueAt(model, 0);
                String codigo = (String) tabela.getModel().getValueAt(model, 1);
                String modelo = (String) tabela.getModel().getValueAt(model, 2);
                String marca = (String) tabela.getModel().getValueAt(model, 3);
                String condicao = (String) tabela.getModel().getValueAt(model, 5);

                var equipamento = new Equipamentos();
                equipamento.setId(id);
                equipamento.setCodigo(codigo);
                equipamento.setModelo(modelo);
                equipamento.setMarca(marca);
                equipamento.setStatus(condicao);

                var atualizar = new AtualizarEquipamentos(TelaEquipamentos.this);
                atualizar.preencherCampos(equipamento);
                atualizar.setIdEquipamentoSelecionado(id);
                atualizar.setVisible(true);
            }

            @Override
            public void excluindo(int linha) {
                int model = tabela.convertRowIndexToModel(linha);
                int idEquipamento = (int) tabela.getModel().getValueAt(model, 0);
                
                // Pega a Condição (Ativo/Inativo) - Coluna 6
                String condicao = (String) tabela.getModel().getValueAt(model, 6);

                // BLOQUEIO: Não pode excluir/inativar se já estiver Inativo
                if ("Inativo".equalsIgnoreCase(condicao)) {
                    JOptionPane.showMessageDialog(null, 
                            "O equipamento já está inativo.", 
                            "Bloqueio", JOptionPane.WARNING_MESSAGE);
                    return; // Sai do método
                }

                int opcao = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente inativar o equipamento ID " + idEquipamento + "?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    EquipamentosDAO dao = new EquipamentosDAO();
                    dao.inativarEquipamento(idEquipamento);
                    carregarTabela();   // Atualiza a tabela após exclusão
                    renderizar();   // Reaplica os botões de ação
                }
            }
        };
    }

    private void renderizar() {
        // Faz o famoso desliga e liga pra recarregar os botões da coluna Ações
        tabela.getColumnModel().getColumn(7).setCellRenderer(new TabelaAcaoRender());
        tabela.getColumnModel().getColumn(7).setCellEditor(new TabelaAcaoEditor(evento));

        // Também ajudam no processo de recarregar
        tabela.revalidate();
        tabela.repaint();
    }

    public void carregarTabela() {
        EquipamentosDAO dao = new EquipamentosDAO();
        List<Equipamentos> lista = dao.listarEquipamentos();

        // Caso algum botão tenha sido clicado, encerra edição
        if (tabela.isEditing() && tabela.getCellEditor() != null) {
            tabela.getCellEditor().stopCellEditing();
        }

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0); // limpa a tabela

        for (Equipamentos equipamento : lista) {
            modelo.addRow(new Object[]{
                equipamento.getId(),
                equipamento.getCodigo(),
                equipamento.getModelo(),
                equipamento.getMarca(),
                equipamento.getSoldador(),
                equipamento.getStatus(),
                equipamento.getCondicao()
            });
        }
        tabela.clearSelection();
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

        botaoCadastro = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(228, 228, 228));
        setMaximumSize(new java.awt.Dimension(1810, 1014));
        setMinimumSize(new java.awt.Dimension(1810, 1014));
        setPreferredSize(new java.awt.Dimension(1810, 1014));
        setLayout(null);

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

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 15f));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Interno", "Código de Série", "Equipamento", "Marca", "Em posse do soldador", "Status", "Condição", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
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

        jLabel4.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 25f));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("+ NOVO EMPRESTIMO");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        add(jLabel4);
        jLabel4.setBounds(1240, 20, 260, 90);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        add(jLabel5);
        jLabel5.setBounds(1240, 20, 260, 90);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        TelaEmprestimos emprestimo = new TelaEmprestimos(this);
        emprestimo.setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void botaoCadastroMouseClicked(java.awt.event.MouseEvent evt) {
        CadastroEquipamentos cadastro = new CadastroEquipamentos(this);
        cadastro.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaoCadastro;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}