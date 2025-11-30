package tela_relatorios;

import dao.RelatoriosDAO;
import models.Relatorios;
import dao.EquipamentosDAO;
import controllers.Conexao;
import models.Equipamentos;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;

/**
 *
 * @author Rafael Moreira
 */

public class RelatorioCorpo extends JFrame {

    private final JTextField txtCliente;
    private final JTextArea txtEquipamento;
    private final JTextArea txtServicos;
    private final String nomeEmpresa;

    public RelatorioCorpo(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Relatório da Empresa");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===================== CABEÇALHO =====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 85, 170));
        headerPanel.setPreferredSize(new Dimension(700, 70));

        // Ícone (emoji)
        JLabel iconLabel = new JLabel("\uD83D\uDCCB");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Título
        JLabel titleLabel = new JLabel("Relatório da Empresa " + nomeEmpresa, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Botão voltar
        JButton btnVoltar = new JButton("←");
        btnVoltar.setFocusPainted(false);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(41, 85, 170));
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            dispose();
        });
        headerPanel.add(btnVoltar, BorderLayout.EAST);

        // Adiciona ao topo da tela
        add(headerPanel, BorderLayout.NORTH);

        // ===================== FORM =====================
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cliente
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Cliente:"), gbc);

        gbc.gridy++;
        txtCliente = new JTextField();
        txtCliente.setPreferredSize(new Dimension(400, 30));
        formPanel.add(txtCliente, gbc);

        // Equipamento
        gbc.gridy++;
        formPanel.add(new JLabel("Equipamento Inspecionado:"), gbc);

        gbc.gridy++;
        txtEquipamento = new JTextArea(4, 40);
        txtEquipamento.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtEquipamento.setLineWrap(true);
        txtEquipamento.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(txtEquipamento), gbc);

        // Serviços
        gbc.gridy++;
        formPanel.add(new JLabel("Serviços:"), gbc);

        gbc.gridy++;
        txtServicos = new JTextArea(4, 40);
        txtServicos.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtServicos.setLineWrap(true);
        txtServicos.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(txtServicos), gbc);

        // Botão PDF
        gbc.gridy++;
        JButton btnGerarPDF = new JButton("Gerar PDF");
        btnGerarPDF.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGerarPDF.setBackground(new Color(41, 85, 170));
        btnGerarPDF.setForeground(Color.WHITE);
        btnGerarPDF.setFocusPainted(false);
        btnGerarPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGerarPDF.addActionListener(this::gerarPDF);
        formPanel.add(btnGerarPDF, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    // =====================================================================
    // GERAR PDF COMPLETO (Manual + Equipamentos + Empréstimos ativos e finalizados)
    // =====================================================================
    private void gerarPDF(ActionEvent e) {

        String cliente = txtCliente.getText().trim();
        String equipamentoTxt = txtEquipamento.getText().trim();
        String servicos = txtServicos.getText().trim();

        if (cliente.isEmpty() || equipamentoTxt.isEmpty() || servicos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos antes de gerar o PDF.",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar PDF");
        fileChooser.setSelectedFile(new java.io.File("Relatorio_" + nomeEmpresa + ".pdf"));

        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String caminho = fileChooser.getSelectedFile().getAbsolutePath();
        if (!caminho.toLowerCase().endsWith(".pdf")) {
            caminho += ".pdf";
        }

        Document document = new Document(PageSize.A4);

        try (FileOutputStream fos = new FileOutputStream(caminho)) {
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            document.open();

            String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            // ===========================================================
            // CABEÇALHO
            // ===========================================================
            com.itextpdf.text.Font tituloFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,
                    18, com.itextpdf.text.Font.BOLD);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // ===========================================================
            // RELATÓRIO PRINCIPAL (tela)
            // ===========================================================
            document.add(new Paragraph("RELATÓRIO PRINCIPAL"));
            document.add(new Paragraph("-----------------------------------------"));
            document.add(new Paragraph("Empresa: " + nomeEmpresa));
            document.add(new Paragraph("Cliente: " + cliente));
            document.add(new Paragraph("Equipamento Inspecionado: " + equipamentoTxt));
            document.add(new Paragraph("Serviços: " + servicos));
            document.add(new Paragraph(" "));

            // ===========================================================
            // RELATÓRIO DE EQUIPAMENTOS
            // ===========================================================
            document.newPage();
            document.add(new Paragraph("RELATÓRIO DE EQUIPAMENTOS"));
            document.add(new Paragraph("-----------------------------------------"));

            EquipamentosDAO equipamentosDAO = new EquipamentosDAO();
            List<Equipamentos> listaEq = equipamentosDAO.listarEquipamentos();

            PdfPTable tabelaEq = new PdfPTable(6); // colunas
            tabelaEq.setWidthPercentage(100);
            tabelaEq.addCell("ID");
            tabelaEq.addCell("Código");
            tabelaEq.addCell("Modelo");
            tabelaEq.addCell("Marca");
            tabelaEq.addCell("Condição");
            tabelaEq.addCell("Soldador");

            for (Equipamentos eq : listaEq) {
                tabelaEq.addCell(String.valueOf(eq.getId()));
                tabelaEq.addCell(eq.getCodigo() != null ? eq.getCodigo() : "-");
                tabelaEq.addCell(eq.getModelo() != null ? eq.getModelo() : "-");
                tabelaEq.addCell(eq.getMarca() != null ? eq.getMarca() : "-");
                tabelaEq.addCell(eq.getCondicao() != null ? eq.getCondicao() : "-");
                tabelaEq.addCell(eq.getSoldador() != null ? eq.getSoldador() : "—");
            }

            document.add(tabelaEq);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total de equipamentos: " + listaEq.size()));

            // ===========================================================
            // EMPRÉSTIMOS ATIVOS
            // ===========================================================
            document.newPage();
            document.add(new Paragraph("RELATÓRIO DE EMPRÉSTIMOS (ATIVOS)"));
            document.add(new Paragraph("-----------------------------------------"));

            String sqlEmprestados = """
                    SELECT em.id, e.codigo, e.modelo, e.marca, u.nome AS soldador, em.emprestimo
                    FROM emprestimos em
                    JOIN equipamentos e ON em.fk_equipamento = e.id
                    JOIN usuarios u ON em.fk_soldador = u.id
                    WHERE em.devolucao IS NULL
                    ORDER BY em.emprestimo DESC;
                    """;

            PdfPTable tabelaAtivos = new PdfPTable(6);
            tabelaAtivos.setWidthPercentage(100);
            tabelaAtivos.addCell("ID");
            tabelaAtivos.addCell("Código");
            tabelaAtivos.addCell("Modelo");
            tabelaAtivos.addCell("Marca");
            tabelaAtivos.addCell("Soldador");
            tabelaAtivos.addCell("Empréstimo");

            try (Connection conn = Conexao.getConexao();
                    PreparedStatement ps = conn.prepareStatement(sqlEmprestados);
                    ResultSet rs = ps.executeQuery()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                boolean temAtivos = false;
                while (rs.next()) {
                    temAtivos = true;
                    tabelaAtivos.addCell(String.valueOf(rs.getInt("id")));
                    tabelaAtivos.addCell(rs.getString("codigo") != null ? rs.getString("codigo") : "-");
                    tabelaAtivos.addCell(rs.getString("modelo") != null ? rs.getString("modelo") : "-");
                    tabelaAtivos.addCell(rs.getString("marca") != null ? rs.getString("marca") : "-");
                    tabelaAtivos.addCell(rs.getString("soldador") != null ? rs.getString("soldador") : "—");
                    tabelaAtivos.addCell(rs.getDate("emprestimo") != null ? sdf.format(rs.getDate("emprestimo")) : "-");
                }

                if (!temAtivos) {
                    document.add(new Paragraph("Nenhum empréstimo ativo no momento."));
                } else {
                    document.add(tabelaAtivos);
                }

            } catch (Exception ex) {
                document.add(new Paragraph("Erro ao carregar empréstimos ativos: " + ex.getMessage()));
                System.out.println("Erro SQL (ativos): " + ex.getMessage());
            }

            // ===========================================================
            // EMPRÉSTIMOS FINALIZADOS
            // ===========================================================
            document.newPage();
            document.add(new Paragraph("EMPRÉSTIMOS FINALIZADOS"));
            document.add(new Paragraph("-----------------------------------------"));

            String sqlFinalizados = """
                    SELECT em.id, e.codigo, e.modelo, e.marca, u.nome AS soldador, em.emprestimo, em.devolucao
                    FROM emprestimos em
                    JOIN equipamentos e ON em.fk_equipamento = e.id
                    JOIN usuarios u ON em.fk_soldador = u.id
                    WHERE em.devolucao IS NOT NULL
                    ORDER BY em.devolucao DESC;
                    """;

            PdfPTable tabelaFin = new PdfPTable(7);
            tabelaFin.setWidthPercentage(100);
            tabelaFin.addCell("ID");
            tabelaFin.addCell("Código");
            tabelaFin.addCell("Modelo");
            tabelaFin.addCell("Marca");
            tabelaFin.addCell("Soldador");
            tabelaFin.addCell("Empréstimo");
            tabelaFin.addCell("Devolução");

            try (Connection conn = Conexao.getConexao();
                    PreparedStatement ps = conn.prepareStatement(sqlFinalizados);
                    ResultSet rs = ps.executeQuery()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                boolean temFinalizados = false;
                while (rs.next()) {
                    temFinalizados = true;
                    tabelaFin.addCell(String.valueOf(rs.getInt("id")));
                    tabelaFin.addCell(rs.getString("codigo") != null ? rs.getString("codigo") : "-");
                    tabelaFin.addCell(rs.getString("modelo") != null ? rs.getString("modelo") : "-");
                    tabelaFin.addCell(rs.getString("marca") != null ? rs.getString("marca") : "-");
                    tabelaFin.addCell(rs.getString("soldador") != null ? rs.getString("soldador") : "—");
                    tabelaFin.addCell(rs.getDate("emprestimo") != null ? sdf.format(rs.getDate("emprestimo")) : "-");
                    tabelaFin.addCell(rs.getDate("devolucao") != null ? sdf.format(rs.getDate("devolucao")) : "-");
                }

                if (!temFinalizados) {
                    document.add(new Paragraph("Nenhum empréstimo finalizado encontrado."));
                } else {
                    document.add(tabelaFin);
                }

            } catch (Exception ex) {
                document.add(new Paragraph("Erro ao carregar empréstimos finalizados: " + ex.getMessage()));
                System.out.println("Erro SQL (finalizados): " + ex.getMessage());
            }

            // ===========================================================
            // RODAPÉ
            // ===========================================================
            document.add(new Paragraph(" "));
            document.add(new Paragraph("--------------------------------------------------"));
            document.add(new Paragraph("Data de Criação" + dataHora));

            document.close();
            writer.close();

            // Salva no banco (registro do relatório)
            Relatorios rel = new Relatorios();
            rel.setFkGestor(1);
            rel.setNome("Relatório Completo de " + nomeEmpresa);
            rel.setDescricao("Relatório manual + equipamentos + empréstimos (ativos e finalizados)");
            rel.setCaminho(caminho);
            rel.setCondicao(true);

            new RelatoriosDAO().inserirRelatorio(rel);

            JOptionPane.showMessageDialog(this,
                    "PDF gerado com sucesso!\n" + caminho,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar PDF: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}