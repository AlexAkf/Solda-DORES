package models;

import java.sql.Timestamp;

/**
 *
 * @author Rafael Moreira
 */

public class Relatorios {

    private int id;
    private int fkGestor;
    private String nome;
    private String descricao;
    private String caminho;
    private boolean condicao;
    private Timestamp criadoEm;
    private Timestamp atualizadoEm;

    // ======== Construtores ========
    public Relatorios() {
    }

    public Relatorios(int fkGestor, String nome, String descricao, String caminho, boolean condicao) {
        this.fkGestor = fkGestor;
        this.nome = nome;
        this.descricao = descricao;
        this.caminho = caminho;
        this.condicao = condicao;
    }

    // ======== Getters e Setters ========
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkGestor() {
        return fkGestor;
    }

    public void setFkGestor(int fkGestor) {
        this.fkGestor = fkGestor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public boolean isCondicao() {
        return condicao;
    }

    public void setCondicao(boolean condicao) {
        this.condicao = condicao;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Timestamp getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Timestamp atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    @Override
    public String toString() {
        return "RelatorioModel{" +
                "id=" + id +
                ", fkGestor=" + fkGestor +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", caminho='" + caminho + '\'' +
                ", condicao=" + condicao +
                ", criadoEm=" + criadoEm +
                ", atualizadoEm=" + atualizadoEm +
                '}';
    }
}