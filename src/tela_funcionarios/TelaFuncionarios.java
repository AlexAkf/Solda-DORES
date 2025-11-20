package tela_funcionarios;

import dao.UsuariosDAO;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.Usuarios;
import util.Fonte;
import util.TabelaAcaoEditor;
import util.TabelaAcaoEvento;
import util.TabelaAcaoRender;

/**
 *
 * @author Alex
 */

public final class TelaFuncionarios extends javax.swing.JPanel {

    /**
     * Creates new form TelaFuncionarios
     * 
     * @throws java.sql.SQLException
     */

    public void carregarTabela() throws SQLException {
        var data = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formata a data no padrão normal

        UsuariosDAO dao = new UsuariosDAO();
        List<Usuarios> lista = dao.listar();

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0); // limpa a tabela

        for (Usuarios usuario : lista) {

            // Elvis operator para formatar as datas e o supervisor
            String validade = (usuario.getValidade() != null) ? usuario.getValidade().format(data) : "—";
            String supervisor = (usuario.getSupervisor() != null) ? usuario.getSupervisor().getNome() : "—";
            String sinete = (usuario.getSinete() != null) ? usuario.getSinete() : "—";

            modelo.addRow(new Object[] {
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getCpf(),
                    usuario.getEmail(),
                    usuario.getLogin(),
                    usuario.getCargo(),
                    sinete,
                    supervisor,
                    validade,
                    null
            });
        }
    }

    public TelaFuncionarios() throws SQLException {
        initComponents();
        carregarTabela();

        // ============= PERSONALIZAÇÃO =============
        // Ocultando a coluna de ID
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(0);

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

        // Medidas da coluna de CPF
        tabela.getColumnModel().getColumn(2).setMinWidth(125);
        tabela.getColumnModel().getColumn(2).setMaxWidth(125);
        tabela.getColumnModel().getColumn(2).setWidth(125);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(125);

        // Medidas da coluna de email
        tabela.getColumnModel().getColumn(3).setMinWidth(300);
        tabela.getColumnModel().getColumn(3).setMaxWidth(300);
        tabela.getColumnModel().getColumn(3).setWidth(300);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(300);

        // Medidas da coluna de sinete
        tabela.getColumnModel().getColumn(6).setMinWidth(125);
        tabela.getColumnModel().getColumn(6).setMaxWidth(125);
        tabela.getColumnModel().getColumn(6).setWidth(125);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(125);

        // Medidas da coluna de certificado
        tabela.getColumnModel().getColumn(8).setMinWidth(125);
        tabela.getColumnModel().getColumn(8).setMaxWidth(125);
        tabela.getColumnModel().getColumn(8).setWidth(125);
        tabela.getColumnModel().getColumn(8).setPreferredWidth(125);

        // ========== BOTÕES DE AÇÃO DA TABELA ==========
        TabelaAcaoEvento evento;
        evento = new TabelaAcaoEvento() {
            @Override
            public void editando(int linha) {
                // Pega os dados da linha selecionada
                int id = (int) tabela.getValueAt(linha, 0);
                String nome = (String) tabela.getValueAt(linha, 1);
                String cpf = (String) tabela.getValueAt(linha, 2);
                String email = (String) tabela.getValueAt(linha, 3);
                String login = (String) tabela.getValueAt(linha, 4);
                String cargo = (String) tabela.getValueAt(linha, 5);
                String sinete = (String) tabela.getValueAt(linha, 6);
                String supervisorNome = (String) tabela.getValueAt(linha, 7);
                String validadeStr = (String) tabela.getValueAt(linha, 8);

                // Cria o objeto Usuarios
                Usuarios usuario = new Usuarios();
                usuario.setId(id);
                usuario.setNome(nome);
                usuario.setCpf(cpf);
                usuario.setEmail(email);
                usuario.setLogin(login);
                usuario.setCargo(cargo);
                usuario.setSinete(sinete);

                // Configura o supervisor se houver
                if (!supervisorNome.equals("—")) {
                    Usuarios supervisor = new Usuarios();
                    supervisor.setNome(supervisorNome);
                    usuario.setSupervisor(supervisor);
                }

                // Configura a validade se houver
                if (!validadeStr.equals("—")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    usuario.setValidade(java.time.LocalDate.parse(validadeStr, formatter));
                }

                // Abre a tela de atualização
                AtualizarFuncionarios atualizar = new AtualizarFuncionarios(TelaFuncionarios.this);
                atualizar.setUsuarioSelecionado(usuario);
                atualizar.preencherCampos(usuario);
                atualizar.setVisible(true);
            }

            @Override
            public void excluindo(int linha) {
                int idUsuario = (int) tabela.getValueAt(linha, 0);

                int opcao = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente excluir o usuário ID " + idUsuario + "?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    try {
                        UsuariosDAO dao = new UsuariosDAO();
                        dao.deletar(idUsuario);
                        carregarTabela();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "Erro ao excluir: " + e.getMessage());
                    }
                }
            }
        };

        tabela.getColumnModel().getColumn(9).setCellRenderer(new TabelaAcaoRender());
        tabela.getColumnModel().getColumn(9).setCellEditor(new TabelaAcaoEditor(evento));
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
                "ID", "Nome", "CPF", "E-mail", "Login", "Cargo", "Sinete", "Supervisor", "Certificado", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}