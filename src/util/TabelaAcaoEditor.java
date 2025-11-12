package util;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;

/**
 *
 * @author Hugo
 */
public class TabelaAcaoEditor extends DefaultCellEditor{
    
    private final TabelaAcaoEvento evento;
        
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