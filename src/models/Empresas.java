package models;

/**
 * 
 * @author Rafhael Muzzi
 */

public class Empresas {

    // Campos gerais da empresa
    private int id;
    private String nome;
    private String cnpj;
    private String email;
    private boolean condicao = true;// Para identificar se está ativa ou não
    private String telefone;

    public Empresas() {
    }

    public Empresas(int id, String nome, String cnpj, String email, boolean condicao, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.condicao = condicao;
        this.telefone = telefone;
    }

    // Getters e Setters ↓↓↓

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //#####################################
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //#####################################
    
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    //#####################################
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //#####################################

    public boolean isCondicao() {
        return condicao;
    }

    public void setCondicao(boolean condicao) {
        this.condicao = condicao;
    }

    //#####################################
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}