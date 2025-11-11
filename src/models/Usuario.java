package models;
import java.time.LocalDate;

/**
 * Classe modelo que representa a tabela de usuários no banco de dados
 * 
 * estilo POJO (Plain Old Java Object) -> por isso tem construtores vazios,
 * aparentemente é pela ausencia de frameworks. Modelos recentes parecem usar
 * JPA (Java Persistence API) e pelo que vi, a escrita é bem similar a do BD
 *  
 * @author Alex
 */
public class Usuario{

    // Campos gerais de usuario
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private boolean senhaPadrao = true;
    private String cargo;   // gestor, supervisor e soldador
    private boolean condicao = true;   // se a conta é ativa ou não
    private String perfil;  // adm, restrito ou comum
    private Empresa empresa;

    // Campos do soldador
    private Usuario supervisor;
    private String sinete;
    private LocalDate validadeCertificado;
    private LocalDate ultimaSolda;



    // Construtor vazio para o DAO conseguir se comunicar com o BD e puxar as informações
    public Usuario(){}

    // Construtor completo para nós podermos criar objetos
    public Usuario(int id, String nome, String cpf, String email, String login, String senha, boolean senhaPadrao, String cargo, boolean condicao,
                   String perfil, Empresa empresa, Usuario supervisor, String sinete, LocalDate validadeCertificado, LocalDate ultimaSolda
                  ){
       this.id = id;
       this.nome = nome;
       this.cpf = cpf;
       this.email = email;
       this.login = login;
       this.senha = senha;
       this.senhaPadrao = senhaPadrao;
       this.cargo = cargo;
       this.condicao = condicao;
       this.perfil = perfil;
       this.empresa = empresa;
       this.supervisor = supervisor;
       this.sinete = sinete;
       this.validadeCertificado = validadeCertificado;
       this.ultimaSolda = ultimaSolda;
    }


    // gets e sets
    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}

    public String getCpf(){return cpf;}
    public void setCpf(String cpf){this.cpf = cpf;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getLogin(){return login;}
    public void setLogin(String login){this.login = login;}

    public String getSenha(){return senha;}
    public void setSenha(String senha){this.senha = senha;}

    public boolean isSenhaPadrao(){return senhaPadrao;} // -------------> boolean | no lugar de get é is
    public void setSenhaPadrao(boolean senhaPadrao){this.senhaPadrao = senhaPadrao;}

    public String getCargo(){return cargo;}
    public void setCargo(String cargo){this.cargo = cargo;}

    public boolean isCondicao(){return condicao;}   // -------------> boolean | no lugar de get é is
    public void setCondicao(boolean condicao){this.condicao = condicao;}
    
    public String getPerfil(){return perfil;}
    public void setPerfil(String perfil){this.perfil = perfil;}

    public Empresa getEmpresa(){return empresa;}
    public void setEmpresa(Empresa empresa){this.empresa = empresa;}
    
    public Usuario getSupervisor(){return supervisor;}
    public void setSupervisor(Usuario supervisor){this.supervisor = supervisor;}

    public String getSinete(){return sinete;}
    public void setSinete(String sinete){this.sinete = sinete;}

    public LocalDate getValidadeCertificado(){return validadeCertificado;}
    public void setValidadeCertificado(LocalDate validadeCertificado){this.validadeCertificado = validadeCertificado;}

    public LocalDate getUltimaSolda(){return ultimaSolda;}
    public void setUltimaSolda(LocalDate ultimaSolda){this.ultimaSolda = ultimaSolda;}


    // métodos auxiliares
    public boolean certificadoValido(){
      if(ultimaSolda.plusDays(30).isBefore(LocalDate.now())){   // se ele passar um mês sem soldar o certificado perde a validade
          return false;
      }
      return true;
    }
    
    @Override
    public String toString(){
      return String.format("%s (%s) - %s", nome, cargo, empresa);
    }
}