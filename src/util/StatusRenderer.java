package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * Classe para deixar a exibição da tabela de Juntas visualmente agradável.
 * @author hugos
 */
public class StatusRenderer extends JPanel implements TableCellRenderer {

    private final JLabel label;
    private Color bgColor = Color.GRAY;

    public StatusRenderer() {
        setOpaque(true); 
        setLayout(new GridBagLayout());

        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Poppins", Font.BOLD, 14));
        label.setForeground(Color.WHITE);

        add(label);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        String status = value != null ? value.toString() : "";

        // Escolhe a cor de fundo
        switch (status.toLowerCase()) {
            case "nao_realizado" -> bgColor = new Color(220, 53, 69);
            case "em_andamento" -> bgColor = new Color(255, 193, 7);
            case "concluido" -> bgColor = new Color(40, 167, 69);
            case "refazer" -> bgColor = new Color(0, 123, 255);
            default -> bgColor = new Color(108, 117, 125);
        }

        label.setText(formatarTexto(status));

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
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
}
