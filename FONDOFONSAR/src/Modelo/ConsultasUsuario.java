/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author victor
 */
public class ConsultasUsuario extends Conexion{
    
    
    public boolean verificausuario(Usuario mov){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection con = getConexion();
                String sql = "SELECT * FROM usuario WHERE Usuario = ? and Password = ?";
                
         try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getUsuario());
            ps.setString(2, mov.getPassword());
            rs = ps.executeQuery();        
        if(rs.next()){
            
                mov.setUsuario(rs.getString("Usuario"));
                mov.setTipoUsuario(rs.getString("Tipo"));
                
                return true;
            }
            else
            {
            return false;
            
            }
            
            
        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }
         
    }
    
}
