package tela_empresas;

import dao.EmpresasDAO;
import javax.swing.JOptionPane;
import models.Empresas;
import util.Fonte;

/**
 *
 * @author Rafhael Muzzi
 */

public class FichaEdicao extends javax.swing.JFrame {

    public FichaEdicao() {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        formatarCNPJ();
        formatarTelefone();
    }

    // Preenche os campos da tela com os dados do usuário
    public void preencherCampos(Empresas empresa) {
        this.idEmpresaselecionada = empresa.getId();

        txtempresa.setText(empresa.getNome());
        txtcnpj.setText(empresa.getCnpj());
        txtemail.setText(empresa.getEmail());
        txttelefone.setText(empresa.getTelefone());
    }

    private int idEmpresaselecionada;

    public void setIdEquipamentoSelecionado(int id) {
        this.idEmpresaselecionada = id;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtempresa = new javax.swing.JTextField();
        txtcnpj = new javax.swing.JFormattedTextField();
        txttelefone = new javax.swing.JFormattedTextField();
        txtemail = new javax.swing.JTextField();
        botaoatualizar = new javax.swing.JLabel();
        botaocancelar = new javax.swing.JLabel();
        ficha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        txtempresa.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtempresa);
        txtempresa.setBounds(730, 350, 450, 50);

        txtcnpj.setEditable(false);
        txtcnpj.setEnabled(false);
        txtcnpj.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtcnpj);
        txtcnpj.setBounds(730, 480, 450, 50);

        txttelefone.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txttelefone);
        txttelefone.setBounds(730, 608, 450, 50);

        txtemail.setFont(Fonte.inserirFonte("Poppins-Regular.ttf", 18f));
        getContentPane().add(txtemail);
        txtemail.setBounds(730, 733, 450, 50);

        botaoatualizar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaoatualizar.setForeground(new java.awt.Color(255, 255, 255));
        botaoatualizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaoatualizar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaoatualizar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaoatualizar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaoatualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoatualizarMouseClicked(evt);
            }
        });
        getContentPane().add(botaoatualizar);
        botaoatualizar.setBounds(764, 815, 390, 84);

        botaocancelar.setFont(Fonte.inserirFonte("Baloo2-Bold.ttf", 36f));
        botaocancelar.setForeground(new java.awt.Color(255, 255, 255));
        botaocancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botaocancelar.setMaximumSize(new java.awt.Dimension(260, 83));
        botaocancelar.setMinimumSize(new java.awt.Dimension(260, 83));
        botaocancelar.setPreferredSize(new java.awt.Dimension(260, 83));
        botaocancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaocancelarMouseClicked(evt);
            }
        });
        getContentPane().add(botaocancelar);
        botaocancelar.setBounds(1201, 189, 50, 50);

        ficha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fichaEmpresas.png"))); // NOI18N
        getContentPane().add(ficha);
        ficha.setBounds(0, 0, 1920, 1080);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoatualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoatualizarMouseClicked
        // Recebendo os valores cadastrados.
        String empresa = txtempresa.getText();
        String cnpj = txtcnpj.getText();
        if (!validarCNPJ(cnpj)) {
            JOptionPane.showMessageDialog(this, "CNPJ inválido! Verifique e tente novamente.");
            return;
        }
        String telefone = txttelefone.getText();
        if (!validarTelefone(telefone)) {
            JOptionPane.showMessageDialog(this, "Telefone inválido! Verifique e tente novamente.");
            return;
        }
        String email = txtemail.getText();
        if (!validaEmail(email)) {
            JOptionPane.showMessageDialog(this, "E-mail inválido! Verifique o endereço e tente novamente.");
            return;
        }

        // Enviando o cadastro.
        Empresas emp = new Empresas();
        emp.setId(idEmpresaselecionada);
        emp.setNome(empresa);
        emp.setCnpj(cnpj);
        emp.setTelefone(telefone);
        emp.setEmail(email);

        // Atualiza no banco
        EmpresasDAO dao = new EmpresasDAO();
        dao.atualizarempresa(emp);

        this.dispose(); // fecha a tela
    }//GEN-LAST:event_botaoatualizarMouseClicked

    private void botaocancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaocancelarMouseClicked
        this.dispose(); // fecha a tela
    }//GEN-LAST:event_botaocancelarMouseClicked

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
        if (cnpj == null) {
            return false;
        }

        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("\\D", "");

        // Verifica se tem 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verifica se não é uma sequência de dígitos repetidos
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

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
            return digito1 == Character.getNumericValue(cnpj.charAt(12))
                    && digito2 == Character.getNumericValue(cnpj.charAt(13));

        } catch (Exception e) {
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
    private javax.swing.JLabel botaoatualizar;
    private javax.swing.JLabel botaocancelar;
    private javax.swing.JLabel ficha;
    private javax.swing.JFormattedTextField txtcnpj;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtempresa;
    private javax.swing.JFormattedTextField txttelefone;
    // End of variables declaration//GEN-END:variables
}
