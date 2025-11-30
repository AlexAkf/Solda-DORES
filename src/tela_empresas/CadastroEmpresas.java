package tela_empresas;

import dao.EmpresasDAO;
import javax.swing.JOptionPane;
import models.Empresas;
import util.Fonte;


/**
 *
 * @author Rafhael Muzzi
 */

public class CadastroEmpresas extends javax.swing.JFrame {
    
    private final TelaEmpresas TELA;
    
    public CadastroEmpresas(TelaEmpresas tela) {
        this.TELA = tela;
        initComponents();
        formatarCNPJ();
        formatarTelefone();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botaocancelar = new javax.swing.JLabel();
        botaocadastrar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtempresa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        txtcnpj = new javax.swing.JFormattedTextField();
        txttelefone = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Solda-DORES");
        setMinimumSize(new java.awt.Dimension(770, 430));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 58, 138), 2, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 430));
        jPanel1.setLayout(null);

        jLabel1.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 40f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CADASTRAR EMPRESA");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 770, 40);

        botaocancelar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaocancelar.setForeground(new java.awt.Color(255, 255, 255));
        botaocancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaocancelar.setText("CANCELAR");
        botaocancelar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaocancelar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaocancelar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaocancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaocancelarMouseClicked(evt);
            }
        });
        jPanel1.add(botaocancelar);
        botaocancelar.setBounds(40, 320, 260, 83);

        botaocadastrar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaocadastrar.setForeground(new java.awt.Color(255, 255, 255));
        botaocadastrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaocadastrar.setText("CADASTRAR");
        botaocadastrar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaocadastrar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaocadastrar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaocadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaocadastrarMouseClicked(evt);
            }
        });
        jPanel1.add(botaocadastrar);
        botaocadastrar.setBounds(470, 320, 260, 83);

        jLabel2.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 320, 260, 83);

        jLabel3.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastro_botao.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 320, 260, 83);

        jLabel5.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel5.setText("Empresa");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 100, 180, 30);

        jLabel6.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel6.setText("CNPJ");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 150, 140, 30);

        txtempresa.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtempresa);
        txtempresa.setBounds(300, 100, 450, 30);

        jLabel8.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel8.setText("Celular");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 200, 170, 30);

        jLabel9.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 20f));
        jLabel9.setText("E-mail");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 250, 140, 30);

        txtemail.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtemail);
        txtemail.setBounds(300, 250, 450, 30);

        txtcnpj.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txtcnpj);
        txtcnpj.setBounds(300, 150, 450, 30);

        txttelefone.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        jPanel1.add(txttelefone);
        txttelefone.setBounds(300, 200, 450, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 770, 430);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaocancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaocancelarMouseClicked
        this.dispose(); // fecha a tela
    }//GEN-LAST:event_botaocancelarMouseClicked

    private void botaocadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaocadastrarMouseClicked
        // Recebendo os valores cadastrados.
        String empresa = txtempresa.getText();
        String cnpj = txtcnpj.getText();
        if (!validarCNPJ(cnpj)){
            JOptionPane.showMessageDialog(this, "CNPJ inválido! Verifique e tente novamente.");
            return;
        }
        String telefone = txttelefone.getText();
        if (!validarTelefone(telefone)){
            JOptionPane.showMessageDialog(this, "Telefone inválido! Verifique e tente novamente.");
            return;
        }
        String email = txtemail.getText().trim();
        if (!validaEmail(email)) {
            JOptionPane.showMessageDialog(this, "E-mail inválido! Verifique o endereço e tente novamente.");
            return;
        }
        

        // Enviando o cadastro.
        Empresas emp = new Empresas();
        emp.setNome(empresa);
        emp.setCnpj(cnpj);
        emp.setTelefone(telefone);
        emp.setEmail(email);
        

        // Atualiza no banco
        EmpresasDAO dao = new EmpresasDAO();
        dao.inserirempresa(emp);
        
        if (TELA != null) {
            TELA.carregarTabela();
        }
        
        this.dispose(); // fecha a tela
    }//GEN-LAST:event_botaocadastrarMouseClicked

    private boolean validaEmail(String email) {
        /* Aqui tem uma expressão regular para validar email, eu não entendi muito bem como funciona.
           Mas ela vai conferir caracteres, permitir ponto, traço, obriga a ter arroba e o domínio */
        String formato = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(formato);
    }
    
    private void formatarCNPJ() {
            try {
                javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter("##.###.###/####-##");
                mf.setPlaceholderCharacter('_');
                txtcnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mf));
            } catch (java.text.ParseException ex) {
        }
    }

    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null) return false;

        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("\\D", "");

        // Verifica se tem 14 dígitos
        if (cnpj.length() != 14) return false;

        // Verifica se não é uma sequência de dígitos repetidos
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            // Calcula primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
            }

            int digito1 = soma % 11;
            digito1 = (digito1 < 2) ? 0 : 11 - digito1;

            // Calcula segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
            }

            int digito2 = soma % 11;
            digito2 = (digito2 < 2) ? 0 : 11 - digito2;

            // Verifica se os dígitos calculados são iguais aos informados
            return digito1 == Character.getNumericValue(cnpj.charAt(12)) &&
                   digito2 == Character.getNumericValue(cnpj.charAt(13));

        } 
        catch (Exception e) {
            return false;
        }
    }
    
    
    private void formatarTelefone() {
            try {
                javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter("(##) #####-####"); // Formatação feita para telefone celular
                mf.setPlaceholderCharacter('_');
                txttelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mf));
            } catch (java.text.ParseException ex) {
        }
    }
    
    
    public static boolean validarTelefone(String telefone) {
        String regex = "^\\(\\d{2}\\)\\s?(9?\\d{4})-\\d{4}$";
        return telefone != null && telefone.matches(regex);
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botaocadastrar;
    private javax.swing.JLabel botaocancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFormattedTextField txtcnpj;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtempresa;
    private javax.swing.JFormattedTextField txttelefone;
    // End of variables declaration//GEN-END:variables
}