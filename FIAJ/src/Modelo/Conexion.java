
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    
   /* private final String base = "bachilleresguerrero";
    private final String user = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost:3306/" + base;*/
    
    private final String base = "fiaj";
    private final String user = "administrador";
    private final String password = "chivas10";
    private final String url = "jdbc:mysql://server:3306/" + base;
    //private final String url = "jdbc:mysql://10.0.0.26:3306/" + base;
    
    //servidor antiguo
    //private final String url = "jdbc:mysql://10.0.0.2:3306/" + base;
    
    private final String basenota = "notatecnica";
    private final String usernota = "administrador";
    private final String passwordnota = "chivas10";
    //private final String urlnota = "jdbc:mysql://10.0.0.26:3306/" + basenota;
    private final String urlnota = "jdbc:mysql://server:3306/" + basenota;
      
//servidor antiguo
    //private final String urlnota = "jdbc:mysql://10.0.0.2:3306/" + basenota;
    
    private final String basefiar = "fiaj";
    private final String userfiar = "administrador";
    private final String passwordfiar = "chivas10";
    private final String urlfiar = "jdbc:mysql://server:3306/" + basefiar;
    //servidor antiguo
    //private final String urlfiar = "jdbc:mysql://10.0.0.2:3306/" + basefiar;
    
    
    
    private Connection con = null;
     private Connection connota = null;
   private Connection confiar = null;
    
    public Connection getConexion(){
    
        try{
       
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection)DriverManager.getConnection(this.url,this.user,this.password);
            
        } 
        catch(SQLException e){
            
            System.err.println(e);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
        
    
    }
    
    public Connection getConexionnota(){
    
        try{
       
            Class.forName("com.mysql.jdbc.Driver");
            connota = (Connection)DriverManager.getConnection(this.urlnota,this.usernota,this.passwordnota);
            
        } 
        catch(SQLException e){
            
            System.err.println(e);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connota;
        
    
    }
    
    public Connection getConexionFiar(){
    
        try{
       
            Class.forName("com.mysql.jdbc.Driver");
            confiar = (Connection)DriverManager.getConnection(this.urlfiar,this.userfiar,this.passwordfiar);
            
        } 
        catch(SQLException e){
            
            System.err.println(e);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return confiar;
        
    
    }
    
    
}
