package util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hugo
 */

public class TabelaAcaoRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selecionado, boolean bln1,
            int linha, int coluna) {
        Component com = super.getTableCellRendererComponent(jtable, o, selecionado, bln1, linha, coluna);
        PainelAcoes acoes = new PainelAcoes();
        acoes.setBackground(com.getBackground());
        if (selecionado == false && linha % 2 == 0) {
            acoes.setBackground(Color.WHITE);
        } else {
            acoes.setBackground(com.getBackground());
        }
        return acoes;
    }
}