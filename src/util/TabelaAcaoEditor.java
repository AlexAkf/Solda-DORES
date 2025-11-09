/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;

/**
 *
 * @author hugos
 */
public class TabelaAcaoEditor extends DefaultCellEditor{
    
    private TabelaAcaoEvento evento;
        
    public TabelaAcaoEditor(TabelaAcaoEvento evento){
        
        super(new JCheckBox());
        this.evento = evento;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int linha, int coluna) {
        PainelAcoes acao = new PainelAcoes();
        acao.iniciaEvento(evento, linha);
        acao.setBackground(jtable.getSelectionBackground());
        return acao;
    }
}
