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

    public void setid(int id) {
        this.id = id;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    public int getfk_empresa() {
        return fk_empresa;
    }

    public void setfk_empresa(int fk_empresa) {
        this.fk_empresa = fk_empresa;
    }

    public int getfk_supervisor() {
        return fk_supervisor;
    }

    public void setfk_supervisor(int fk_supervisor) {
        this.fk_supervisor = fk_supervisor;
    }

    public LocalDate getinicio() {
        return inicio;
    }

    public void setinicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getprazo() {
        return prazo;
    }

    public void setprazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getcondicao() {
        return condicao;
    }

    public void setcondicao(String condicao) {
        this.condicao = condicao;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " + fk_empresa + " - " + fk_supervisor + " - " + inicio + " - " + prazo + " - "
                + descricao + " - " + condicao;
    }

    public void setId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNome(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setFk_empresa(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setFk_supervisor(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setInicio(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setPrazo(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setDescricao(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setCondicao(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}