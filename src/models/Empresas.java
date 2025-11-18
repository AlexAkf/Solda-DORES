package models;

/**
 * Classe temporária para representar uma empresa.
 * Por enquanto, serve apenas para permitir o uso no DAO de Usuario.
 * Depois você pode expandir com mais atributos e métodos conforme precisar.
 * 
 * @author Rafhael Muzzi

public class Empresas {
    private Integer id;
    private String nome;

    // Construtor vazio (necessário para criação simples)
    public Empresa() {}

    // Construtor opcional para criar empresa direto com ID
    public Empresa(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getter e Setter do ID
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter e Setter do nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Representação textual (opcional, útil em comboboxes e logs)
    @Override
    public String toString() {
        return nome != null ? nome : "Empresa sem nome";
    }
}
 */
/** */
public class Empresas {

    private int id;
    private String nome;
    private String cnpj;
    private String email;
    private boolean condicao;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCondicao() {
        return condicao;
    }

    public void setCondicao(boolean condicao) {
        this.condicao = condicao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}