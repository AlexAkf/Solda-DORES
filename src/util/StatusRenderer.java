package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Classe para deixar a exibição da tabela de Juntas visualmente agradável.
 * @author hugos
 */
public class StatusRenderer extends JPanel implements TableCellRenderer {

    private final JLabel label;

    public StatusRenderer() {
        setOpaque(false); // O painel não pinta fundo automaticamente
        setLayout(new GridBagLayout()); // Garante centralização real

        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Poppins", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setOpaque(true); // Permite cor de fundo

        add(label);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        String status = value != null ? value.toString() : "";

        // ======= CORES =======
        switch (status.toLowerCase()) {
            case "nao_realizado" ->
                label.setBackground(new Color(220, 53, 69));     // Vermelho
            case "em_andamento" ->
                label.setBackground(new Color(255, 193, 7));     // Amarelo
            case "concluido" ->
                label.setBackground(new Color(40, 167, 69));     // Verde
            case "refazer" ->
                label.setBackground(new Color(0, 123, 255));     // Azul
            default ->
                label.setBackground(new Color(108, 117, 125));   // Cinza
        }

        // Texto amigável
        label.setText(formatarTexto(status));

        // ======= ARREDONDAMENTO =======
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        return this;
    }

    private String formatarTexto(String s) {
        return switch (s.toLowerCase()) {
            case "nao_realizado" -> "Não realizado";
            case "em_andamento" -> "Em andamento";
            case "concluido" -> "Concluído";
            case "refazer" -> "A refazer";
            default -> s;
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 30;
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}