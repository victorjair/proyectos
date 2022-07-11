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
public class ConsultasTasas extends Conexion{
    
    public boolean registrar(Tasas tasa){
    PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO tasa(santander,banamex,bancomer,hsbc,promedio,"
                + "ano,mes) VALUES (?,?,?,?,?,?,?)";
        
        try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1,tasa.getTasaSantander());
            ps.setDouble(2,tasa.getTasaBanamex());
            ps.setDouble(3, tasa.getTasaBancomer());
            ps.setDouble(4,tasa.getTasaHsbc());
            ps.setDouble(5, tasa.getPromedio());
            ps.setString(6, tasa.getPeriodo());
             ps.setString(7, tasa.getMes());
           
              ps.execute();
            return true;

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
    
    public boolean eliminar(Tasas tasa){
        PreparedStatement ps = null;
        Connection con = getConexion();

        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "DELETE from tasa WHERE Id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, tasa.getId());
                ps.execute();
            return true;

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
    
    public boolean modificar(Tasas tasa){
         PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE tasa set santander=?,banamex=?,bancomer=? "
                + ",hsbc=?,promedio=? where Id = ?";
         try {

            ps = con.prepareStatement(sql);
            // ps.setString(1, persona.getRfc());
            ps.setDouble(1,tasa.getTasaSantander());
            ps.setDouble(2,tasa.getTasaBanamex());
            ps.setDouble(3, tasa.getTasaBancomer());
            ps.setDouble(4,tasa.getTasaHsbc());
            ps.setDouble(5, tasa.getPromedio());
            ps.setInt(6, tasa.getId());
          
                 ps.execute();
            return true;

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
    
    public boolean buscar(Tasas tasa){
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from tasa where ano = ? and mes = ? ";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, tasa.getPeriodo());
            ps.setString(2, tasa.getMes());
            
            rs = ps.executeQuery();
            if (rs.next()) {

                tasa.setTasaBanamex(Double.parseDouble(rs.getString("banamex")));
                tasa.setTasaBancomer(Double.parseDouble(rs.getString("bancomer")));
                tasa.setTasaSantander(Double.parseDouble(rs.getString("santander")));
                tasa.setTasaHsbc(Double.parseDouble(rs.getString("hsbc")));
                tasa.setPromedio(Double.parseDouble(rs.getString("promedio")));
                 tasa.setId(Integer.parseInt(rs.getString("Id")));
               
                return true;

            } else {
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
