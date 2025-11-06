package models;

/**
 *
 * @author Hugo
 */

public class Equipamentos {
    private String codigo;
    private String modelo;
    private String marca;
    private String condicao;
    private String soldador;

    public Equipamentos(String codigo, String modelo, String marca, String condicao, String soldador) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.condicao = condicao;
        this.soldador = soldador;
    }

    public String getCodigo() { return codigo; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public String getCondicao() { return condicao; }
    public String getSoldador() { return soldador; }
}