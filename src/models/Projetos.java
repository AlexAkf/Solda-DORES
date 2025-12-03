package models;

import java.time.LocalDate;

/**
 *
 * @author Rafael Silva
 */

public class Projetos {

    private int id;
    private String nome;
    private String Empresa;      // Nome da empresa
    private String Supervisor;   // Nome do supervisor (pode ser null)
    private LocalDate inicio;
    private LocalDate prazo;
    private String descricao;
    private String condicao;     // ativo, avaliacao, finalizado, cancelado

    // ===== GETTERS E SETTERS =====

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

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        this.Empresa = empresa;
    }

    public String getSupervisor() {
        return Supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.Supervisor = supervisor;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }
}