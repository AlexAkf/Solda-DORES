package models;

import java.time.LocalDate;

/**
 * Classe modelo que representa a tabela de usuários no DB
 * a classe é estilo POJO (Plain Old Java Object), por isso tem construtores
 * vazios, aparentemente
 * é pela ausencia de frameworks. Modelos recentes parecem usar um processo de
 * JPA (Java Persistence API),
 * e pelo que vi, a escrita desse estilo JPA é bem similar a escrita do script
 * do DB
 * 
 * @author Alex
 */

public class Usuarios {

    // Campos gerais do usuário
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private boolean senha_padrao = true;
    private String cargo;
    private boolean ativo = true; // Se a conta é ativa ou não
    private String perfil; // Nível de acesso às informações do sistema

    // Campos do soldador
    private Usuarios supervisor; // Auto referência para ser usada no DAO
    private String sinete;
    private LocalDate validade_certificado;
    private LocalDate ultima_solda;

    // Construtor vazio para o DAO conseguir se comunicar com o DB e puxar as
    // informações
    public Usuarios() {
    }

    // Construtor completo para nós podermos criar os objetos
    public Usuarios(String nome, String cpf, String email, String login, String senha,
            String cargo, String perfil, Usuarios supervisor,
            String sinete, LocalDate validade_certificado, LocalDate ultima_solda) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
        this.perfil = perfil;
        this.supervisor = supervisor;
        this.sinete = sinete;
        this.validade_certificado = validade_certificado;
        this.ultima_solda = ultima_solda;
    }

    // ! =============== MÉTODOS GET ===============
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getCargo() {
        return cargo;
    }

    public String getPerfil() {
        return perfil;
    }

    public Usuarios getSupervisor() {
        return supervisor;
    }

    public String getSinete() {
        return sinete;
    }

    public LocalDate getValidade() {
        return validade_certificado;
    }

    public LocalDate getSolda() {
        return ultima_solda;
    }

    // ! =============== MÉTODOS IS ===============
    public boolean isSenhaPadrao() {
        return senha_padrao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    // ! =============== MÉTODOS SET ===============
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSenhaPadrao(boolean senha_padrao) {
        this.senha_padrao = senha_padrao;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setSupervisor(Usuarios supervisor) {
        this.supervisor = supervisor;
    }

    public void setSinete(String sinete) {
        this.sinete = sinete;
    }

    public void setValidade(LocalDate validade_certificado) {
        this.validade_certificado = validade_certificado;
    }

    public void setSolda(LocalDate ultima_solda) {
        this.ultima_solda = ultima_solda;
    }

    // ! =============== MÉTODOS AUXILIARES ===============
    public boolean validado() {
        // Se o soldador ficar um mês sem soldar o certificado perde a validade
        return !ultima_solda.plusDays(30).isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        // Para especificar o funcionário e o cargo que executou uma ação, tipo gerar um
        // relatório
        return String.format("%s (%s)", nome, cargo);
    }
}