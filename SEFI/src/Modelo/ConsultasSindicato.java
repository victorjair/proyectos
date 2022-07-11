/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author victor
 */
public class ConsultasSindicato extends Conexion {
    
    
    public boolean completarquincenaactual(Sindicato sindicato){
     
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "select concat(year(now()),numeroquincena) as qnaactual from dias "
                + "where day(now()) = dia and month(now()) = mes ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                //credito.setFolio(Integer.parseInt(rs.getString("folio")));
               sindicato.setQnaAfiliacion(rs.getString("qnaactual"));

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
    
     public boolean registrar(Sindicato sindicato) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO sindicato(rfcsindicato,nombresindicato,telefonouno,rfcrepresentante,"
                + "nombrerepresentante,cargorepresentante,qnaafiliacionsindicato,fechaafiliacion,status,detallessindicato) "
                + "Values(?,?,?,?,"
                + "?,?,?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, sindicato.getRfcSindicato());
            ps.setString(2, sindicato.getNombreSindicato());
            ps.setString(3, sindicato.getTelefonoSindicato());
            ps.setString(4, sindicato.getRfcRepresentante());
            ps.setString(5, sindicato.getNombreRepresentante());
            ps.setString(6, sindicato.getPuestoRepresentante());
            ps.setString(7, sindicato.getQnaAfiliacion());
            ps.setString(8, sindicato.getFechaAfiliacion());
            ps.setString(9, sindicato.getStatus());
            ps.setString(10, sindicato.getDetalles());
           
            

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
     
     public boolean modificar(Sindicato sindicato) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE sindicato set rfcsindicato=?,nombresindicato=?,telefonouno=? "
                + ",rfcrepresentante=?,nombrerepresentante=?,cargorepresentante=?,qnaafiliacionsindicato=?,fechaafiliacion=?,detallessindicato=?"
                + "where Id = ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, sindicato.getRfcSindicato());
            ps.setString(2, sindicato.getNombreSindicato());
            ps.setString(3, sindicato.getTelefonoSindicato());
            ps.setString(4, sindicato.getRfcRepresentante());
            ps.setString(5, sindicato.getNombreRepresentante());
            ps.setString(6, sindicato.getPuestoRepresentante());
            ps.setString(7, sindicato.getQnaAfiliacion());
            ps.setString(8, sindicato.getFechaAfiliacion());
            ps.setString(9, sindicato.getDetalles());
            ps.setString(10, sindicato.getId());
            
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
     
      public boolean eliminar(Sindicato sindicato) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE from sindicato WHERE Id =?";
        //String sql = "UPDATE afiliado SET status = ?,causabaja = ?,fechabaja = ?, usuariobaja = ? WHERE rfc = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,sindicato.getId());
            
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

    public boolean buscar(Sindicato sindicato) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from sindicato where Id  = ? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, sindicato.getId());

            rs = ps.executeQuery();

            if (rs.next()) {

                sindicato.setFechaAfiliacion(rs.getString("fechaafiliacion"));
                sindicato.setNombreRepresentante(rs.getString("nombrerepresentante"));
                sindicato.setNombreSindicato(rs.getString("nombresindicato"));
                sindicato.setPuestoRepresentante(rs.getString("cargorepresentante"));
                sindicato.setQnaAfiliacion(rs.getString("qnaafiliacionsindicato"));
                sindicato.setRfcRepresentante(rs.getString("rfcrepresentante"));
                sindicato.setRfcSindicato(rs.getString("rfcsindicato"));
                sindicato.setTelefonoSindicato(rs.getString("telefonouno"));
                sindicato.setStatus(rs.getString("status"));
                sindicato.setDetalles(rs.getString("detallessindicato"));

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
