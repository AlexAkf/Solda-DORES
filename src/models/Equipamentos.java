package models;

/**
 *
 * @author Hugo
 */
public class Equipamentos {
    private int id;
    private String codigo;
    private String modelo;
    private String marca;
    private String condicao;
    private String soldador;

    // 🔹 Construtor usado quando o registro já existe no banco (tem ID)
    public Equipamentos(int id, String codigo, String modelo, String marca, String condicao, String soldador) {
        this.id = id;
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.condicao = condicao;
        this.soldador = soldador;
    }

    // 🔹 Construtor usado no cadastro (antes de ter ID)
    public Equipamentos(String codigo, String modelo, String marca, String condicao, String soldador) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.condicao = condicao;
        this.soldador = soldador;
    }

    // Getters
    public int getID() { return id; }
    public String getCodigo() { return codigo; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public String getCondicao() { return condicao; }
    public String getSoldador() { return soldador; }

    // Setters (caso precise atualizar depois)
    public void setId(int id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setCondicao(String condicao) { this.condicao = condicao; }
    public void setSoldador(String soldador) { this.soldador = soldador; }
}