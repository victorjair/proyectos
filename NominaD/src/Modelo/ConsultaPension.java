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
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class ConsultaPension extends Conexion {
    
    public boolean guardarpension(Pension pension) {
       
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO pension(empleado,porcentaje,beneficiario,plantel,oficio,montodescuento"
                + ",status,clave,rfc,secuencia,numeroquincena) Values(?,?,?,?,?,?,?,?,?,?,?)";
  try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, pension.getEmpleado());
            ps.setFloat(2, pension.getPorcentaje());
            ps.setString(3, pension.getBeneficiario());
            ps.setString(4, pension.getPlantel());
            ps.setString(5, pension.getOficio());
            ps.setFloat(6, pension.getMontodescuento());
            ps.setString(7, "A");
            ps.setString(8, "D_10");
            ps.setString(9, pension.getRfc());
            ps.setString(10, "1");
            ps.setString(11, pension.getNumeroQuincena());

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

  public boolean modificarpension(Pension pension) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();

         
        
        String sql = "UPDATE pension set porcentaje=?,beneficiario =?,plantel=?,oficio=?,montodescuento=?,rfc=?,"
                + "numeroquincena=? where Id = ? ";

      
               try {

                     ps = con.prepareStatement(sql);
                     
                     ps.setFloat(2, pension.getPorcentaje());
                     ps.setString(3, pension.getBeneficiario());
                     ps.setString(4, pension.getPlantel());
                     ps.setString(5, pension.getOficio());
                     ps.setFloat(6, pension.getMontodescuento());
                     ps.setString(9, pension.getRfc());
                     ps.setString(10, pension.getIdPension());
                   
               
                  
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
    
 public boolean buscarpension(Pension pension) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from pension where Id = ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, pension.getIdPension());
            rs = ps.executeQuery();
            if (rs.next()) {

                pension.setIdPension(rs.getString("Id"));
                pension.setSecuencia(rs.getInt("secuencia"));
                pension.setEmpleado(rs.getInt("empleado"));
                pension.setPorcentaje(rs.getFloat("porcentaje"));
                pension.setBeneficiario(rs.getString("beneficiario"));
                pension.setPlantel(rs.getString("plantel"));
                pension.setOficio(rs.getString("oficio"));
                pension.setMontodescuento(rs.getFloat("montodescuento"));
                pension.setStatus(rs.getString("status"));
                pension.setRfc(rs.getString("rfc"));
                pension.setNumeroQuincena(rs.getString("numeroquincena"));

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
 
 public boolean bajapension(Pension pension){
    PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE pension SET status = ? Where Id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,"B");
            ps.setString(4, pension.getIdPension());
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
 
 public ArrayList buscarbeneficiariopension(Pension pension) {

        ArrayList<Pension> Listabeneficiario = new ArrayList<Pension>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT beneficiario,porcentaje,oficio,montodescuento,status,secuencia,Id from pension where Id = ? ";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, pension.getIdPension());
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Pension lista = new Pension(rs.getString("beneficiario"),rs.getFloat("porcentaje"), rs.getString("oficio"),
                        rs.getFloat("montodescuento"), rs.getString("status"), rs.getInt("secuencia"), rs.getString("Id"));

                Listabeneficiario.add(lista);

            }

        } catch (SQLException e) {

            System.err.println(e);
            //return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return Listabeneficiario;
    }
}
