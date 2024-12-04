/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author Henrique
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Conexao {
    private Statement stmt; //executar consultas DML
    private ResultSet rs; //gerenciar consultas DQL
    private Connection c;         


    public Conexao() {
        String Database = "trab1_psc";
        String user = "alunos_psc_1";
        String password = "Hde15@jUd-";        
        String server = "trab1-psc.mysql.uhserver.com";
        String url = "jdbc:mysql://" + server + ":3306/" + Database + "?serverTimezone=UTC";
        
        //Abrir conexão
        try {      
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            c = DriverManager.getConnection(url, user, password);
            stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);              
        }
        catch (Exception e) {            
            JOptionPane.showMessageDialog(null, "Erro na conexão MySQL");            
            JOptionPane.showMessageDialog(null, e.getMessage());
            JOptionPane.showMessageDialog(null, "A aplicação será finalizada...");
            System.exit(0); //Finalizar a aplicação
        }              
    }    
    
    public boolean SQLExecute(String SQL) {
        try {
            this.stmt.execute(SQL);
            return true;
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }        
    }


    public boolean setResultSet(String Query) {
        try {
            this.rs = this.stmt.executeQuery(Query);
            return true;
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }                
    }             
    
    public ResultSet getResultSet() {
        return rs;
    }       
    
    public DatabaseMetaData getMetaData()
    {
        try
        {
            return c.getMetaData();
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }
}
