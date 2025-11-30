package util;

import java.awt.event.ActionEvent;

/**
 *
 * @author Hugo
 */

public class PainelAcoes extends javax.swing.JPanel {
    public PainelAcoes() {
        initComponents();
    }

    public void iniciaEvento(TabelaAcaoEvento evento, int linha) {
        botaoEditar.addActionListener((ActionEvent ae) -> {
            evento.editando(linha);
        });
        botaoExcluir.addActionListener((ActionEvent ae) -> {
            evento.excluindo(linha);
        });
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoEditar = new util.BotaoAcoes();
        botaoExcluir = new util.BotaoAcoes();

        botaoEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        botaoEditar.setText("EDITAR");
        botaoEditar.setFocusable(false);
        botaoEditar.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 15f));
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });

        botaoExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excluir.png"))); // NOI18N
        botaoExcluir.setText("EXCLUIR");
        botaoExcluir.setFocusable(false);
        botaoExcluir.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 15f));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botaoExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private util.BotaoAcoes botaoEditar;
    private util.BotaoAcoes botaoExcluir;
    // End of variables declaration//GEN-END:variables
}