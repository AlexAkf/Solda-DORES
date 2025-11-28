package models;

import java.time.LocalDate;

/**
 *
 * @author Rafael Silva
 */

public class Projetos {
    private int id;
    private String nome;
    private int fk_empresa;
    private int fk_supervisor;
    private LocalDate inicio;
    private LocalDate prazo;
    private String descricao;
    private String condicao;

    public Projetos() {
    }

    public Projetos(int id, String nome, int fk_empresa, int fk_supervisor, LocalDate inicio, LocalDate prazo,
            String descricao, String condicao) {
        this.id = id;
        this.nome = nome;
        this.fk_empresa = fk_empresa;
        this.fk_supervisor = fk_supervisor;
        this.inicio = inicio;
        this.prazo = prazo;
        this.descricao = descricao;
        this.condicao = condicao;
    }

    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getfk_empresa() {
        return fk_empresa;
    }

    public void setFk_empresa(int fk_empresa) {
        this.fk_empresa = fk_empresa;
    }

    public int getfk_supervisor() {
        return fk_supervisor;
    }

    public void setFk_supervisor(int fk_supervisor) {
        this.fk_supervisor = fk_supervisor;
    }

    public LocalDate getinicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getprazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getcondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " + fk_empresa + " - " + fk_supervisor + " - " + inicio + " - " + prazo + " - "
                + descricao + " - " + condicao;
    }
}