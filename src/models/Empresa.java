package models;

/**
 * Classe temporária para representar uma empresa.
 * Por enquanto, serve apenas para permitir o uso no DAO de Usuario.
 * Depois você pode expandir com mais atributos e métodos conforme precisar.
 * 
 * @author Muzzi, Alex
 */
public class Empresa {
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