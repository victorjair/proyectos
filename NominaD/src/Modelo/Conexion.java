/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */
public class Conexion {
    private final String base = "consultanomina";
    private final String user = "administrador";
    private final String password = "chivas10";
    private final String url = "jdbc:mysql://server:3306/" + base;
     
    private final String basenota = "notatecnica";
    private final String usernota = "administrador";
    private final String passwordnota = "chivas10";
    private final String urlnota = "jdbc:mysql://server:3306/" + basenota;
    
     private final String basenotafonsar = "bachilleresguerrerofonsar";
    private final String usernotafonsar = "administrador";
    private final String passwordnotafonsar = "chivas10";
    private final String urlnotafonsar = "jdbc:mysql://server:3306/" + basenotafonsar;
    
    private final String basenotaremoto = "progr073_notatecnica";
    private final String usernotaremoto = "progr073_sefi";
    private final String passwordnotaremoto = "chivas10";
    private final String urlnotaremoto = "jdbc:mysql://programasefi.com.mx/" + basenotaremoto;
    
    private final String basenotaremotofonsar = "progr073_programa_fonsar";
    private final String usernotaremotofonsar = "progr073_sefi";
    private final String passwordnotaremotofonsar = "chivas10";
    private final String urlnotaremotofonsar = "jdbc:mysql://programasefi.com.mx/" + basenotaremotofonsar;
    
    private Connection con = null;
    private Connection connota = null;
    private Connection connotaremoto = null;
    private Connection connotafonsar = null;
   private Connection connotaremotofonsar = null;
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
     
     public Connection getConexionnotaRemoto(){
    
        try{
       
            Class.forName("com.mysql.jdbc.Driver");
            connotaremoto = (Connection)DriverManager.getConnection(this.urlnotaremoto,this.usernotaremoto,this.passwordnotaremoto);
            
        } 
        catch(SQLException e){
            
            System.err.println(e);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connotaremoto;
        
    
    }
     
     public Connection getConexionnotaRemotoFonsar(){
    
        try{
       
            Class.forName("com.mysql.jdbc.Driver");
            connotaremotofonsar = (Connection)DriverManager.getConnection(this.urlnotaremotofonsar,this.usernotaremotofonsar,this.passwordnotaremotofonsar);
            
        } 
        catch(SQLException e){
            
            System.err.println(e);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connotaremotofonsar;
        
    
    }
     
     public Connection getConexionfonsar(){
    
        try{
       
            Class.forName("com.mysql.jdbc.Driver");
            connotafonsar = (Connection)DriverManager.getConnection(this.urlnotafonsar,this.usernotafonsar,this.passwordnotafonsar);
            
        } 
        catch(SQLException e){
            
            System.err.println(e);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connotafonsar;
        
    
    }
    
}
