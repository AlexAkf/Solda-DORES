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
    private String soldador;
    private String condicao;

    // Getters
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getSoldador() {
        return soldador;
    }

    public String getCondicao() {
        return condicao;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setSoldador(String soldador) {
        this.soldador = soldador;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }
}