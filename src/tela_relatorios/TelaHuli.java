package tela_relatorios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Rafael Moreira
 */

public class TelaHuli extends JFrame {

    public TelaHuli() {
        setTitle("Relatório das Empresas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===================== CABEÇALHO AZUL =====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 85, 170));
        headerPanel.setPreferredSize(new Dimension(700, 70));

        // Ícone de prédio à esquerda
        JLabel iconLabel = new JLabel("\uD83C\uDFE2");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Título no centro
        JLabel titleLabel = new JLabel("Relatório das Empresas", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Botão de sair (seta)
        JButton btnSair = new JButton("←");
        btnSair.setFocusPainted(false);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSair.setForeground(Color.WHITE);
        btnSair.setBackground(new Color(41, 85, 170));
        btnSair.setBorderPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> System.exit(0));
        headerPanel.add(btnSair, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ===================== PAINEL PRINCIPAL =====================
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Exemplo: botão Petrobras
        JButton btnPetrobras = criarBotaoEmpresa("Petrobras");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(btnPetrobras, gbc);

        // Exemplo: adicione outras empresas se quiser
        JButton btnVale = criarBotaoEmpresa("Vale");
        gbc.gridy++;
        mainPanel.add(btnVale, gbc);

        JButton btnShell = criarBotaoEmpresa("Shell");
        gbc.gridy++;
        mainPanel.add(btnShell, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Cria um botão de empresa que abre a tela de relatório correspondente
     */
    private JButton criarBotaoEmpresa(String nomeEmpresa) {
        JButton botao = new JButton(nomeEmpresa);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setBackground(new Color(41, 85, 170));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Evento de clique → abre a tela de relatório
        botao.addActionListener((ActionEvent e) -> {
            RelatorioCorpo telaRelatorio = new RelatorioCorpo(nomeEmpresa);
            telaRelatorio.setVisible(true);
            dispose(); // fecha a tela atual
        });

        return botao;
    }

    // ===================== MÉTODO MAIN =====================
    /**
     * Ponto de entrada da aplicação.
     * Cria e exibe a TelaHuli na Event Dispatch Thread (EDT).
     * 
     * @param args os argumentos de linha de comando
     */
    public static void main(String[] args) {
        // Usa SwingUtilities.invokeLater para garantir que a GUI seja
        // criada e atualizada com segurança na EDT.
        SwingUtilities.invokeLater(() -> {
            new TelaHuli().setVisible(true);
        });
    }
}