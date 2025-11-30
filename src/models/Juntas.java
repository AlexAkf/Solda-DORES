package models;

/**
 *
 * @author hugos
 */
public class Juntas {
    
    private int id;
    private String projeto;
    private String status;
    private double comprimento;
    private String codigo; 

    // Getters
    public int getId() {
        return id;
    }

    public String getProjeto() {
        return projeto;
    }

    public String getStatus() {
        return status;
    }

    public double getComprimento() {
        return comprimento;
    }

    public String getCodigo() {
        return codigo;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}