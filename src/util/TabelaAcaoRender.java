/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author hugos
 */
public class TabelaAcaoRender extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selecionado, boolean bln1, int linha, int coluna) {
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
