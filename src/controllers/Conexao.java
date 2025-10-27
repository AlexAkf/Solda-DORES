package controllers;

/**
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
*/

/**
 *
 * @author Hugo
 */

/**
public class Conexao {
    private static Conexao instancia;
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String BD = "jdbc:mysql://<host>:<porta>/<nome_do_banco>?<opções>";
    private static Connection conexao;
    
    private Conexao(){
    
    }
    
    public static Conexao getInstancia() {
        if(instancia==null){
            instancia = new Conexao();
        }
        return instancia;
    }
    
    public Connection abrirCOnexao(){
        
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(BD);
            conexao.setAutoCommit(false);
        } catch (SQLException | ClassNotDoundException e) {
            System.out.println("Erro ao conectar com o banco de dados" + e.printStackTrace());
        }
        return conexao;
    }
    
    public void fecharConexao() {
        try {
            if(conexao!=null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexao: " + e.getMessage());
        } finally {
            conexao = null;
        }
    }
}
*/